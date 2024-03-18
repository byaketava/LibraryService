package by.byak.library.mapper.book;

import by.byak.library.dto.book.BookDTO;
import by.byak.library.entity.Book;
import by.byak.library.mapper.author.AuthorNameDTOMapper;
import by.byak.library.mapper.genre.GenreNameDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class BookDTOMapper implements Function<Book, BookDTO> {
    private final AuthorNameDTOMapper authorMapper = new AuthorNameDTOMapper();
    private final GenreNameDTOMapper genreMapper = new GenreNameDTOMapper();

    @Override
    public BookDTO apply(Book book) {
        if (book == null) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setGenres(book.getGenres().stream().map(genreMapper).toList());
        bookDTO.setAuthor(authorMapper.apply(book.getAuthor()));

        return bookDTO;
    }
}