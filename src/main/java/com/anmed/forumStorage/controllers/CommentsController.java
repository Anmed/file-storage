package com.anmed.forumStorage.controllers;

import com.anmed.forumStorage.documents.Comment;
import com.anmed.forumStorage.repositries.CommentRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Anmed on 01.09.2016.
 */
@RestController
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    //storing comment info will be implemented in future, so far it is just creating an Comment Object
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public String createComment(){
        Comment comment = new Comment();
        commentRepository.save(comment);
        return "comment created with id ".concat(comment.getId());
    }

    @RequestMapping(value = "/assignComment/{commentId}/{fileId}", method = RequestMethod.PUT)
    public void assignCommentToFile(@PathVariable("commentId") String commentId, @PathVariable("fileId") String fileId){
        Comment comment = commentRepository.findOne(commentId);
        comment.getFileList().add(fileId);
        commentRepository.save(comment);
    }


}
