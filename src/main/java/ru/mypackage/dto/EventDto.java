package ru.mypackage.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mypackage.model.EventEntity;
import ru.mypackage.model.FileEntity;
import ru.mypackage.model.UserEntity;

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
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("file_id")
    private Long fileId;
    @Expose
    private UserEntity userEntity;
    @Expose
    private FileEntity fileEntity;


    public static EventDto fromEntity(EventEntity eventEntity){
        return EventDto.builder()
                .id(eventEntity.getId())
                .description(eventEntity.getDescription())
                .date(eventEntity.getDate())
                .userId(eventEntity.getUserEntity().getId())
                .fileId(eventEntity.getFileEntity().getId())
                .build();
    }

    public EventEntity toEntity(){
        return EventEntity.builder()
                .id(id)
                .description(description)
                .date(date)
                .userEntity(userEntity)
                .fileEntity(fileEntity)
                .build();
    }

}
