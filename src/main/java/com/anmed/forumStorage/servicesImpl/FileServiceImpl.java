package com.anmed.forumStorage.servicesImpl;

import com.anmed.forumStorage.services.FileService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Anmed on 2016-08-30.
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public String persistFile(MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("size", file.getSize());
        metaData.put("contentType", file.getContentType());
        metaData.put("originalFileName", file.getOriginalFilename());
        GridFSFile createdFile =  gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData);
        return createdFile.getId().toString();
    }

    @Override
    public GridFSDBFile getFile(String fileId) {
      return     gridFsTemplate.find(new Query().addCriteria(Criteria.where("_id").is(fileId))).get(0);
    }

    @Override
    public DBObject getFileMetaData(String fileId) {
      return gridFsTemplate.find(new Query().addCriteria(Criteria.where("_id").is(fileId))).get(0).getMetaData();
    }

    @Override
    public void isUnique() {

    }

    @Override
    public List<GridFSDBFile> getAllFileIds() {
        return gridFsTemplate.find(null);
    }

    @Override
    public void removeFile(String fileId) {
        gridFsTemplate.delete(new Query().addCriteria(Criteria.where("_id").is(fileId)));
    }

    @Override
    public void removeFilesOlderThan(Date yesterday) {
        gridFsTemplate.delete(new Query().addCriteria(Criteria.where("uploadDate").lt(yesterday)));
    }
}
