package com.anmed.forumStorage.services;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by adementiev on 2016-08-30.
 */
public interface FileService {
     String persistFile(MultipartFile file) throws IOException;

     GridFSDBFile getFile(String fileId);

     DBObject getFileMetaData(String fileId);

     void isUnique();
}
