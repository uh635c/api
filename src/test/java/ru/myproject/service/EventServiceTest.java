package ru.myproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.mypackage.model.EventEntity;
import ru.mypackage.model.FileEntity;
import ru.mypackage.model.UserEntity;
import ru.mypackage.repository.EventRepository;
import ru.mypackage.repository.FileRepository;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.service.EventService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    @Mock
    EventRepository eventRepository;
    @Mock
    FileRepository fileRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    EventService eventService;


//    @Test
//    public void shouldReturnListOfEventsDto(){
//        List<EventEntity> eventEntitiesActual = new ArrayList<>();
//        eventEntitiesActual.add(EventEntity.builder()
//                .id(1L)
//                .description("description")
//                .date(new Date(100000))
//                .fileEntity(FileEntity.builder()
//                        .id(1L)
//                        .location("location")
//                        .size(100L)
//                        .build())
//                .userEntity(UserEntity.builder()
//                        .id(1L)
//                        .name("Name1")
//
//                        .build())
//                .build());
//
//
//
//        eventService.getById(1L);
//    }
//
//    @Test
//    public void shouldReturnEventDtoById(){
//        EventEntity eventEntity = EventEntity.builder()
//                .id(1L)
//                .description("description")
//                .date(new Date(100000))
//                .fileEntity(FileEntity.builder()
//                        .id(1L)
//                        .location("location")
//                        .size(100L)
//                        .build())
//                .userEntity(UserEntity.builder()
//                        .id(1L)
//                        .name("Name1")
//
//                        .build())
//                .build();
//    }





}
