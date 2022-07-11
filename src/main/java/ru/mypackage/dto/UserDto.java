package ru.mypackage.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;
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


   public static UserDto fromEntity(UserEntity userEntity){
       return UserDto.builder()
               .id(userEntity.getId())
               .name(userEntity.getName())
               .eventDtos(userEntity.getEventEntities().stream().map(EventDto::fromEntityForUser).collect(Collectors.toList()))
               .build();
   }

   public UserEntity toEntity() {
       return UserEntity.builder()
               .id(id)
               .name(name)
               .eventEntities(null)
               .build();
   }
}