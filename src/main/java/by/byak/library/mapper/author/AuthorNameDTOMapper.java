package by.byak.library.mapper.author;

import by.byak.library.dto.author.AuthorNameDTO;
import by.byak.library.entity.Author;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AuthorNameDTOMapper implements Function<Author, AuthorNameDTO> {
    @Override
    public AuthorNameDTO apply(Author author) {
        return new AuthorNameDTO(author.getName());
    }
}
