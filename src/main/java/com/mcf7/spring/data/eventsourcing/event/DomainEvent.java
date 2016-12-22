package com.mcf7.spring.data.eventsourcing.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class DomainEvent {
    private String eventId;
    private Date timestamp;
}
