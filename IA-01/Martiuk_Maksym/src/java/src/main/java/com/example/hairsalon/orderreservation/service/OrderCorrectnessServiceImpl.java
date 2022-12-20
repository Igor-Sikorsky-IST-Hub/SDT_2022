package com.example.hairsalon.orderreservation.service;

import com.example.hairsalon.entity.Barbershop;
import com.example.hairsalon.entity.Order;
import com.example.hairsalon.entity.Schedule;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.exception.ResourceWriteException;
import com.example.hairsalon.outofbusiness.service.OutOfBusinessCheckService;
import com.example.hairsalon.repository.BarbershopRepository;
import com.example.hairsalon.repository.OrderRepository;
import com.example.hairsalon.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCorrectnessServiceImpl implements OrderCorrectnessService {

    private final ScheduleRepository scheduleRepository;
    private final OrderRepository orderRepository;
    private final BarbershopRepository barbershopRepository;
    private final OutOfBusinessCheckService outOfBusinessCheckService;

    @Override
    public void checkOrderCorrect(Order order) {
        if (!checkReliabilityOfTheOrder(order)) {
            throw new ResourceWriteException("Your order isn't correct! Check your order credentials.");
        }
    }

    private boolean checkReliabilityOfTheOrder(Order order) {
        var barbershopOrders = getBarbershopOrders(order);
        var quantity = quantityOfBusySlotsAtTheMomentOfOrdering(order, barbershopOrders);
        var barbershop = getBarbershop(order);
        var amountOfWorkplaces = barbershop.getAmountOfWorkplaces();

        return quantity < amountOfWorkplaces
                && !checkingBarberBusyness(order, barbershopOrders)
                && isOrderStartAndFinishInBarbershopWorkingHours(order)
                && isInBusinessNow(barbershop);
    }

    private boolean isInBusinessNow(Barbershop barbershop) {
        var outOfBusinesses = outOfBusinessCheckService.getAllFromOutOfBusinessCategory().stream()
                .filter(outOfBusiness -> outOfBusiness.getBarbershopId().equals(barbershop.getId())
                        && (isNull(outOfBusiness.getFinish()) || outOfBusiness.getFinish().isAfter(LocalDateTime.now())))
                .toList();

        return outOfBusinesses.isEmpty();
    }

    private List<Order> getBarbershopOrders(Order order) {
        return orderRepository.findAll().stream()
                .filter(iterOrder -> iterOrder.getBarbershopId().equals(order.getBarbershopId()))
                .toList();
    }

    private int quantityOfBusySlotsAtTheMomentOfOrdering(Order order, List<Order> barbershopOrders) {
        return (int) barbershopOrders.stream()
                .filter(iterOrder -> isScheduleOrderStartInGivenOrderDuration(order, iterOrder)
                        || isScheduleOrderStartEqualToGivenOrderStart(order, iterOrder))
                .count();
    }

    private Barbershop getBarbershop(Order order) {
        return barbershopRepository.findById(order.getBarbershopId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Cannot find barbershop with id: %d", order.getBarbershopId()
                )));
    }

    private boolean checkingBarberBusyness(Order order, List<Order> barbershopOrders) {
        return barbershopOrders.stream()
                .anyMatch(iterOrder -> isAnyOrderExistAtTheTimeOfOrdering(order, iterOrder)
                        && necessaryBarber(order, iterOrder));
    }

    private boolean isScheduleOrderStartInGivenOrderDuration(Order order, Order iterOrder) {
        return iterOrder.getStart().isAfter(order.getStart())
                && iterOrder.getStart().isBefore(order.getFinish())

                || iterOrder.getStart().isBefore(order.getStart())
                && iterOrder.getFinish().isAfter(order.getStart());
    }

    private boolean isOrderStartAndFinishInBarbershopWorkingHours(Order order) {
        var orderDay = getOrderDayOfWeek(order);
        var barbershopSchedules = getBarbershopSchedules(order);
        var associatedSchedule = getAssociatedScheduleWithGivenOrder(orderDay, barbershopSchedules);

        var orderStartTime = order.getStart().toLocalTime();
        var orderFinishTime = order.getFinish().toLocalTime();

        return (orderStartTime.isAfter(associatedSchedule.getOpening())
                || orderStartTime.equals(associatedSchedule.getOpening()))
                && (orderFinishTime.isBefore(associatedSchedule.getClosing())
                || orderFinishTime.equals(associatedSchedule.getClosing()));
    }

    private DayOfWeek getOrderDayOfWeek(Order order) {
        return order.getStart().toLocalDate().getDayOfWeek();
    }

    private List<Schedule> getBarbershopSchedules(Order order) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getBarbershopId().equals(order.getBarbershopId()))
                .toList();
    }

    private Schedule getAssociatedScheduleWithGivenOrder(DayOfWeek orderDay, List<Schedule> barbershopSchedules) {
        return barbershopSchedules.stream()
                .filter(schedule -> schedule.isActive()
                        && schedule.getDayOfWeek().equals(orderDay))
                .findFirst()
                .orElseThrow(() -> new ResourceWriteException("Order not in barbershop schedule"));
    }

    private boolean necessaryBarber(Order order, Order iterOrder) {
        return Objects.equals(iterOrder.getBarberProfileId(), order.getBarberProfileId());
    }

    private boolean isAnyOrderExistAtTheTimeOfOrdering(Order order, Order iterOrder) {
        return isScheduleOrderStartInGivenOrderDuration(order, iterOrder)
                || isScheduleOrderStartEqualToGivenOrderStart(order, iterOrder);
    }

    private boolean isScheduleOrderStartEqualToGivenOrderStart(Order order, Order iterOrder) {
        return iterOrder.getStart().equals(order.getStart());
    }

}
