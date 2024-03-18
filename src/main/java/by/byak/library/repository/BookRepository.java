package by.byak.library.repository;

import by.byak.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitle(String title);

    boolean existsByTitle(String title);

    void deleteBookById (Long id);
}
