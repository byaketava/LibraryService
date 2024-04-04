package by.byak.library.service;

import by.byak.library.cache.InMemoryCache;
import by.byak.library.dto.book.BookDTO;
import by.byak.library.entity.Book;
import by.byak.library.mapper.book.BookDTOMapper;
import by.byak.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final BookDTOMapper bookMapper;
    private  final InMemoryCache<Integer, Book> cache;

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper).toList();
    }

    public BookDTO findBookByTitle(String title) {
        Book cachedBook = cache.get(title.hashCode());
        if (cachedBook != null) {
            return bookMapper.apply(cachedBook);
        }

        Book book = bookRepository.findByTitle(title);
        if (book == null) {
            return null;
        }
        cache.put(title.hashCode(), book);
        return bookMapper.apply(book);
    }

    public List<BookDTO> findByAuthorIdAndGenreId(Long authorId, Long genreId) {
        return bookRepository.findByAuthorIdAndGenreId(authorId,genreId).stream().map(bookMapper).toList();
    }

    public Optional<Book> addBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle())) {
            return Optional.empty();
        }

        return Optional.of(bookRepository.save(book));
    }

    public boolean deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
            cache.remove(book.getTitle().hashCode());
            return true;
        }

        return false;
    }

    public boolean updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id).orElse(null);

        if (existingBook != null) {
            cache.remove(book.getTitle().hashCode());

            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenres(book.getGenres());
            bookRepository.save(existingBook);

            return true;
        }

        return false;
    }
}
