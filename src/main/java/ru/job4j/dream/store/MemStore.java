package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static AtomicInteger POST_ID = new AtomicInteger(3);
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(3);

    private MemStore() {
//        posts.put(1, new Post(1, "Junior Java Job", "Requirements: Java Core"));
//        posts.put(2, new Post(2, "Middle Java Job", "Requirements: Spring, Hibernate"));
//        posts.put(3, new Post(3, "Senior Java Job", "Requirements: Software Architect"));
        candidates.put(1, new Candidate(1, "Киселёв", "Людвиг", 26));
        candidates.put(2, new Candidate(2, "Третьяков", "Аскольд", 48));
        candidates.put(3, new Candidate(3, "Андреев", "Олег", 34));
    }

    public static MemStore instOf() {
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

    public void savePost(String idForEdit, String name, String description) {
        int id;
        if (idForEdit.equals("0")) {
            id = POST_ID.incrementAndGet();
        } else {
            id = Integer.parseInt(idForEdit);
        }
//        posts.put(id, new Post(id, name, description));
    }

    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    public void saveCandidate(String idForEdit, String firstName, String lastName, String age) {
        int id;
        if (idForEdit.equals("0")) {
            id = CANDIDATE_ID.incrementAndGet();
        } else {
            id = Integer.parseInt(idForEdit);
        }
        int ageCandidate = age.matches("^(?!-|0(?:\\.0*)?$)\\d+(?:\\d+)?$") ? Integer.parseInt(age) : 0;
        candidates.put(id, new Candidate(id, firstName, lastName, ageCandidate));
    }

    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }
}
