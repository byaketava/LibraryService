package by.byak.library.service;

import by.byak.library.dto.genre.GenreDTO;
import by.byak.library.entity.Book;
import by.byak.library.entity.Genre;
import by.byak.library.mapper.genre.GenreDTOMapper;
import by.byak.library.repository.BookRepository;
import by.byak.library.repository.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final GenreDTOMapper genreMapper;

    public List<GenreDTO> findAllGenres() {
        return genreRepository.findAll().stream().map(genreMapper).toList();
    }

    public GenreDTO findGenreByName(String name) {
        Genre genre = genreRepository.findByName(name);

        if (genre == null) {
            return null;
        }

        return genreMapper.apply(genre);
    }

    public Optional<Genre> addGenre(Genre genre) {
        if (genreRepository.existsByName(genre.getName())) {
            return Optional.empty();
        }

        return Optional.of(genreRepository.save(genre));
    }

    public boolean deleteGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElse(null);
        if (genre != null) {
            for (Book book : genre.getBooks()) {
                book.getGenres().remove(genre);
                bookRepository.save(book);
            }
            genreRepository.delete(genre);
            return true;
        }

        return false;
    }

    public boolean updateGenre(Long id, Genre genre) {
        Genre existingGenre = genreRepository.findById(id).orElse(null);

        if (existingGenre != null) {
            existingGenre.setName(genre.getName());
            existingGenre.setBooks(genre.getBooks());
            genreRepository.save(existingGenre);
            return true;
        }
        return false;
    }
}
