package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static AtomicInteger POST_ID = new AtomicInteger(3);

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "Requirements: Java Core"));
        posts.put(2, new Post(2, "Middle Java Job", "Requirements: Spring, Hibernate"));
        posts.put(3, new Post(3, "Senior Java Job", "Requirements: Software Architect"));
        candidates.put(1, new Candidate(1, "Киселёв", "Людвиг", 26));
        candidates.put(2, new Candidate(2, "Третьяков", "Аскольд", 48));
        candidates.put(3, new Candidate(3, "Андреев", "Олег", 34));
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

    public int getSizePosts() {
        return posts.size();
    }

    public int getSizeCandidates() {
        return candidates.size();
    }

    public void save(String name, String description) {
        int id = POST_ID.incrementAndGet();
        posts.put(id, new Post(id, name, description));
    }
}
