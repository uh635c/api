package ru.mypackage.service;

import ru.mypackage.dto.UserDto;
import ru.mypackage.model.EventEntity;
import ru.mypackage.model.UserEntity;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.repository.FileRepository;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.repository.hibernate.HiberEventRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberFileRepositoryImpl;
import ru.mypackage.repository.hibernate.HiberUserRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UserService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public UserDto getById(Long id){
        UserEntity userEntity = userRepository.getById(id);
        userEntity.getEventEntities().forEach(e-> e.setFileEntity(eventRepository.getById(e.getId()).getFileEntity()));
        return UserDto.fromEntity(userEntity);
    }

    public List<UserDto> getAll(){
        List<UserEntity> userEntities = userRepository.getAll();
        return userEntities.stream().map(u-> getById(u.getId())).collect(Collectors.toList());
    }

    public void save(UserDto userDto){
        userRepository.save(userDto.toEntity());
    }

    public void update(UserDto userDto){
        userDto.setEventEntities(userRepository.getById(userDto.getId()).getEventEntities());
        userRepository.update(userDto.toEntity());
    }

    public void remove(Long id){
        userRepository.remove(id);
    }


}
