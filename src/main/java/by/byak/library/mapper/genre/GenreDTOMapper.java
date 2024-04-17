package by.byak.library.mapper.genre;

import by.byak.library.dto.book.BookTitleDTO;
import by.byak.library.dto.genre.GenreDTO;
import by.byak.library.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class GenreDTOMapper implements Function<Genre, GenreDTO> {
    @Override
    public GenreDTO apply(Genre genre) {
        List<BookTitleDTO> bookTitles = genre.getBooks().stream()
                .map(book -> new BookTitleDTO(book.getTitle())).toList();

        return new GenreDTO(genre.getId(), genre.getName(), bookTitles);
    }
}
