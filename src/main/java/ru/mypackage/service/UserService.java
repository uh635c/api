package ru.mypackage.service;

import ru.mypackage.dto.UserDto;
import ru.mypackage.model.UserEntity;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.repository.UserRepository;


import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
            }

    public UserDto getById(Long id){
        UserEntity userEntity = userRepository.getById(id);
        return UserDto.fromEntity(userEntity);
    }

    public List<UserDto> getAll(){
        List<UserEntity> userEntities = userRepository.getAll();
        return userEntities.stream().map(u->getById(u.getId())).collect(Collectors.toList());
    }

    public UserEntity save(UserDto userDto){
        return userRepository.save(userDto.toEntity());
    }

    public UserEntity update(UserDto userDto){
        return userRepository.update(userDto.toEntity());
    }

    public void remove(Long id){
        userRepository.remove(id);
    }


}
