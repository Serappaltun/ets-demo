package com.seraptemel.jwtdemo.dto;

import com.seraptemel.jwtdemo.entity.FileMeta;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileMetaDto extends AbstractDto {
    private Long id;
    private String name;
    private String contentType;
    private Long sizeBytes;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public FileMetaDto(FileMeta fileMeta){
        setSuccess(true);
        this.id = fileMeta.getId();
        this.name = fileMeta.getName();
        this.contentType = fileMeta.getContentType();
        this.sizeBytes = fileMeta.getSizeBytes();
        this.createTime = fileMeta.getCreateTime();
        this.updateTime = fileMeta.getUpdateTime();
    }

    public FileMetaDto(String errorMessage){
        setSuccess(false);
        setErrorMessage(errorMessage);
    }
}
