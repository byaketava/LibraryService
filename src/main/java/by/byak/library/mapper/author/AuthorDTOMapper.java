package by.byak.library.mapper.author;

import by.byak.library.dto.author.AuthorDTO;
import by.byak.library.dto.book.BookTitleDTO;
import by.byak.library.entity.Author;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class AuthorDTOMapper implements Function<Author, AuthorDTO> {
    @Override
    public AuthorDTO apply(Author author) {
        List<BookTitleDTO> bookTitles = author.getBooks().stream()
                .map(book -> new BookTitleDTO(book.getTitle())).toList();

        return new AuthorDTO(author.getId(), author.getName(), bookTitles);
    }
}
