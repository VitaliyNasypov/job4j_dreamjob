package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post, User user);

    void save(Candidate candidate, User user);

    Post findByIdPost(int id);

    Candidate findByIdCandidate(int id);

    User findByUser(String email, String password);
}
