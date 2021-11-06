package com.selfmade.fileuploaderservice.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Category implements ICategory{
    private final Map<String, Set<String>> tags;

    public Category() {
        this.tags = new ConcurrentHashMap<>();
    }

    @Override
    public void addFile(String tag, String fileName) {
        if (!tags.containsKey(tag)) {
            tags.put(tag, ConcurrentHashMap.newKeySet());
        }
        tags.get(tag).add(fileName);
    }

    @Override
    public List<String> index(String tag){
        List<String> ret = new ArrayList<>(tags.getOrDefault(tag, new HashSet<String>()));
        Collections.sort(ret);
        return ret;
    }
}



