package com.seraptemel.jwtdemo.service.impl;

import com.seraptemel.jwtdemo.dto.FileMetaDto;
import com.seraptemel.jwtdemo.entity.FileMeta;
import com.seraptemel.jwtdemo.property.FileServiceProperties;
import com.seraptemel.jwtdemo.repository.FileMetaRepository;
import com.seraptemel.jwtdemo.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private final Path uploadFolderPath;
    private Path foundFile;

    final FileMetaRepository fileMetaRepository;

    @Autowired
    public FileServiceImpl(FileServiceProperties properties, FileMetaRepository repository) {
        this.fileMetaRepository = repository;
        this.uploadFolderPath = Paths.get(properties.getUploadFolder()).toAbsolutePath();

        try {
            if (!Files.exists(uploadFolderPath)) {
                Files.createDirectories(uploadFolderPath);
            } else {
                log.info("upload folder exists.");
            }
        } catch (IOException e) {
            log.error("Upload folder directory couldn't created.", e);
        }
    }

    @Override
    public FileMetaDto saveMultipartFile(MultipartFile file) {
        FileMetaDto fileMetaDto;
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        if (originalFilename != null) {
            try {
                Path targetLocation = uploadFolderPath.resolve(originalFilename);
                if(Files.exists(targetLocation)){
                    return new FileMetaDto("File already exists in server. You can update.");
                }
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error("Storage Error.", e);
                fileMetaDto = new FileMetaDto(e.getMessage());
                return fileMetaDto;
            }
            long size = file.getSize();
            FileMeta fileMeta = saveFileMeta(originalFilename, contentType, size);
            fileMetaDto = new FileMetaDto(fileMeta);
        } else {
            fileMetaDto = new FileMetaDto("File Not Found.");
        }

        return fileMetaDto;
    }

    private void replaceFile(String originalFilename, MultipartFile file) throws IOException {
        Path targetLocation = uploadFolderPath.resolve(originalFilename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public byte[] getFileAsResource(String fileName) {
        try {
            Files.list(uploadFolderPath).forEach(file -> {
                if (file.getFileName().toString().startsWith(fileName)) {
                    foundFile = file;
                }
            });

            if (foundFile != null) {
                return Files.readAllBytes(foundFile);
            }
        } catch (IOException e) {
            log.error("File found error");
        }
        return null;
    }

    @Override
    public Collection<FileMetaDto> getAllFileMeta() {
        Iterable<FileMeta> all = fileMetaRepository.findAll();
        Collection<FileMetaDto> fileMetaDtos = new ArrayList<>();
        for(FileMeta fileMeta: all){
            FileMetaDto fileMetaDto = new FileMetaDto(fileMeta);
            String fileMetaName = fileMeta.getName();
            fileMetaDtos.add(fileMetaDto);
        }

        return fileMetaDtos;
    }

    @Override
    public FileMetaDto updateFileMeta(MultipartFile file, Long id) {
        Optional<FileMeta> optionalFileMeta = fileMetaRepository.findById(id);

        if(optionalFileMeta.isPresent()){
            FileMeta fileMeta = optionalFileMeta.get();
            try {
                deleteFile(fileMeta.getName());
            } catch (IOException e) {
                log.error("File not found on server.");
            }

            try {
                Path targetLocation = uploadFolderPath.resolve(fileMeta.getName());
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error("File update error. ",e);
                return new FileMetaDto(String.format("File Update Error. %s",e.getMessage()));
            }

            fileMeta.setUpdateTime(LocalDateTime.now());
            fileMetaRepository.save(fileMeta);
            return new FileMetaDto(fileMeta);
        }else{
            return new FileMetaDto("record not found on db");
        }
    }

    @Override
    public FileMetaDto deleteFileMeta(long id) {

        Optional<FileMeta> optionalFileMeta = fileMetaRepository.findById(id);

        if(optionalFileMeta.isPresent()){
            FileMeta fileMeta = optionalFileMeta.get();
            FileMetaDto fileMetaDto = new FileMetaDto(fileMeta);
            fileMetaRepository.deleteById(id);
            try {
                deleteFile(fileMeta.getName());
            } catch (IOException e) {
                log.error("File not found.");
                return new FileMetaDto("File not found on server, db record deleted.");
            }
            return fileMetaDto;

        }else{
            return new FileMetaDto("record not found on db");
        }
    }

    private void deleteFile(String fileName) throws IOException {
        Files.list(uploadFolderPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileName)) {
                foundFile = file;
            }
        });
        Files.delete(foundFile);
    }

    @Override
    public FileMetaDto getFileMeta(long id) {
        FileMeta fileMeta = fileMetaRepository.findById(id).get();
        FileMetaDto fileMetaDto = new FileMetaDto(fileMeta);
        return fileMetaDto;
    }

    private FileMeta saveFileMeta(String originalFilename, String contentType, long size) {
        FileMeta fileMeta = new FileMeta();
        fileMeta.setCreateTime(LocalDateTime.now());
        fileMeta.setName(originalFilename);
        fileMeta.setContentType(contentType);
        fileMeta.setSizeBytes(size);

        fileMetaRepository.save(fileMeta);
        return fileMeta;
    }


}
