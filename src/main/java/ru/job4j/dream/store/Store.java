package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final Store INST = new Store();

    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job","Developer", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job","Tester", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job","DevOps", LocalDateTime.now()));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
//        posts.
        return posts.values();
    }

    private void t(){
        for (Post post : Store.instOf().findAll().toArray(Post[]::new)){
            post.getName();
        }
//        Store.instOf().findAll().
    }
}
