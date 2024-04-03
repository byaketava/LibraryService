package by.byak.library.repository;

import by.byak.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);

    boolean existsByTitle(String title);

    @Query("SELECT b FROM Book b JOIN b.genres g JOIN b.author a WHERE a.id = :authorId AND g.id = :genreId")
    List<Book> findByAuthorIdAndGenreId(@Param("authorId") Long authorId,
                                        @Param("genreId") Long genreId);
}