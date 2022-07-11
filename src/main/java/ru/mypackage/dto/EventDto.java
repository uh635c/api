package ru.mypackage.dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import ru.mypackage.model.EventEntity;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    @SerializedName("id")
    private Long id;
    @SerializedName("description")
    private String description;
    @SerializedName("date")
    private Date date;
    @SerializedName("user")
    private UserDto userDto;
    @SerializedName("file")
    private FileDto fileDto;


    public static EventDto fromEntity(EventEntity eventEntity){
        return EventDto.builder()
                .id(eventEntity.getId())
                .description(eventEntity.getDescription())
                .date(eventEntity.getDate())
                .userDto(UserDto.builder()
                        .id(eventEntity.getUserEntity().getId())
                        .name(eventEntity.getUserEntity().getName())
                        .build())
                .fileDto(FileDto.fromEntity(eventEntity.getFileEntity()))
                .build();
    }

    public static EventDto fromEntityForUser(EventEntity eventEntity){
        return EventDto.builder()
                .id(eventEntity.getId())
                .description(eventEntity.getDescription())
                .date(eventEntity.getDate())
                .build();
    }

    public EventEntity toEntity(){
        return EventEntity.builder()
                .id(id)
                .description(description)
                .date(date)
                .userEntity(userDto.toEntity())
                .fileEntity(fileDto.toEntity())
                .build();
    }

}
