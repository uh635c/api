package ru.mypackage.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mypackage.model.EventEntity;
import ru.mypackage.model.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("events")
    private List<EventDto> eventDtos;
    @Expose
    private List<EventEntity> eventEntities;

   public static UserDto fromEntity(UserEntity userEntity){
       if(userEntity.getEventEntities()==null){
           return UserDto.builder()
                   .id(userEntity.getId())
                   .name(userEntity.getName())
                   .eventDtos(null)
                   .build();
       }else{
           return UserDto.builder()
                   .id(userEntity.getId())
                   .name(userEntity.getName())
                   .eventDtos(userEntity.getEventEntities().stream().map(EventDto::fromEntity).collect(Collectors.toList()))
                   .build();
       }

   }

   public UserEntity toEntity(){
       if(eventDtos==null){
           return UserEntity.builder()
                   .id(id)
                   .name(name)
                   .eventEntities(null)
                   .build();
       }else{
           return UserEntity.builder()
                   .id(id)
                   .name(name)
                   .eventEntities(eventEntities)
                   .build();
       }

   }
}
