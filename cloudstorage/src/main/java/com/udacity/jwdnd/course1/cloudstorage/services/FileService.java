package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveFile(File file) {
        return fileMapper.insert(file);
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getById(fileId);
    }

    public int deleteFile(Integer fileId) {
        return fileMapper.delete(fileId);
    }

    public List<File> getFilesByUserId(Integer userId) {
        return fileMapper.getAllByUserId(userId);
    }

    public int countByFilenameAndUserId(String fileName, Integer userId){
        return fileMapper.countByFilenameAndUserId(fileName,userId);
    }
}
