package com.example.catalog.data.repository;

import com.example.catalog.data.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Locale;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    default List<Book> search(String query) {
        if (query == null || query.isBlank()) {
            return findAll();
        }

        String[] keywords = query.strip().toLowerCase(Locale.ROOT).split("\\s+");
        return findAll((root, criteriaQuery, builder) -> {
            var matches = builder.conjunction();
            for (String keyword : keywords) {
                // Each keyword must occur in at least one of the three fields.
                var keywordMatches = builder.or(
                        builder.greaterThan(builder.locate(builder.lower(root.get("title")), keyword), 0),
                        builder.greaterThan(builder.locate(builder.lower(root.get("description")), keyword), 0),
                        builder.greaterThan(builder.locate(builder.lower(root.get("author")), keyword), 0)
                );
                matches = builder.and(matches, keywordMatches);
            }
            return matches;
        });
    }
}
