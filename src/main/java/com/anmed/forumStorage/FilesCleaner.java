package com.anmed.forumStorage;

import com.anmed.forumStorage.documents.Comment;
import com.anmed.forumStorage.repositries.CommentRepository;
import com.anmed.forumStorage.services.FileService;
import com.mongodb.gridfs.GridFSDBFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by adementiev on 2016-09-01.
 * <p>
 * cleaner with scheduled job
 */
public class FilesCleaner {
    private static final Logger logger = LoggerFactory.getLogger(FilesCleaner.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    //TODO: replace with getting from some properties file
    //hardcoded to have yesterday as an "old date"
    private static final Date yesterday = getYesterday();
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FileService fileService;

    private static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    //configuration for cleaning files
    @Scheduled(cron = "*/5 * * * * MON-FRI")
    public void cleanUnreferencedFiles() {
        logger.info("cleaner for unrefernced files started at " + dateFormat.format(new Date()));
        Set<String> uniqueFileSetFromComments = getFileIdsFromComments(commentRepository.findAll());
        Set<String> fileSet = getFileIds(fileService.getAllFileIds());
        removeUnreferencedFiles(fileSet, uniqueFileSetFromComments);
    }


    @Scheduled(cron = "*/5 * * * * MON-FRI")
    public void cleanOldFiles() {
        logger.info("cleaner for old files started at " + dateFormat.format(new Date()));
        fileService.removeFilesOlderThan(yesterday);

    }

    private void removeUnreferencedFiles(Set<String> fileSet, Set<String> uniqueFileSetFromComments) {
        fileSet.removeAll(uniqueFileSetFromComments);
        if (fileSet.size() > 0) {
            fileSet.stream().forEach(file -> {
                fileService.removeFile(file);
            });
        }

    }

    private Set<String> getFileIds(List<GridFSDBFile> allFileIds) {
        return allFileIds.stream().map(gridFSDBFile -> gridFSDBFile.getId().toString()).collect(Collectors.toSet());
    }

    private Set<String> getFileIdsFromComments(List<Comment> comments) {
        Set<String> result = new HashSet<>();
        comments.stream().forEach(comment -> {
            result.addAll(comment.getFileList());
        });
        return result;
    }
}


