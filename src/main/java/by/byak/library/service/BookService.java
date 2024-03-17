package by.byak.library.service;

import by.byak.library.entity.Author;
import by.byak.library.entity.Book;
import by.byak.library.entity.Genre;
import by.byak.library.repository.AuthorRepository;
import by.byak.library.repository.BookRepository;
import by.byak.library.repository.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findByTitle(String title) {
        Book book = bookRepository.findByTitle(title);

        if (book == null) {
            return null;
        }

        return book;
    }

    public Optional<Book> addBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle())) {
            return Optional.empty();
        }

        List<Genre> genres = book.getGenres();
        List<Genre> allGenres = new ArrayList<>();

        for (Genre genre : genres) {
            Genre existingGenre = genreRepository.findByName(genre.getName());
            if (existingGenre != null) {
                allGenres.add(existingGenre);
            } else {
                Genre savedGenre = genreRepository.save(genre);
                allGenres.add(savedGenre);
            }
        }

        Author author = book.getAuthor();
        Author existingAuthor = authorRepository.findByName(author.getName());

        if (existingAuthor != null) {
            book.setAuthor(existingAuthor);
            existingAuthor.getBooks().add(book);
        } else {
            Author savedAuthor = authorRepository.save(author);
            book.setAuthor(savedAuthor);
            savedAuthor.getBooks().add(book);
        }

        book.setGenres(allGenres);
        return Optional.of(bookRepository.save(book));
    }


    public boolean deleteBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title);

        if (book != null) {
            bookRepository.delete(book);
            return true;
        }

        return false;
    }

    public boolean updateBook(Long id, String title) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(title);
            bookRepository.save(book);
            return true;
        }

        return false;
    }
}
