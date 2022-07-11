package ru.mypackage.service;

import ru.mypackage.dto.EventDto;
import ru.mypackage.dto.FileDto;
import ru.mypackage.dto.UserDto;
import ru.mypackage.model.*;
import ru.mypackage.repository.*;
import ru.mypackage.repository.hibernate.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {

    private final FileRepository fileRepository ;
    private final EventRepository eventRepository;

    public FileService(FileRepository fileRepository, EventRepository eventRepository) {
        this.fileRepository = fileRepository;
        this.eventRepository = eventRepository;

    }

    public FileDto getById(Long id) {
        return FileDto.fromEntity(fileRepository.getById(id));
    }

    public List<FileDto> getAll(){
        List<FileEntity> fileDtoList = fileRepository.getAll();
        return fileDtoList.stream().map(FileDto::fromEntity).collect(Collectors.toList());
    }

    public EventEntity save(FileDto fileDto, UserDto userDto){
        fileDto = FileDto.fromEntity(fileRepository.save(fileDto.toEntity()));

        EventDto eventDto = EventDto.builder()
                .description("creating a file")
                .date(new Date())
                .userDto(userDto)
                .fileDto(fileDto)
                .build();
        EventEntity eventEntity = eventDto.toEntity();

        return eventRepository.save(eventEntity);
    }

    public void update(String userId){
        // It is nothing to update in a file
    }

    public void remove(Long id){
        fileRepository.remove(id);
    }
}
