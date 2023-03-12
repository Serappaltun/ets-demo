package com.seraptemel.etsdemo.service;

import com.seraptemel.etsdemo.dto.FileMetaDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface FileService {

    FileMetaDto saveMultipartFile(MultipartFile file);

    byte[] getFileAsResource(String fileName);

    Collection<FileMetaDto> getAllFileMeta();

    FileMetaDto updateFileMeta(MultipartFile file, Long id);

    FileMetaDto deleteFileMeta(long id);

    FileMetaDto getFileMeta(long id);
}
