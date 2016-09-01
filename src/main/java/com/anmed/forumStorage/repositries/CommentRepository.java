package com.anmed.forumStorage.repositries;

import com.anmed.forumStorage.documents.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Anmed on 01.09.2016.
 */
public interface CommentRepository extends MongoRepository<Comment, String> {
}
