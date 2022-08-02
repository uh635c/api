package ru.myproject.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ru.mypackage.dto.EventDto;
import ru.mypackage.model.*;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.service.EventService;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    @Mock
    EventRepository eventRepositoryMock;
    @InjectMocks
    EventService eventService;

    @Test
    public void shouldReturnEventDtoById(){
        EventEntity eventEntity = EventEntity.builder()
                .id(1L)
                .description("description")
                .date(new Date())
                .userEntity(UserEntity.builder()
                        .id(1L)
                        .name("Name")
                        .build())
                .fileEntity(FileEntity.builder()
                        .id(1L)
                        .location("location1")
                        .size(100L)
                        .build())
                .build();

        EventDto eventDto = EventDto.fromEntity(eventEntity);

        Mockito.when(eventRepositoryMock.getById(Mockito.any(Long.class))).thenReturn(eventEntity);

        Assert.assertEquals(eventDto, eventService.getById(1L));
        Mockito.verify(eventRepositoryMock).getById(1L);
    }

    @Test
    public void shouldReturnListOfEventDtos(){
        EventEntity eventEntity1 = EventEntity.builder()
                .id(1L)
                .description("description1")
                .date(new Date())
                .userEntity(UserEntity.builder()
                        .id(1L)
                        .name("Name1")
                        .build())
                .fileEntity(FileEntity.builder()
                        .id(1L)
                        .location("location1")
                        .size(100L)
                        .build())
                .build();
        EventEntity eventEntity2 = EventEntity.builder()
                .id(2L)
                .description("description2")
                .date(new Date())
                .userEntity(UserEntity.builder()
                        .id(2L)
                        .name("Name2")
                        .build())
                .fileEntity(FileEntity.builder()
                        .id(2L)
                        .location("location2")
                        .size(200L)
                        .build())
                .build();
        List<EventEntity> eventEntityList = Arrays.asList(eventEntity1, eventEntity2);

        List<EventDto> eventDtoList = eventEntityList.stream().map(EventDto::fromEntity).collect(Collectors.toList());

        Mockito.when(eventRepositoryMock.getAll()).thenReturn(eventEntityList);

        Assert.assertEquals(eventDtoList, eventService.getAll());
        Mockito.verify(eventRepositoryMock).getAll();
    }

    @Test
    public void shouldReturnSavedEventEntity(){
        EventEntity eventEntityActual = EventEntity.builder()
                .description("description1")
                .date(new Date())
                .userEntity(UserEntity.builder()
                        .id(1L)
                        .name("Name1")
                        .build())
                .fileEntity(FileEntity.builder()
                        .id(1L)
                        .location("location1")
                        .size(100L)
                        .build())
                .build();

        EventEntity eventEntityExpected = EventEntity.builder()
                .description("description1")
                .date(new Date())
                .userEntity(UserEntity.builder()
                        .id(1L)
                        .name("Name1")
                        .build())
                .fileEntity(FileEntity.builder()
                        .id(1L)
                        .location("location1")
                        .size(100L)
                        .build())
                .build();


        EventDto eventDto = EventDto.fromEntity(eventEntityActual);

        Mockito.when(eventRepositoryMock.save(eventEntityActual)).thenReturn(eventEntityActual);

        Assert.assertEquals(eventEntityExpected, eventService.save(eventDto));
        Mockito.verify(eventRepositoryMock).save(eventEntityActual);
    }

    @Test
    public void shouldReturnUpdatedEventEntity(){
        EventEntity eventEntityActual = EventEntity.builder()
                .id(1L)
                .description("description1")
                .date(new Date())
                .userEntity(UserEntity.builder()
                        .id(1L)
                        .name("Name1")
                        .build())
                .fileEntity(FileEntity.builder()
                        .id(1L)
                        .location("location1")
                        .size(100L)
                        .build())
                .build();

        EventEntity eventEntityExpected = EventEntity.builder()
                .id(1L)
                .description("description1")
                .date(new Date())
                .userEntity(UserEntity.builder()
                        .id(1L)
                        .name("Name1")
                        .build())
                .fileEntity(FileEntity.builder()
                        .id(1L)
                        .location("location1")
                        .size(100L)
                        .build())
                .build();

        EventDto eventDto = EventDto.fromEntity(eventEntityActual);

        Mockito.when(eventRepositoryMock.update(eventEntityActual)).thenReturn(eventEntityActual);

        Assert.assertEquals(eventEntityExpected, eventService.update(eventDto));
        Mockito.verify(eventRepositoryMock).update(eventEntityActual);
    }

    @Test
    public void shouldCallRemoveMethod(){
        eventService.remove(1L);

        Mockito.verify(eventRepositoryMock).remove(1L);
    }

}
