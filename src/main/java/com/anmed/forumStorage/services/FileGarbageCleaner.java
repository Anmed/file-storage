package com.anmed.forumStorage.services;

/**
 * Created by adementiev on 2016-08-30.
 */
public interface FileGarbageCleaner {

    void cleanUnreferencedFiles();

    void cleanOldFiles();


}
