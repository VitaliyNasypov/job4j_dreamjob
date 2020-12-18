package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Test","Test", LocalDateTime.now(ZoneId.of("UTC"))));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName() + " " + post.getDescription() + " " + post.getCreatedToString());
        }
        store.save(new Post(2, "Test2","Test2", LocalDateTime.now(ZoneId.of("UTC"))));
        System.out.println(store.findByIdPost(2).getCreatedToString());
        store.save(new Candidate(0, "Киселёв", "Людвиг", 26));
        System.out.println(store.getSizePosts());
    }
}
