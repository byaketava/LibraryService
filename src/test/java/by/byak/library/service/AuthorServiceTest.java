package by.byak.library.service;

import by.byak.library.cache.InMemoryCache;
import by.byak.library.dto.author.AuthorDto;
import by.byak.library.entity.Author;
import by.byak.library.exception.AlreadyExistsException;
import by.byak.library.exception.NotFoundException;
import by.byak.library.mapper.author.AuthorDtoMapper;
import by.byak.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

  @Mock
  private AuthorRepository authorRepository;

  @Mock
  private AuthorDtoMapper authorMapper;

  @Mock
  private InMemoryCache<Integer, Author> cache;

  @InjectMocks
  private AuthorService authorService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAllAuthors() {
    Author author1 = new Author();
    author1.setName("Author 1");

    Author author2 = new Author();
    author2.setName("Author 2");

    List<Author> authorList = new ArrayList<>();
    authorList.add(author1);
    authorList.add(author2);

    AuthorDto authorDto1 = new AuthorDto();
    authorDto1.setName("Author 1 DTO");

    AuthorDto authorDto2 = new AuthorDto();
    authorDto2.setName("Author 2 DTO");

    List<AuthorDto> authorDtoList = new ArrayList<>();
    authorDtoList.add(authorDto1);
    authorDtoList.add(authorDto2);

    when(authorRepository.findAll()).thenReturn(authorList);

    when(authorMapper.apply(author1)).thenReturn(authorDto1);
    when(authorMapper.apply(author2)).thenReturn(authorDto2);

    List<AuthorDto> result = authorService.findAllAuthors();

    assertEquals(authorDtoList.size(), result.size());
    assertTrue(result.contains(authorDto1));
    assertTrue(result.contains(authorDto2));

    verify(authorRepository, times(1)).findAll();

    verify(authorMapper, times(1)).apply(author1);
    verify(authorMapper, times(1)).apply(author2);
  }

  @Test
  void testFindByName() {
    Author author = new Author();
    author.setName("Test Author");

    AuthorDto authorDto = new AuthorDto();
    authorDto.setName("Test Author DTO");

    when(cache.get(anyInt())).thenReturn(null);

    when(authorRepository.findByName("Test Author")).thenReturn(Optional.of(author));

    when(authorMapper.apply(author)).thenReturn(authorDto);

    AuthorDto result = authorService.findByName("Test Author");

    assertEquals(authorDto, result);

    verify(cache, times(1)).get(anyInt());

    verify(authorRepository, times(1)).findByName("Test Author");

    verify(cache, times(1)).put(anyInt(), eq(author));

    verify(authorMapper, times(1)).apply(author);
  }

  @Test
  void testAddAuthor() {
    Author author = new Author();
    author.setName("Test Author");

    when(authorRepository.existsByName("Test Author")).thenReturn(false);

    authorService.addAuthor(author);

    verify(authorRepository, times(1)).existsByName("Test Author");
    verify(authorRepository, times(1)).save(author);
  }

  @Test
  void testAddAuthor_AlreadyExists() {
    Author author = new Author();
    author.setName("Test Author");

    when(authorRepository.existsByName("Test Author")).thenReturn(true);

    assertThrows(AlreadyExistsException.class, () -> authorService.addAuthor(author));

    verify(authorRepository, times(1)).existsByName("Test Author");
    verify(authorRepository, never()).save(author);
  }

  @Test
  void testDeleteAuthorById() {
    Author author = new Author();
    author.setId(1L);
    author.setName("Test Author");

    when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

    authorService.deleteAuthorById(1L);

    verify(authorRepository, times(1)).findById(1L);
    verify(authorRepository, times(1)).delete(author);

    verify(cache, times(1)).remove(author.getName().hashCode());
  }

  @Test
  void testDeleteAuthorById_NotFound() {
    when(authorRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> authorService.deleteAuthorById(1L));

    verify(authorRepository, times(1)).findById(1L);
    verify(authorRepository, never()).delete(any());

    verify(cache, never()).remove(anyInt());
  }

  @Test
  void testUpdateAuthor() {
    Author existingAuthor = new Author();
    existingAuthor.setId(1L);
    existingAuthor.setName("Existing Author");

    Author updatedAuthor = new Author();
    updatedAuthor.setName("Updated Author");

    when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));

    authorService.updateAuthor(1L, updatedAuthor);

    verify(authorRepository, times(1)).findById(1L);
    verify(authorRepository, times(1)).save(existingAuthor);

    verify(cache, times(1)).remove(existingAuthor.getName().hashCode());

    assertEquals(updatedAuthor.getName(), existingAuthor.getName());
  }

  @Test
  void testUpdateAuthor_NotFound() {
    Author updatedAuthor = new Author();
    updatedAuthor.setName("Updated Author");

    when(authorRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> authorService.updateAuthor(1L, updatedAuthor));

    verify(authorRepository, times(1)).findById(1L);
    verify(authorRepository, never()).save(any());

    verify(cache, never()).remove(anyInt());
  }
}
