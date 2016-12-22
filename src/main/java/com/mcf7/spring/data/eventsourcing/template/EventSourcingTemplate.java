package com.mcf7.spring.data.eventsourcing.template;

import com.mcf7.spring.data.eventsourcing.aggregate.AggregateUpdater;
import com.mcf7.spring.data.eventsourcing.event.DomainEvent;
import com.mcf7.spring.data.eventsourcing.event.EventValidationHandler;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class EventSourcingTemplate<T extends DomainEvent> {
    //TODO work out an abstraction pattern for injecting this
    private PagingAndSortingRepository<T, String> eventStoreRepository;

    private EventValidationHandler eventValidationHandler;
    private AggregateUpdater aggregateUpdater;

    public EventSourcingTemplate(EventValidationHandler eventValidationHandler, AggregateUpdater aggregateUpdater) {
        this.eventValidationHandler = eventValidationHandler;
        this.aggregateUpdater = aggregateUpdater;
    }

    public void handle(T event) throws Exception {
        boolean isValid = eventValidationHandler.validateEvent(event);
        if (isValid) {
            eventStoreRepository.save(event);
            aggregateUpdater.invokeAggregateUpdate(event);
        }
    }
}