package by.byak.library.dto.book;

import by.byak.library.dto.author.AuthorNameDTO;
import by.byak.library.dto.genre.GenreNameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private List<GenreNameDTO> genres;
    private AuthorNameDTO author;
}
