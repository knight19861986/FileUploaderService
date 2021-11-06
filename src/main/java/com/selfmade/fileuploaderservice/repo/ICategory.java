package com.selfmade.fileuploaderservice.repo;

import java.util.List;

public interface ICategory{
    //TODO: Future work to implement the persistence of data in category
    void addFile(String tag, String fileName);
    List<String> index(String tag);
}
