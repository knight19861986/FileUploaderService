package com.selfmade.fileuploaderservice;

import com.selfmade.fileuploaderservice.repo.Category;
import com.selfmade.fileuploaderservice.repo.ICategory;
import com.selfmade.fileuploaderservice.storage.IStorageService;
import com.selfmade.fileuploaderservice.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PreDestroy;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


@RestController
public class Controller {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private final IStorageService storageService;
    private final ICategory category;

    @Autowired
    public Controller(@Qualifier("main") IStorageService storageService) {
        this.category = new Category();
        this.storageService = storageService;
        storageService.init();
    }

    @GetMapping("/")
    public List<String> listAllFiles() {
        return this.storageService.listFiles();
    }

    @GetMapping("/category")
    public List<String> listFilesInCategory(@RequestParam(value = "tag", defaultValue = "") String tag) {
        if (tag.isEmpty())
            return this.listAllFiles();
        return this.category.index(tag);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource newFile = this.storageService.loadFile(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + newFile.getFilename() + "\"").body(newFile);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "tag", required = false) String[] tags) {
        this.storageService.storeFile(file);
        if (tags != null && tags.length > 0) {
            Arrays.stream(tags).forEach(tag -> {
                this.category.addFile(tag, file.getOriginalFilename());
            });
        }
        return "Succeeded to upload file: " + file.getOriginalFilename() + "\n";
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageException(StorageException e) {
        return ResponseEntity.notFound().build();
    }

    //TODO: In production maybe we don't need to clear all files after shutting down
    @PreDestroy
    void clearFiles() {
        this.storageService.clear();
    }

}
