package ru.mypackage.service;

import ru.mypackage.dto.EventDto;
import ru.mypackage.model.EventEntity;
import ru.mypackage.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDto getById(Long id){
        EventEntity eventEntity = eventRepository.getById(id);
        return EventDto.fromEntity(eventEntity);
    }

    public List<EventDto> getAll(){
        List<EventEntity> eventEntities = eventRepository.getAll();
        return eventEntities.stream().map(EventDto::fromEntity).collect(Collectors.toList());
    }

    public EventEntity save(EventDto eventDto){
        return eventRepository.save(eventDto.toEntity());
    }

    public EventEntity update(EventDto eventDto){
        return eventRepository.update(eventDto.toEntity());
    }

    public void remove(Long id){
        eventRepository.remove(id);
    }
}
