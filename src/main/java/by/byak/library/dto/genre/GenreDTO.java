package by.byak.library.dto.genre;

import by.byak.library.dto.book.BookTitleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {
    private Long id;
    private String name;
    private List<BookTitleDTO> books;
}
