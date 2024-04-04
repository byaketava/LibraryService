package by.byak.library.service;

import by.byak.library.cache.InMemoryCache;
import by.byak.library.dto.author.AuthorDTO;
import by.byak.library.entity.Author;
import by.byak.library.entity.Book;
import by.byak.library.mapper.author.AuthorDTOMapper;
import by.byak.library.repository.AuthorRepository;
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
    private final AuthorDTOMapper authorMapper;
    private  final InMemoryCache<Integer, Author> cache;

    public List<AuthorDTO> findAllAuthors() {
        return authorRepository.findAll().stream().map(authorMapper).toList();
    }

    public AuthorDTO findByName(String name) {
        Author cachedAuthor = cache.get(name.hashCode());
        if (cachedAuthor != null) {
            return authorMapper.apply(cachedAuthor);
        }

        Author author = authorRepository.findByName(name);
        if (author == null) {
            return null;
        }

        cache.put(name.hashCode(), author);

        return authorMapper.apply(author);
    }

    public Optional<Author> addAuthor(Author author) {
        if (authorRepository.existsByName(author.getName())) {
            return Optional.empty();
        }

        return Optional.of(authorRepository.save(author));
    }

    public boolean deleteAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            authorRepository.delete(author);
            cache.remove(author.getName().hashCode());
            return true;
        }

        return false;
    }

    public boolean updateAuthor(Long id, Author author) {
        Author existingAuthor = authorRepository.findById(id).orElse(null);

        if (existingAuthor != null) {
            cache.remove(author.getName().hashCode());

            existingAuthor.setName(author.getName());
            existingAuthor.setBooks(author.getBooks());
            for (Book book : author.getBooks()) {
                book.setAuthor(existingAuthor);
            }
            authorRepository.save(existingAuthor);
            return true;
        }

        return false;
    }
}
