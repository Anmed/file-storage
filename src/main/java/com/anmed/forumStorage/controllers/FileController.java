package com.anmed.forumStorage.controllers;

import com.anmed.forumStorage.FileStorageManager;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adementiev on 2016-08-30.
 *
 * Since user can save files without comment we do not need here any info about comments
 */

@RestController
public class FileController {
    Logger logger = Logger.getLogger(FileController.class);


    @Autowired
    private FileStorageManager fileStorageManager;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file){
        try {
            return "created file with id ".concat(fileStorageManager.persistFile(file));
        } catch (IOException e) {
           logger.error("error occurred during storing the fil",e);
        }
        return "error occurred please check logs";
    }

    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(@PathVariable("fileId") String fileId, HttpServletResponse response){
        GridFSDBFile file = fileStorageManager.getFile(fileId);
        try {
            IOUtils.copy(file.getInputStream(), response.getOutputStream());
            response.setContentType(file.getContentType());
            response.setContentLength(Math.toIntExact(file.getLength()));
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("error occurred during reading the file",e);
        }
    }

    @RequestMapping(value = "/meta/{fileId}", method = RequestMethod.GET)
    public DBObject fileMetaData(@PathVariable("fileId") String fileId){
        return fileStorageManager.getFileMetaData(fileId);
    }

}
