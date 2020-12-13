package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {

    private static final Store INST = new Store();

    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job","Developer", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job","Tester", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job","DevOps", LocalDateTime.now()));
        candidates.put(1, new Candidate(1, "Киселёв","Людвиг", 26));
        candidates.put(2, new Candidate(2, "Третьяков","Аскольд", 48));
        candidates.put(3, new Candidate(3, "Андреев","Олег", 34));
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}
