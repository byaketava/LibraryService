package by.byak.library.service;

import by.byak.library.cache.InMemoryCache;
import by.byak.library.dto.genre.GenreDto;
import by.byak.library.entity.Book;
import by.byak.library.entity.Genre;
import by.byak.library.exception.AlreadyExistsException;
import by.byak.library.exception.BadRequestException;
import by.byak.library.exception.NotFoundException;
import by.byak.library.mapper.genre.GenreDtoMapper;
import by.byak.library.repository.BookRepository;
import by.byak.library.repository.GenreRepository;
import java.util.Collections;
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

class GenreServiceTest {

  @Mock
  private GenreRepository genreRepository;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private GenreDtoMapper genreMapper;

  @Mock
  private InMemoryCache<Integer, Genre> cache;

  @Mock
  private InMemoryCache<Integer, Book> bookCache;

  @InjectMocks
  private GenreService genreService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAllGenres() {
    List<Genre> genres = new ArrayList<>();
    Genre fantasyGenre = new Genre();
    fantasyGenre.setName("Fantasy");
    genres.add(fantasyGenre);

    Genre mysteryGenre = new Genre();
    mysteryGenre.setName("Mystery");
    genres.add(mysteryGenre);

    List<GenreDto> expectedGenreDtos = new ArrayList<>();
    expectedGenreDtos.add(new GenreDto(1L, "Fantasy", new ArrayList<>()));
    expectedGenreDtos.add(new GenreDto(2L, "Mystery", new ArrayList<>()));

    when(genreRepository.findAll()).thenReturn(genres);
    when(genreMapper.apply(any(Genre.class))).thenReturn(
        new GenreDto(1L, "Fantasy", new ArrayList<>()),
        new GenreDto(2L, "Mystery", new ArrayList<>()));

    List<GenreDto> result = genreService.findAllGenres();

    assertEquals(expectedGenreDtos, result);
    verify(genreRepository, times(1)).findAll();
    verify(genreMapper, times(2)).apply(any(Genre.class));
  }

  @Test
  void testFindAllGenresEmptyList() {
    when(genreRepository.findAll()).thenReturn(Collections.emptyList());

    List<GenreDto> result = genreService.findAllGenres();

    assertTrue(result.isEmpty());
    verify(genreRepository, times(1)).findAll();
  }

  @Test
  void testFindAllGenresOneGenre() {
    List<Genre> genres = Collections.singletonList(new Genre());
    genres.get(0).setName("Fantasy");
    when(genreRepository.findAll()).thenReturn(genres);
    when(genreMapper.apply(any(Genre.class))).thenReturn(
        new GenreDto(1L, "Fantasy", new ArrayList<>()));

    List<GenreDto> result = genreService.findAllGenres();

    assertEquals(1, result.size());
    assertEquals("Fantasy", result.get(0).getName());
    verify(genreRepository, times(1)).findAll();
    verify(genreMapper, times(1)).apply(any(Genre.class));
  }

  @Test
  void testFindGenreByNameNotFound() {
    String genreName = "Thriller";
    when(genreRepository.findByName(genreName)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> genreService.findGenreByName(genreName));
    verify(genreRepository, times(1)).findByName(genreName);
    verify(genreMapper, never()).apply(any(Genre.class));
    assertNull(cache.get(genreName.hashCode()));
  }

  @Test
  void testAddGenreAlreadyExists() {
    String genreName = "Fantasy";
    Genre genre = new Genre();
    genre.setName(genreName);
    when(genreRepository.existsByName(genreName)).thenReturn(true);

    assertThrows(AlreadyExistsException.class, () -> genreService.addGenre(genre));
    verify(genreRepository, times(1)).existsByName(genreName);
    verify(genreRepository, never()).save(any(Genre.class));
  }

  @Test
  void testAddGenreSuccess() {
    String genreName = "Mystery";
    Genre genre = new Genre();
    genre.setName(genreName);
    when(genreRepository.existsByName(genreName)).thenReturn(false);

    genreService.addGenre(genre);

    verify(genreRepository, times(1)).existsByName(genreName);
    verify(genreRepository, times(1)).save(genre);
  }

  @Test
  void testAddGenreSaveException() {
    // Arrange
    String genreName = "Thriller";
    Genre genre = new Genre();
    genre.setName(genreName);
    when(genreRepository.existsByName(genreName)).thenReturn(false);
    doThrow(RuntimeException.class).when(genreRepository).save(genre);

    // Act and Assert
    assertThrows(BadRequestException.class, () -> genreService.addGenre(genre));
    verify(genreRepository, times(1)).existsByName(genreName);
    verify(genreRepository, times(1)).save(genre);
  }

  @Test
  void testDeleteGenreByIdNotFound() {
    Long genreId = 1L;
    when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> genreService.deleteGenreById(genreId));
    verify(genreRepository, times(1)).findById(genreId);
    verify(bookRepository, never()).save(any(Book.class));
    verify(genreRepository, never()).delete(any(Genre.class));
    verify(cache, never()).remove(anyInt());
    verify(bookCache, never()).remove(anyInt());
  }

  @Test
  void testDeleteGenreByIdBookSaveException() {
    Long genreId = 1L;
    Genre genre = new Genre();
    genre.setId(genreId);
    genre.setName("Fantasy");
    List<Book> books = new ArrayList<>();
    Book book = new Book();
    book.setTitle("Book Title");
    books.add(book);
    genre.setBooks(books);
    when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
    doThrow(RuntimeException.class).when(bookRepository).save(book);

    assertThrows(BadRequestException.class, () -> genreService.deleteGenreById(genreId));
    verify(genreRepository, times(1)).findById(genreId);
    verify(bookRepository, times(1)).save(book);
    verify(genreRepository, never()).delete(any(Genre.class));
    verify(cache, never()).remove(anyInt());
    verify(bookCache, never()).remove(anyInt());
  }

  @Test
  void testUpdateGenreNotFound() {
    Long genreId = 1L;
    when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> {
      genreService.updateGenre(genreId, new Genre());
    });
    verify(genreRepository, times(1)).findById(genreId);
    verify(cache, never()).remove(anyInt());
    verify(genreRepository, never()).save(any(Genre.class));
  }
}
