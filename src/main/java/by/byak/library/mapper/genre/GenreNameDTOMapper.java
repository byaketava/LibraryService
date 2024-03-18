package by.byak.library.mapper.genre;

import by.byak.library.dto.genre.GenreNameDTO;
import by.byak.library.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GenreNameDTOMapper implements Function<Genre, GenreNameDTO> {
    @Override
    public GenreNameDTO apply(Genre genre) {
        return new GenreNameDTO(genre.getName());
    }
}