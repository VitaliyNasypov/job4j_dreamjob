package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeMockStore implements Store {
    private static final FakeMockStore INST = new FakeMockStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(3);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(3);
    private static final AtomicInteger USER_ID = new AtomicInteger(1);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private FakeMockStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Requirements: Java Core",
                LocalDateTime.now(ZoneId.of("UTC"))));
        posts.put(2, new Post(2, "Middle Java Job", "Requirements: Spring, Hibernate",
                LocalDateTime.now(ZoneId.of("UTC"))));
        posts.put(3, new Post(3, "Senior Java Job", "Requirements: Software Architect",
                LocalDateTime.now(ZoneId.of("UTC"))));
        candidates.put(1, new Candidate(1, "Киселёв", "Людвиг", 26));
        candidates.put(2, new Candidate(2, "Третьяков", "Аскольд", 48));
        candidates.put(3, new Candidate(3, "Андреев", "Олег", 34));
        User user = new User();
        user.setId(1);
        user.setName("Test");
        user.setEmail("test@test.ru");
        user.setPassword("test");
        user.setGroup("hr");
        users.put(1, user);
    }

    public static FakeMockStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public void save(Post post, User user) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), new Post(post.getId(),
                post.getName(), post.getDescription(), post.getCreated()));
    }

    @Override
    public void save(Candidate candidate, User user) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), new Candidate(candidate.getId(),
                candidate.getFirstName(), candidate.getLastName(), candidate.getAge()));
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    @Override
    public User findByUser(String email, String password) {
        User user = new User();
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            if (entry.getValue().getEmail().equals(email)
                    && entry.getValue().getPassword().equals(password)) {
                user = entry.getValue();
            }
        }
        return user;
    }

    @Override
    public boolean isUserCreated(String email) {
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            if (entry.getValue().getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> findByCity(String findCity) {
        return null;
    }
}
