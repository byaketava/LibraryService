package by.byak.library.service;

import by.byak.library.dto.genre.GenreDTO;
import by.byak.library.entity.Genre;
import by.byak.library.mapper.genre.GenreDTOMapper;
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
    private final GenreDTOMapper genreMapper;

    public List<GenreDTO> findAllGenres() {
        return genreRepository.findAll().stream().map(genreMapper).toList();
    }

    public GenreDTO findByName(String name) {
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

    public boolean deleteGenreByName(String name) {
        Genre genre = genreRepository.findByName(name);

        if (genre != null) {
            genreRepository.delete(genre);
            return true;
        }

        return false;
    }

    public boolean updateGenreName(Long id, String newName) {
        Optional<Genre> genreOptional = genreRepository.findById(id);

        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();
            genre.setName(newName);
            genreRepository.save(genre);
            return true;
        }

        return false;
    }
}
