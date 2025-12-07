package com.johnakins.tickets.mappers;

import com.johnakins.tickets.domain.dtos.createEvent.CreateEventRequest;
import com.johnakins.tickets.domain.dtos.createEvent.CreateEventRequestDto;
import com.johnakins.tickets.domain.dtos.createEvent.CreateEventResponseDto;
import com.johnakins.tickets.domain.dtos.createTicketType.CreateTicketTypeRequest;
import com.johnakins.tickets.domain.dtos.createTicketType.CreateTicketTypeResponseDto;
import com.johnakins.tickets.domain.dtos.updateEvent.UpdateEventRequest;
import com.johnakins.tickets.domain.dtos.updateEvent.UpdateEventRequestDto;
import com.johnakins.tickets.domain.dtos.updateEvent.UpdateEventResponseDto;
import com.johnakins.tickets.domain.dtos.updateTicketType.UpdateTicketTypeRequest;
import com.johnakins.tickets.domain.dtos.updateTicketType.UpdateTicketTypeResponseDto;
import com.johnakins.tickets.domain.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

//    public CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto){
//        if (dto == null) return null;
//        return  new CreateTicketTypeRequest(
//                dto.getName(),
//                dto.getPrice(),
//                dto.getDescription(),
//                dto.getTotalAvailable()
//        );
//    }

    public CreateEventRequest fromDto(CreateEventRequestDto dto){
        if (dto == null) return null;
        return new CreateEventRequest(
                dto.getName(),
                dto.getStart(),
                dto.getEnd(),
                dto.getVenue(),
                dto.getSalesStart(),
                dto.getSalesEnd(),
                dto.getStatus(),
                dto.getTicketTypes()
                        .stream().map(t -> new CreateTicketTypeRequest(
                                t.getName(),
                                t.getPrice(),
                                t.getDescription(),
                                t.getTotalAvailable()
                        )).toList()
        );
    }

    public CreateEventResponseDto toDto(Event event){
        if (event == null) return null;
        return  new CreateEventResponseDto(
                event.getId(),
                event.getName(),
                event.getStart(),
                event.getEnd(),
                event.getVenue(),
                event.getSalesStart(),
                event.getSalesEnd(),
                event.getStatus(),
                event.getTicketTypes()
                        .stream().map(t -> new CreateTicketTypeResponseDto(
                                t.getId(),
                                t.getName(),
                                t.getPrice(),
                                t.getDescription(),
                                t.getTotalAvailable(),
                                t.getCreatedAt(),
                                t.getUpdatedAt()
                        )).toList(),
                event.getCreatedAt(),
                event.getUpdatedAt()
            );
    }

    public UpdateEventRequest fromDto(UpdateEventRequestDto dto){
        if (dto == null) return null;
        return new UpdateEventRequest(
                dto.getId(),
                dto.getName(),
                dto.getStart(),
                dto.getEnd(),
                dto.getVenue(),
                dto.getSalesStart(),
                dto.getSalesEnd(),
                dto.getStatus(),
                dto.getTicketTypes()
                        .stream().map(t -> new UpdateTicketTypeRequest(
                                t.getId(),
                                t.getName(),
                                t.getPrice(),
                                t.getDescription(),
                                t.getTotalAvailable()
                        )).toList()
        );
    }

    public UpdateEventResponseDto toUpdateDto(Event event){
        if (event == null) return null;
        return  new UpdateEventResponseDto(
                event.getId(),
                event.getName(),
                event.getStart(),
                event.getEnd(),
                event.getVenue(),
                event.getSalesStart(),
                event.getSalesEnd(),
                event.getStatus(),
                event.getTicketTypes()
                        .stream().map(t -> new UpdateTicketTypeResponseDto(
                                t.getId(),
                                t.getName(),
                                t.getPrice(),
                                t.getDescription(),
                                t.getTotalAvailable(),
                                t.getCreatedAt(),
                                t.getUpdatedAt()
                        )).toList(),
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}
