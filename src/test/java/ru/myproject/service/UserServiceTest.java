package ru.myproject.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.mypackage.dto.UserDto;
import ru.mypackage.model.*;
import ru.mypackage.repository.UserRepository;
import ru.mypackage.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepositoryMock;
    @InjectMocks
    UserService userService;

    @Test
    public void shouldReturnUserEntity(){
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .name("Name")
                .eventEntities(Arrays.asList(
                        EventEntity.builder()
                                .id(1L)
                                .description("description1")
                                .date(new Date())
                                .build(),
                        EventEntity.builder()
                                .id(2L)
                                .description("description2")
                                .date(new Date())
                                .build()))
                .build();

        UserDto userDto = UserDto.fromEntity(userEntity);

        Mockito.when(userRepositoryMock.getById(Mockito.any(Long.class))).thenReturn(userEntity);

        Assert.assertEquals(userDto, userService.getById(1L));
        Mockito.verify(userRepositoryMock).getById(1L);
    }

    @Test
    public void shouldReturnListOfUserDtos(){
        UserEntity userEntity1 = UserEntity.builder()
                .id(1L)
                .name("Name1")
                .eventEntities(Arrays.asList(EventEntity.builder()
                                .id(1L)
                                .description("description1")
                                .date(new Date())
                                .build(),
                        EventEntity.builder()
                                .id(2L)
                                .description("description2")
                                .date(new Date())
                                .build()))
                .build();

        UserEntity userEntity2 = UserEntity.builder()
                .id(2L)
                .name("Name2")
                .eventEntities(Arrays.asList(EventEntity.builder()
                                .id(3L)
                                .description("description3")
                                .date(new Date())
                                .build(),
                        EventEntity.builder()
                                .id(4L)
                                .description("description4")
                                .date(new Date())
                                .build()))
                .build();

        List<UserEntity> userEntityList = Arrays.asList(userEntity1, userEntity2);

        List<UserDto> userDtoList = userEntityList.stream().map(UserDto::fromEntity).collect(Collectors.toList());

        Mockito.when(userRepositoryMock.getAll()).thenReturn(userEntityList);
        Mockito.when(userRepositoryMock.getById(1L)).thenReturn(userEntity1);
        Mockito.when(userRepositoryMock.getById(2L)).thenReturn(userEntity2);

        Assert.assertEquals(userDtoList, userService.getAll());
        Mockito.verify(userRepositoryMock).getAll();
        Mockito.verify(userRepositoryMock).getById(1L);
        Mockito.verify(userRepositoryMock).getById(2L);
    }

    @Test
    public void shouldReturnSavedUserEntity(){
        UserEntity userEntityActual = UserEntity.builder()
                .name("Name")
                .eventEntities(null)
                .build();

        UserEntity userEntityExpected = UserEntity.builder()
                .name("Name")
                .eventEntities(null)
                .build();

        UserDto userDto = UserDto.builder()
                .name("Name")
                .eventDtos(null)
                .build();

        Mockito.when(userRepositoryMock.save(userEntityActual)).thenReturn(userEntityActual);

        Assert.assertEquals(userEntityExpected, userService.save(userDto));
        Mockito.verify(userRepositoryMock).save(userEntityActual);
    }

    @Test
    public void shouldReturnUpdatedUserEntity(){
        UserEntity userEntityActual = UserEntity.builder()
                .id(1L)
                .name("Name")
                .eventEntities(null)
                .build();

        UserEntity userEntityExpected = UserEntity.builder()
                .id(1L)
                .name("Name")
                .eventEntities(null)
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("Name")
                .eventDtos(null)
                .build();

        Mockito.when(userRepositoryMock.update(userEntityActual)).thenReturn(userEntityActual);

        Assert.assertEquals(userEntityExpected, userService.update(userDto));
        Mockito.verify(userRepositoryMock).update(userEntityActual);
    }

    @Test
    public void shouldCallRemoveMethod(){
        userService.remove(1L);

        Mockito.verify(userRepositoryMock).remove(1L);
    }

}
