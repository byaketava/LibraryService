package by.byak.library.service;

import by.byak.library.cache.InMemoryCache;
import by.byak.library.dto.book.BookDto;
import by.byak.library.entity.Book;
import by.byak.library.exception.AlreadyExistsException;
import by.byak.library.exception.BadRequestException;
import by.byak.library.exception.NotFoundException;
import by.byak.library.mapper.book.BookDtoMapper;
import by.byak.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class BookServiceTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookDtoMapper bookMapper;

  @Mock
  private InMemoryCache<Integer, Book> cache;

  @InjectMocks
  private BookService bookService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testFindAllBooks() {
    List<Book> books = new ArrayList<>();
    books.add(new Book());
    books.add(new Book());

    when(bookRepository.findAll()).thenReturn(books);

    List<BookDto> bookDtos = new ArrayList<>();
    bookDtos.add(new BookDto());
    bookDtos.add(new BookDto());

    when(bookMapper.apply(any(Book.class))).thenReturn(new BookDto());

    List<BookDto> result = bookService.findAllBooks();

    assertEquals(2, result.size());
    verify(bookRepository, times(1)).findAll();
    verify(bookMapper, times(2)).apply(any(Book.class));

    // Verify that the bookMapper.apply() method is called with any Book object
    verify(bookMapper, times(books.size())).apply(any(Book.class));
  }

  @Test
  public void testFindBookByTitle_BookFoundInCache() {
    String title = "Test Book";
    Book cachedBook = new Book();
    cachedBook.setTitle(title);

    when(cache.get(title.hashCode())).thenReturn(cachedBook);

    BookDto bookDto = new BookDto();
    bookDto.setTitle(title);

    when(bookMapper.apply(cachedBook)).thenReturn(bookDto);

    BookDto result = bookService.findBookByTitle(title);

    assertEquals(title, result.getTitle());
    verify(cache, times(1)).get(title.hashCode());
    verify(bookMapper, times(1)).apply(cachedBook);
    verify(bookRepository, never()).findByTitle(title);
  }

  @Test
  public void testFindBookByTitle_BookNotFound() {
    String title = "Non-existent Book";

    when(cache.get(title.hashCode())).thenReturn(null);
    when(bookRepository.findByTitle(title)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> bookService.findBookByTitle(title));

    verify(cache, times(1)).get(title.hashCode());
    verify(bookRepository, times(1)).findByTitle(title);
  }

  @Test
  public void testFindByAuthorIdAndGenreId_BooksFound() {
    Long authorId = 1L;
    Long genreId = 2L;

    List<Book> books = new ArrayList<>();
    books.add(new Book());
    books.add(new Book());

    when(bookRepository.findByAuthorIdAndGenreId(authorId, genreId)).thenReturn(Optional.of(books));

    List<BookDto> bookDtos = new ArrayList<>();
    bookDtos.add(new BookDto());
    bookDtos.add(new BookDto());

    when(bookMapper.apply(any(Book.class))).thenReturn(new BookDto());

    List<BookDto> result = bookService.findByAuthorIdAndGenreId(authorId, genreId);

    assertEquals(2, result.size());
    verify(bookRepository, times(1)).findByAuthorIdAndGenreId(authorId, genreId);
    verify(bookMapper, times(2)).apply(any(Book.class));
  }

  @Test
  public void testFindByAuthorIdAndGenreId_BooksNotFound() {
    Long authorId = 1L;
    Long genreId = 2L;

    when(bookRepository.findByAuthorIdAndGenreId(authorId, genreId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class,
        () -> bookService.findByAuthorIdAndGenreId(authorId, genreId));

    verify(bookRepository, times(1)).findByAuthorIdAndGenreId(authorId, genreId);
    verify(bookMapper, never()).apply(any(Book.class));
  }

  @Test
  public void testAddBook_BookDoesNotExist() {
    Book book = new Book();
    book.setTitle("New Book");

    when(bookRepository.existsByTitle(book.getTitle())).thenReturn(false);

    bookService.addBook(book);

    verify(bookRepository, times(1)).existsByTitle(book.getTitle());
    verify(bookRepository, times(1)).save(book);
  }

  @Test
  public void testAddBook_BookAlreadyExists() {
    Book book = new Book();
    book.setTitle("ExistingBook");

    when(bookRepository.existsByTitle(book.getTitle())).thenReturn(true);

    assertThrows(AlreadyExistsException.class, () -> bookService.addBook(book));

    verify(bookRepository, times(1)).existsByTitle(book.getTitle());
    verify(bookRepository, never()).save(book);
  }

  @Test
  public void testDeleteBookById_BookExists() {
    Long id = 1L;
    String title = "Test Book";
    Book book = new Book();
    book.setId(id);
    book.setTitle(title);

    when(bookRepository.findById(id)).thenReturn(Optional.of(book));

    bookService.deleteBookById(id);

    verify(bookRepository, times(1)).findById(id);
    verify(bookRepository, times(1)).delete(book);
    verify(cache, times(1)).remove(title.hashCode());
  }

  @Test
  public void testDeleteBookById_BookNotFound() {
    Long id = 1L;

    when(bookRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> bookService.deleteBookById(id));

    verify(bookRepository, times(1)).findById(id);
    verify(bookRepository, never()).delete(any(Book.class));
    verify(cache, never()).remove(anyInt());
  }

  @Test
  public void testUpdateBook_BookNotFound() {
    Long id = 1L;
    Book book = new Book();

    when(bookRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> bookService.updateBook(id, book));

    verify(bookRepository, times(1)).findById(id);
    verify(cache, never()).remove(anyInt());
    verify(bookRepository, never()).save(any(Book.class));
  }

  @Test
  public void testAddBook_ExceptionWhileSavingBook() {
    Book book = new Book();
    book.setTitle("New Book");

    when(bookRepository.existsByTitle(book.getTitle())).thenReturn(false);
    doThrow(BadRequestException.class).when(bookRepository).save(book);

    assertThrows(BadRequestException.class, () -> bookService.addBook(book));

    verify(bookRepository, times(1)).existsByTitle(book.getTitle());
    verify(bookRepository, times(1)).save(book);
  }
}