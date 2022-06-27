package ru.myproject.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.mypackage.dto.FileDto;
import ru.mypackage.model.EventEntity;
import ru.mypackage.model.FileEntity;
import ru.mypackage.model.UserEntity;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.repository.FileRepository;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.service.FileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {
    @Mock
    EventRepository eventRepositoryMock;
    @Mock
    FileRepository fileRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;
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

        Mockito.when((fileRepositoryMock.getById(Mockito.any()))).thenReturn(fileEntityActual);

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

        Assert.assertEquals(fileService.getAll(), fileDtosExpected);
        Mockito.verify(fileRepositoryMock).getAll();
    }

    @Test
    public void shouldReturnSavedEventEntity(){
        UserEntity userEntity = UserEntity.builder()
                .id(1L).name("Name1")
                .build();
        FileEntity fileEntity = FileEntity.builder()
                .id(1L).location("location1").size(100L)
                .build();
        EventEntity eventEntityActual = EventEntity.builder()
                .id(1L).description("description1").date(new Date()).userEntity(userEntity).fileEntity(fileEntity)
                .build();
        userEntity.setEventEntities(Arrays.asList(eventEntityActual));

        EventEntity eventEntityExpected = EventEntity.builder()
                .id(eventEntityActual.getId())
                .description(eventEntityActual.getDescription())
                .date(eventEntityActual.getDate())
                .userEntity(eventEntityActual.getUserEntity())
                .fileEntity(eventEntityActual.getFileEntity())
                .build();

        FileDto fileDto = FileDto.builder()
                .id(1L).location("location1").size(100L)
                .build();

        Mockito.when(fileRepositoryMock.save(fileEntity)).thenReturn(fileEntity);
        Mockito.when(userRepositoryMock.getById(1L)).thenReturn(userEntity);
        Mockito.when(eventRepositoryMock.save(eventEntityActual)).thenReturn(eventEntityActual);

        Assert.assertEquals(eventEntityActual, fileService.save(fileDto,"1"));

    }
}
