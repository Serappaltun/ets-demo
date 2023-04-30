package com.seraptemel.jwtdemo.controller;

import com.seraptemel.jwtdemo.dto.FileMetaDto;
import com.seraptemel.jwtdemo.service.FileService;
import com.seraptemel.jwtdemo.validator.ValidFile;
import com.seraptemel.jwtdemo.service.impl.FileServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FileController {

    FileService fileService;

    @Autowired
    public FileController(FileServiceImpl fs) {
        this.fileService = fs;
    }

    @ApiOperation("Upload File")
    @PostMapping("/upload")
    public FileMetaDto uploadFile(@Valid @ValidFile() @RequestParam("file") MultipartFile file) {
        FileMetaDto fileMetaDto = fileService.saveMultipartFile(file);
        return fileMetaDto;
    }

    @ApiOperation("Download File")
    @GetMapping("/download-file/{fileName}")
    public byte[] downloadFile(@PathVariable("fileName") String fileName) {
        return fileService.getFileAsResource(fileName);
    }

    @ApiOperation("Files")
    @GetMapping("/list-files")
    public Collection<FileMetaDto> listFiles() {
        return fileService.getAllFileMeta();
    }


    @ApiOperation("Update File")
    @PutMapping("/update-file/{id}")
    public FileMetaDto updateFile(@Valid @ValidFile() @RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
        return fileService.updateFileMeta(file, id);
    }


    @ApiOperation("Delete File")
    @DeleteMapping("/delete-file/{id}")
    public FileMetaDto deleteFile(@Validated @PathVariable("id") Long id) {
        return fileService.deleteFileMeta(id);
    }

    @ApiOperation("Select File By Id")
    @GetMapping("/select-file/{id}")
    public FileMetaDto getFile(@Validated @PathVariable("id") Long id) {
      return fileService.getFileMeta(id);
    }

}

