package ru.mypackage.service;

import ru.mypackage.dto.FileDto;
import ru.mypackage.model.*;
import ru.mypackage.repository.*;
import ru.mypackage.repository.hibernate.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {

    private final FileRepository fileRepository ;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public FileDto getById(Long id) {
        return FileDto.fromEntity(fileRepository.getById(id));
    }

    public List<FileDto> getAll(){
        List<FileEntity> fileDtoList = fileRepository.getAll();
        return fileDtoList.stream().map(FileDto::fromEntity).collect(Collectors.toList());
    }

    public EventEntity save(FileDto fileDto, String userId){
        FileEntity file = fileRepository.save(fileDto.toEntity());

        UserEntity userEntity = userRepository.getById(Long.parseLong(userId));
        EventEntity eventEntity = EventEntity.builder()
                .description("creating a file")
                .date(new Date())
                .userEntity(userEntity)
                .fileEntity(file)
                .build();
        return eventRepository.save(eventEntity);
    }

    public void update(String userId){
        // It is nothing to update in a file
    }

    public void delete(Long id){
        fileRepository.remove(id);
    }




}
