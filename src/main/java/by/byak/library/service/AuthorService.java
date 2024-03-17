package by.byak.library.service;

import by.byak.library.entity.Author;
import by.byak.library.repository.AuthorRepository;
import by.byak.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author findByName(String name) {
        Author author = authorRepository.findByName(name);

        if (author == null) {
            return null;
        }

        return author;
    }

    public Optional<Author> addAuthor(Author author) {
        if (authorRepository.existsByName(author.getName())) {
            return Optional.empty();
        }

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
