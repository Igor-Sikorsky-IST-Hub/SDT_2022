package com.example.hairsalon.barbershopmanagement.converter;

import com.example.hairsalon.barbershopmanagement.dto.ClientResponseDto;
import com.example.hairsalon.entity.Client;

import java.util.List;

public final class ClientDtoConverter {

    private ClientDtoConverter() {
    }

    public static ClientResponseDto toDto(Client client) {
        var dto = new ClientResponseDto();
        dto.setBarbershopId(client.getBarbershopId());
        dto.setClientProfileId(client.getClientProfileId());

        return dto;
    }

    public static List<ClientResponseDto> toDtoList(List<Client> clients) {
        return clients.stream()
                .map(ClientDtoConverter::toDto)
                .toList();
    }

}
