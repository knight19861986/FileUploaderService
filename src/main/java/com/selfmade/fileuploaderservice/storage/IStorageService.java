package com.selfmade.fileuploaderservice.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStorageService {

    void init();
    void storeFile(MultipartFile file);
    Resource loadFile(String fileName);
    List<String> listFiles();
    void clear();
}
