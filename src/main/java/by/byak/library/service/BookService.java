package by.byak.library.service;

import by.byak.library.cache.InMemoryCache;
import by.byak.library.dto.book.BookDTO;
import by.byak.library.entity.Book;
import by.byak.library.exception.AlreadyExistsException;
import by.byak.library.exception.BadRequestException;
import by.byak.library.exception.NotFoundException;
import by.byak.library.mapper.book.BookDTOMapper;
import by.byak.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final BookDTOMapper bookMapper;
    private final InMemoryCache<Integer, Book> cache;

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper).toList();
    }

    public BookDTO findBookByTitle(String title) {
        Book cachedBook = cache.get(title.hashCode());
        if (cachedBook != null) {
            return bookMapper.apply(cachedBook);
        }

        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException(
                        "The book with that title has not been found"));
        cache.put(title.hashCode(), book);

        return bookMapper.apply(book);
    }

    public List<BookDTO> findByAuthorIdAndGenreId(Long authorId, Long genreId) {
        List<Book> books = bookRepository
                .findByAuthorIdAndGenreId(authorId, genreId)
                .orElseThrow(() -> new NotFoundException(
                        "Books were not found for the given author and genre"));
        return books.stream().map(bookMapper).toList();
    }

    public void addBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new AlreadyExistsException(
                    "The book with that title already exists");
        }

        try {
            bookRepository.save(book);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "The book with that id has not been found"));
        bookRepository.delete(book);
        cache.remove(book.getTitle().hashCode());
    }

    public void updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "The book with that id has not been found"));
        cache.remove(book.getTitle().hashCode());

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setGenres(book.getGenres());

        try {
            bookRepository.save(existingBook);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
