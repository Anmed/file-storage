package com.anmed.forumStorage;

import com.anmed.forumStorage.services.FileService;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Anmed on 01 09 2016.
 */
public class FileStorageManager {

    @Autowired
    @Qualifier(value = "fileService")
    private FileService fileService;

    public String persistFile(MultipartFile file) throws IOException {
       return fileService.persistFile(file);
    }

    public  void collectGarbage(){}

    public GridFSDBFile getFile(String fileId){
        return fileService.getFile(fileId);
    }

    public DBObject getFileMetaData(String fileId){
        return fileService.getFileMetaData(fileId);
    }

}
