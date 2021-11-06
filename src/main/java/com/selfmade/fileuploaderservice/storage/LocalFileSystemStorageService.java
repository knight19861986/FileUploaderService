package com.selfmade.fileuploaderservice.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("main")
public class LocalFileSystemStorageService implements IStorageService {

    private final Path filesRootPath;

    public Path getFilesRootPath() {
        return this.filesRootPath;
    }

    @Autowired
    public LocalFileSystemStorageService(LocalFileSystemStorageServiceProperties properties) throws IOException {
        this.filesRootPath = Paths.get(properties.getRootDir()).toAbsolutePath().normalize();
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(this.filesRootPath))
                Files.createDirectories(this.filesRootPath);
        } catch (IOException e) {
            throw new StorageException("Failed to create root directory of uploaded files.", e);
        }
    }

    @Override
    public void storeFile(MultipartFile file) {
        try {
            File newFile = new File(this.filesRootPath + "/" + file.getOriginalFilename());
            newFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Failed to upload file: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path file = this.filesRootPath.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new StorageException("Could not read file: " + fileName, e);
        }
    }

    @Override
    public List<String> listFiles() {
        try {
            List<String> ret = Files.walk(this.filesRootPath, 1)
                    .filter(path -> !path.equals(this.filesRootPath))
                    .map(path -> path.toFile().getName()).collect(Collectors.toList());
            Collections.sort(ret);
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Failed to list all files.", e);
        }
    }

    @Override
    public void clear() {
        FileSystemUtils.deleteRecursively(this.filesRootPath.toFile());

    }

}
