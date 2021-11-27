package com.selfmade.fileuploaderservice.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStorageService {

    void init();
    void storeFile(MultipartFile file);
    boolean fileExisting(String fileName);
    Resource loadFile(String fileName);
    List<String> listFiles();
    void clear();
}
