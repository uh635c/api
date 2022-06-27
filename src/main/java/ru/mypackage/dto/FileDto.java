package ru.mypackage.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mypackage.model.FileEntity;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    @SerializedName("id")
    private Long id;
    @SerializedName("location")
    private String location;
    @SerializedName("size")
    private long size;

    public static FileDto fromEntity(FileEntity fileEntity){
        return FileDto.builder()
                .id(fileEntity.getId())
                .location(fileEntity.getLocation())
                .size(fileEntity.getSize())
                .build();
    }

    public  FileEntity toEntity(){
        return FileEntity.builder()
                .id(id)
                .location(location)
                .size(size)
                .build();
    }
}
