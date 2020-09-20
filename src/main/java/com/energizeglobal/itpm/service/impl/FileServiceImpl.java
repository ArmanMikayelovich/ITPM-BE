package com.energizeglobal.itpm.service.impl;

import com.energizeglobal.itpm.model.FileInfoEntity;
import com.energizeglobal.itpm.model.TaskEntity;
import com.energizeglobal.itpm.model.dto.FileInfoDto;
import com.energizeglobal.itpm.repository.FileInfoRepository;
import com.energizeglobal.itpm.service.FileService;
import com.energizeglobal.itpm.service.Mapper;
import com.energizeglobal.itpm.service.TaskService;
import com.energizeglobal.itpm.util.exceptions.FileSystemException;
import com.energizeglobal.itpm.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final TaskService taskService;
    private final FileInfoRepository fileInfoRepository;
    private final Mapper mapper;

    @Override
    public String saveToFileSystem(MultipartFile multipartFile, String name) {
        final String userDirectory = System.getProperty("user.dir");
        final String path = userDirectory + "\\tasks\\files\\";
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException exception) {
            throw new FileSystemException(exception.getMessage());
        }

        String fileName = path + name;
        File file;
        BufferedOutputStream stream = null;
        try {
            byte[] bytes = multipartFile.getBytes();
            file = new File(fileName);
            file.createNewFile();
            stream =
                    new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            throw new FileSystemException(e.getMessage());
        } finally {
            if (stream != null) {
                try {

                    stream.close();
                } catch (IOException e) {
                    e.addSuppressed(e);
                    //TODO harcnel Marinayin
                }
            }
        }
        return file.getAbsolutePath();
    }

    @Override
    @Transactional
    public void saveFileInfoToDataBase(String fileName, String path, Long taskId) {
        final FileInfoEntity fileInfoEntity = new FileInfoEntity();
        fileInfoEntity.setFileName(fileName);
        fileInfoEntity.setFilePath(path);
        final TaskEntity taskEntity = taskService.findEntityById(taskId);
        fileInfoEntity.setOwnerTaskEntity(taskEntity);
        fileInfoRepository.save(fileInfoEntity);
    }

    @Override
    @Transactional
    public void saveFile(MultipartFile file, Long taskId) {
        String fileName = "_" + taskId + "_" + file.getOriginalFilename();
        String filePath = saveToFileSystem(file, fileName);
        saveFileInfoToDataBase(file.getOriginalFilename(), filePath, taskId);
    }


    @Override
    @Transactional(readOnly = true)
    public byte[] getFile(Long fileInfoId) {
        final FileInfoEntity infoEntity = findEntityById(fileInfoId);

        File serverFile = new File(infoEntity.getFilePath());

        try {
            return Files.readAllBytes(serverFile.toPath());
        } catch (IOException exception) {
            throw new FileSystemException(exception.getMessage());
        }
    }

    @Override
    public List<FileInfoDto> getAllByTaskId(Long taskId) {
        final TaskEntity taskEntity = taskService.findEntityById(taskId);

        return fileInfoRepository.findAllByOwnerTaskEntityAndIsDeletedFalse(taskEntity)
                .stream()
                .map(fileInfoEntity -> mapper.map(fileInfoEntity, new FileInfoDto()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FileInfoEntity findEntityById(Long fileInfoId) {
        return fileInfoRepository.findById(fileInfoId)
                .orElseThrow(() -> new NotFoundException("File with id: " + fileInfoId + " not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public FileInfoDto findById(Long fileId) {
        final FileInfoEntity infoEntity = findEntityById(fileId);
        return mapper.map(infoEntity, new FileInfoDto());
    }
}
