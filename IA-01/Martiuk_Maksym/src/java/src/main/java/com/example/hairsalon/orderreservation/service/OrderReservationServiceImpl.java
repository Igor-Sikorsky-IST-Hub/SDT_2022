package com.example.hairsalon.orderreservation.service;

import com.example.hairsalon.entity.Client;
import com.example.hairsalon.entity.Order;
import com.example.hairsalon.entity.OrderStatus;
import com.example.hairsalon.exception.ResourceNotFoundException;
import com.example.hairsalon.orderreservation.converter.OrderReservationDtoConverter;
import com.example.hairsalon.orderreservation.dto.OrderReservationDto;
import com.example.hairsalon.repository.BarbershopRepository;
import com.example.hairsalon.repository.ClientRepository;
import com.example.hairsalon.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderReservationServiceImpl implements OrderReservationService {

    private final OrderCorrectnessService orderCorrectnessService;
    private final OrderRepository orderRepository;
    private final BarbershopRepository barbershopRepository;
    private final ClientRepository clientRepository;
    private final ChangeOrderPermissionService changeOrderPermissionService;

    @Override
    public List<Order> makeReservations(List<OrderReservationDto> listOfDto) {
        return listOfDto.stream()
                .map(this::makeReservation)
                .toList();
    }

    @Override
    public Order updateReservation(OrderReservationDto dto) {
        var order = getOrderFromReservationDto(dto);

        changeOrderPermissionService.checkAuthenticationPermission(order);

        orderCorrectnessService.checkOrderCorrect(order);
        order.setStatus(OrderStatus.AWAITING);

        return orderRepository.update(order);
    }

    @Override
    public void deleteReservation(Integer orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Cannot find order with id: %d", orderId))
        );

        changeOrderPermissionService.checkAuthenticationPermission(order);

        deleteUserFromBarbershopClientsIfHasOnlyOneReservation(orderId);
        orderRepository.deleteById(orderId);
    }

    private Order makeReservation(OrderReservationDto orderReservationDto) {
        var order = getOrderFromReservationDto(orderReservationDto);

        orderCorrectnessService.checkOrderCorrect(order);

        order.setStatus(OrderStatus.AWAITING);

        var savedOrder = orderRepository.save(order);
        var client = getClientFromOrder(savedOrder);
        clientRepository.save(client);

        return savedOrder;
    }

    private Order getOrderFromReservationDto(OrderReservationDto orderReservationDto) {
        var barberId = orderReservationDto.getBarberProfileId();
        var clientId = orderReservationDto.getClientProfileId();
        var barbershopId = getBarbershopId(orderReservationDto.getBarbershopName());

        return OrderReservationDtoConverter.fromDto(orderReservationDto, barberId, clientId, barbershopId);
    }

    private Integer getBarbershopId(String barbershopName) {
        return barbershopRepository.findByName(barbershopName).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Cannot find barbershop with name: %S", barbershopName))
        ).getId();
    }

    private Client getClientFromOrder(Order savedOrder) {
        return Client.builder()
                .clientProfileId(savedOrder.getClientProfileId())
                .barbershopId(savedOrder.getBarbershopId())
                .build();
    }

    private void deleteUserFromBarbershopClientsIfHasOnlyOneReservation(Integer orderId) {
        orderRepository.findById(orderId).ifPresent(order -> {
            long quantityOfReservationsThisUser = orderRepository.findAll().stream()
                    .filter(iterOrder -> order.getClientProfileId().equals(iterOrder.getClientProfileId()))
                    .count();

            if (quantityOfReservationsThisUser == 1) {
                var clientFromOrder = getClientFromOrder(order);
                clientRepository.delete(clientFromOrder);
            }
        });
    }

}
