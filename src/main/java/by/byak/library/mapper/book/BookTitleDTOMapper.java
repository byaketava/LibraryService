package by.byak.library.mapper.book;

import by.byak.library.dto.book.BookTitleDTO;
import by.byak.library.entity.Book;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BookTitleDTOMapper implements Function<Book, BookTitleDTO> {
    @Override
    public BookTitleDTO apply(Book book) {
        return new BookTitleDTO(book.getTitle());
    }
}
