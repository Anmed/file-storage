package com.anmed.forumStorage.config;

import com.anmed.forumStorage.FileStorageManager;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import javax.servlet.MultipartConfigElement;

/**
 * Created by adementiev on 2016-08-31.
 */

@Configuration
public class AppConfig extends AbstractMongoConfiguration {
    //here we can specify restriction for downloading files
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("128000000KB");
        factory.setMaxRequestSize("12800000KB");
        return factory.createMultipartConfig();
    }

    @Bean
    public FileStorageManager fileStorageManager(){
        return new FileStorageManager();
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }


    @Override
    protected String getDatabaseName() {
        return "filestorage";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1");
    }


    @Bean
    public MongoDatabase dbCollection() throws Exception {
         MongoClient mongoClient =  (MongoClient) mongo();
        return mongoClient.getDatabase(getDatabaseName());
    }

}
