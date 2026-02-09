package tests.basechecks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.base.BaseTest;
import framework.entity.request.BookCreationUpdateRequest;
import framework.entity.response.book.Book;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BooksAPIChecks extends BaseTest {

    protected void validateBookData(Response bookCreationResponse, BookCreationUpdateRequest bookCreationUpdateRequest) {
        assertThat(JsonPath.from(bookCreationResponse.asString()).getString("id")).as("Book ID is not correct")
                .isEqualTo(String.valueOf(bookCreationUpdateRequest.getId()));
        assertThat(JsonPath.from(bookCreationResponse.asString()).getString("title")).as("Book title is not correct")
                .isEqualTo(bookCreationUpdateRequest.getTitle());
        assertThat(JsonPath.from(bookCreationResponse.asString()).getString("description")).as("Book description is not correct")
                .isEqualTo(bookCreationUpdateRequest.getDescription());
        assertThat(JsonPath.from(bookCreationResponse.asString()).getString("pageCount")).as("Book pageCount is not correct")
                .isEqualTo(String.valueOf(bookCreationUpdateRequest.getPageCount()));
        assertThat(JsonPath.from(bookCreationResponse.asString()).getString("excerpt")).as("Book excerpt is not correct")
                .isEqualTo(bookCreationUpdateRequest.getExcerpt());
        assertThat(JsonPath.from(bookCreationResponse.asString()).getString("publishDate").substring(0, JsonPath.from(bookCreationResponse.asString())
                .getString("publishDate").indexOf("."))).as("Book publishDate is not correct")
                .isEqualTo(bookCreationUpdateRequest.getPublishDate().substring(0, bookCreationUpdateRequest.getPublishDate().indexOf(".")));
    }

    protected List<Book> getBooksList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<List<Book>>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return Collections.<Book>emptyList();
    }

    protected Book getBook(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<Book>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected void validateBooks(List<Book> books) {
        books.stream()
                .forEach(book -> {
                    assertThat(String.valueOf(book.getId()).matches("\\d+")).as("Book ID is not correct").isTrue();
                    assertThat(book.getTitle().matches("\\w+\\s\\w+")).as("Book title is not correct").isTrue();
                    assertThat(!book.getDescription().isEmpty()).as("Book description is not correct").isTrue();
                    assertThat(String.valueOf(book.getPageCount()).matches("\\d+")).as("Book page count is not correct").isTrue();
                    assertThat(!book.getExcerpt().isEmpty()).as("Book excerpt is not correct").isTrue();
                    assertThat(String.valueOf(book.getPublishDate()).matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{4,7}\\+\\d{2}:\\d{2}"))
                            .as("Book publish date is not correct").isTrue();
                });
    }

}
