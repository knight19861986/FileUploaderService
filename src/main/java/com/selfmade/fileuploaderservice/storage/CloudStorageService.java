package com.selfmade.fileuploaderservice.storage;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CloudStorageService implements IStorageService {

    //TODO: Future work to implement the storage layer service by cloud solution
    @Override
    public void init() {

    }

    @Override
    public void storeFile(MultipartFile file) {
    }

    @Override
    public Resource loadFile(String fileName) {

        return null;
    }

    @Override
    public List<String> listFiles() {
        return null;
    }

    @Override
    public void clear() {

    }
}
