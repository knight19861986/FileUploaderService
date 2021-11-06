package com.selfmade.fileuploaderservice;

import com.selfmade.fileuploaderservice.storage.IStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.junit.jupiter.api.Assertions;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.selfmade.fileuploaderservice.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploaderServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    //TODO: In production we should use mock services in tests
    @Autowired
    private @Qualifier("main")
    IStorageService storageService;

    @Test
    void testUploadAndDownLoadSingleFile() throws Exception {
        ResponseEntity<String> response;

        response = this.restTemplate.getForEntity(PATH_DOWNLOAD + PATH_SLASH + FILE_NAME, String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        response = postFile(FILE_NAME);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        response = this.restTemplate.getForEntity(PATH_DOWNLOAD + PATH_SLASH + FILE_NAME, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUploadMultipleFiles() throws Exception {
        String urlForGet;
        postFile(FILE_NAME_TXT);
        postFile(FILE_NAME_PNG, new ArrayList<>(Arrays.asList(TAG_FINANCE)));
        postFile(FILE_NAME_PDF, new ArrayList<>(Arrays.asList(TAG_FINANCE, TAG_CONTRACTS)));

        Assertions.assertEquals(3, restTemplate.getForObject(PATH_SLASH, List.class).size());
        Assertions.assertEquals(3, restTemplate.getForObject(PATH_CATEGORY, List.class).size());

        urlForGet = UriComponentsBuilder.fromPath(PATH_CATEGORY)
                .queryParam(PARAM_TAG, TAG_FINANCE).toUriString();
        Assertions.assertEquals(2, restTemplate.getForObject(urlForGet, List.class).size());

        urlForGet = UriComponentsBuilder.fromPath(PATH_CATEGORY)
                .queryParam(PARAM_TAG, TAG_CONTRACTS).toUriString();
        Assertions.assertEquals(1, restTemplate.getForObject(urlForGet, List.class).size());

    }

    private ResponseEntity<String> postFile(String fileName, List<String> tags) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        ClassPathResource resource = new ClassPathResource(fileName, getClass());
        map.add(PARAM_FILE, resource);
        if (tags != null) {
            tags.stream().forEach(tag -> {
                map.add(PARAM_TAG, tag);
            });
        }
        return this.restTemplate.postForEntity(PATH_UPLOAD, map, String.class);
    }

    private ResponseEntity<String> postFile(String fileName) {
        return postFile(fileName, null);
    }

}
