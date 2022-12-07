package com.example.hairsalon.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;
    private LocalDateTime start;
    private LocalDateTime finish;
    private OrderStatus status;
    private Integer clientProfileId;
    private Integer barberProfileId;
    private Integer barbershopId;

    public Order(OrderBuilder orderBuilder) {
        this.id = orderBuilder.id;
        this.barberProfileId = orderBuilder.barberProfileId;
        this.start = orderBuilder.start;
        this.finish = orderBuilder.finish;
        this.status = orderBuilder.status;
        this.barbershopId = orderBuilder.barbershopId;
        this.clientProfileId = orderBuilder.clientProfileId;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OrderBuilder {
        private Integer id;
        private LocalDateTime start;
        private LocalDateTime finish;
        private OrderStatus status;
        private Integer clientProfileId;
        private Integer barberProfileId;
        private Integer barbershopId;

        public OrderBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public OrderBuilder start(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public OrderBuilder finish(LocalDateTime finish) {
            this.finish = finish;
            return this;
        }

        public OrderBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderBuilder clientProfileId(Integer clientProfileId) {
            this.clientProfileId = clientProfileId;
            return this;
        }

        public OrderBuilder barberProfileId(Integer barberProfileId) {
            this.barberProfileId = barberProfileId;
            return this;
        }

        public OrderBuilder barbershopId(Integer barbershopId) {
            this.barbershopId = barbershopId;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

}
