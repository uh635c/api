package ru.mypackage.service;

import ru.mypackage.dto.EventDto;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.repository.FileRepository;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.repository.hibernate.HiberEventRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberFileRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberUserRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, FileRepository fileRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public EventDto getById(Long id){
        return EventDto.fromEntity(eventRepository.getById(id));
    }

    public List<EventDto> getAll(){
        return eventRepository.getAll().stream().map(e-> getById(e.getId())).collect(Collectors.toList());
    }

    public void save(EventDto eventDto){
        eventDto.setUserEntity(userRepository.getById(eventDto.getUserId()));
        eventDto.setFileEntity(fileRepository.getById(eventDto.getFileId()));
        eventRepository.save(eventDto.toEntity());
    }

    public void update(EventDto eventDto){
        eventDto.setUserEntity(userRepository.getById(eventDto.getUserId()));
        eventDto.setFileEntity(fileRepository.getById(eventDto.getFileId()));
        eventRepository.update(eventDto.toEntity());
    }

    public void remove(Long id){
        eventRepository.remove(id);
    }
}
