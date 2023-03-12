package com.seraptemel.etsdemo.controller;
import static org.mockito.BDDMockito.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seraptemel.etsdemo.dto.FileMetaDto;
import com.seraptemel.etsdemo.entity.FileMeta;
import com.seraptemel.etsdemo.service.impl.FileServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class FileControllerMockMvcStandaloneTest {
    private InputStream is;
    private MockMvc mockMvc;
    @Mock
    private FileServiceImpl mockFileService;

    @Spy
    @InjectMocks
    private FileController controller;

    @Before
    public void init() throws FileNotFoundException {
        //controller.fileService = mockFileService;
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        File file = new File("uploads-test/swagger1.png");
        is = new FileInputStream(file);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void uploadFileTest() throws Exception {
        FileMeta fileMeta = new FileMeta();
        fileMeta.setId(1L);
        fileMeta.setName("swagger1.png");
        given(mockFileService.saveMultipartFile(Mockito.any())).willReturn(new FileMetaDto(fileMeta));

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "swagger1.png", "multipart/form-data", is);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void listFilesTest() throws Exception {
        String uri = "/list-files";

        Collection<FileMetaDto> fileMetaDtos = new ArrayList<>();
        for(int i = 0; i<5; i++){
            FileMeta fileMeta = new FileMeta((long)i,"file"+i,"png",(long)100+i, LocalDateTime.now(),LocalDateTime.now());
            FileMetaDto fileMetaDto = new FileMetaDto(fileMeta);
            fileMetaDtos.add(fileMetaDto);
        }

        given(mockFileService.getAllFileMeta()).willReturn(fileMetaDtos);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
        Collection<FileMetaDto> fileListResult = mapFromJson(result.getResponse().getContentAsString(),Collection.class);
        Assert.assertEquals(5,fileListResult.size());
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }

    /*
    update end point should be post mapping for this unit test.

    @Test
    public void updateFileTest() throws Exception {
        long fileMetaId = 5;
        String uri = "/update-file/"+fileMetaId;
        FileMeta fileMeta = new FileMeta(fileMetaId,"swagger1.png","png",100L, LocalDateTime.now(),LocalDateTime.now());
        FileMetaDto fileMetaDto = new FileMetaDto(fileMeta);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "swagger1.png", "multipart/form-data", is);

        when(mockFileService.updateFileMeta(mockMultipartFile,fileMetaId)).thenReturn(fileMetaDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart(uri)
                        .file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());
        verify(mockFileService,times(1)).updateFileMeta(mockMultipartFile,fileMetaId);

    }
     */

    @Test
    public void deleteFileTest() throws Exception {
        long fileMetaId = 5;
        String uri = "/delete-file/"+fileMetaId;
        FileMeta fileMeta = new FileMeta(fileMetaId,"swagger1.png","png",100L, LocalDateTime.now(),LocalDateTime.now());
        FileMetaDto fileMetaDto = new FileMetaDto(fileMeta);

        when(mockFileService.deleteFileMeta(fileMetaId)).thenReturn(fileMetaDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertNotNull(result.getResponse().getContentAsString());
        verify(mockFileService,times(1)).deleteFileMeta(fileMetaId);

    }
}
