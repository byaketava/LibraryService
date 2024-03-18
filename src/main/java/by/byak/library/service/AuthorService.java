package by.byak.library.service;

import by.byak.library.dto.author.AuthorDTO;
import by.byak.library.entity.Author;
import by.byak.library.entity.Book;
import by.byak.library.mapper.author.AuthorDTOMapper;
import by.byak.library.repository.AuthorRepository;
import by.byak.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorDTOMapper authorMapper;
    private final BookRepository bookRepository;

    public List<AuthorDTO> findAllAuthors() {
        return authorRepository.findAll().stream().map(authorMapper).toList();
    }

    public AuthorDTO findByName(String name) {
        Author author = authorRepository.findByName(name);

        if (author == null) {
            return null;
        }

        return authorMapper.apply(author);
    }

    public Optional<Author> addAuthor(Author author) {
        if (authorRepository.existsByName(author.getName())) {
            return Optional.empty();
        }

        List<Book> books = author.getBooks();
        List<Book> allBooks = new ArrayList<>();

        for (Book book : books) {
            book.setAuthor(author);
            Book savedBook = bookRepository.save(book);
            allBooks.add(savedBook);
        }

        author.setBooks(allBooks);

        return Optional.of(authorRepository.save(author));
    }

    public boolean deleteAuthorByName(String name) {
        Author author = authorRepository.findByName(name);

        if (author != null) {
            bookRepository.deleteAll(author.getBooks());
            authorRepository.delete(author);
            return true;
        }

        return false;
    }

    public boolean updateAuthorName(Long id, String newName) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setName(newName);
            authorRepository.save(author);
            return true;
        }

        return false;
    }
}
