package ru.myproject.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.mypackage.dto.*;
import ru.mypackage.model.*;
import ru.mypackage.repository.*;
import ru.mypackage.service.FileService;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {
    @Mock
    EventRepository eventRepositoryMock;
    @Mock
    FileRepository fileRepositoryMock;

    @InjectMocks
    FileService fileService;

    @Test
    public void shouldReturnFileDtoById(){
        FileEntity fileEntityActual = FileEntity.builder()
                .id(1L)
                .location("location1")
                .size(100L)
                .build();
        FileDto fileDtoExpected = FileDto.builder()
                .id(1L)
                .location("location1")
                .size(100L)
                .build();

        Mockito.when((fileRepositoryMock.getById(Mockito.any(Long.class)))).thenReturn(fileEntityActual);

        Assert.assertEquals(fileDtoExpected, fileService.getById(1L));
        Mockito.verify(fileRepositoryMock).getById(1L);
    }

    @Test
    public void shouldReturnListOfFileDtos(){
        List<FileEntity> fileEntitiesActual = Arrays.asList(
                FileEntity.builder().id(1L).location("location1").size(100L).build(),
                FileEntity.builder().id(1L).location("location1").size(100L).build());

        List<FileDto> fileDtosExpected = Arrays.asList(
                FileDto.builder().id(1L).location("location1").size(100L).build(),
                FileDto.builder().id(1L).location("location1").size(100L).build());

        Mockito.when(fileRepositoryMock.getAll()).thenReturn(fileEntitiesActual);

        Assert.assertEquals(fileDtosExpected, fileService.getAll());
        Mockito.verify(fileRepositoryMock).getAll();
    }

    @Test
    public void shouldReturnSavedEventEntity(){
        FileDto fileDto = FileDto.builder()
                .id(1L).location("location1").size(100L)
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L).name("Name1")
                .build();

        FileEntity fileEntity = fileDto.toEntity();

        EventDto eventDto = EventDto.builder()
                .description("creating a file").date(new Date()).userDto(userDto).fileDto(fileDto)
                .build();

        EventEntity eventEntityActual = eventDto.toEntity();

        EventEntity eventEntityExpected = eventDto.toEntity();

        Mockito.when(fileRepositoryMock.save(Mockito.any(FileEntity.class))).thenReturn(fileEntity);
        Mockito.when(eventRepositoryMock.save(eventEntityActual)).thenReturn(eventEntityActual);

        Assert.assertEquals(eventEntityExpected, fileService.save(fileDto,userDto));
        Mockito.verify(fileRepositoryMock).save(Mockito.any(FileEntity.class));
        Mockito.verify(eventRepositoryMock).save(eventEntityActual);
    }

    // fileService.update(FileDto fileDto) do nothing and do not need to be tested.

    @Test
    public void ShouldCallRemoveMethod(){
        fileService.remove(1L);

        Mockito.verify(fileRepositoryMock).remove(1L);
    }
}
