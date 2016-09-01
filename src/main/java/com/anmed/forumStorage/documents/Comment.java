package com.anmed.forumStorage.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anmed on 01.09.2016.
 */
@Data
@Document(collection = "comments")
public class Comment {
   public final static String COMMENTS = "comments";

    @Id
    private String id;

    private List<String> fileList = new ArrayList<>();

}
