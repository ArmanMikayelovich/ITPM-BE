package com.energizeglobal.itpm.service;

import com.energizeglobal.itpm.model.dto.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String saveToFileSystem(MultipartFile file, String name);

    void saveFileInfoToDataBase(String fileName, String path, Long taskId);

    void saveFile(MultipartFile file, Long taskId);

    byte[] getFile(Long fileInfoId);

    List<FileInfoDto> getAllByTaskId(Long taskId);

    FileInfoDto findById(Long fileId);
}
