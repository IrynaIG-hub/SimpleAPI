package tests;

import data.BooksData;
import framework.base.api.books.BooksApiManager;
import framework.entity.request.BookCreationUpdateRequest;
import framework.entity.response.book.Book;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.basechecks.BooksAPIChecks;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BasicBooksAPITests extends BooksAPIChecks {

    @Feature("Simple Book creation")
    @Description("Check Book creation test")
    @Test(dataProvider = "Simple Book creation data", dataProviderClass = BooksData.class)
    public void checkBookCreationTest(BookCreationUpdateRequest bookCreationUpdateRequest) {
        BooksApiManager apiManager = new BooksApiManager();
        Response bookCreationResponse = apiManager.createBook(bookCreationUpdateRequest);
        assertThat(bookCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        validateBookData(bookCreationResponse, bookCreationUpdateRequest);
    }

    @Feature("Simple Book creation Negative")
    @Description("Check Book creation test Negative")
    @Test(dataProvider = "Simple Book creation data", dataProviderClass = BooksData.class)
    public void checkBookCreationNegativeTest(BookCreationUpdateRequest bookCreationUpdateRequest) {
        BooksApiManager apiManager = new BooksApiManager();
        BookCreationUpdateRequest bookCreationRequest = BookCreationUpdateRequest.builder()
                .id(bookCreationUpdateRequest.getId())
                .title(bookCreationUpdateRequest.getTitle())
                .description("Description Update")
                .pageCount(bookCreationUpdateRequest.getPageCount())
                .excerpt("Excerpt Update")
                .publishDate("publishDate")
                .build();

        Response bookCreationResponse = apiManager.createBook(bookCreationRequest);
        assertThat(bookCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);
    }

    @Feature("Simple Book update")
    @Description("Check Book update test")
    @Test(dataProvider = "Simple Book creation data", dataProviderClass = BooksData.class)
    public void checkBookUpdateTest(BookCreationUpdateRequest bookCreationUpdateRequest) {
        BooksApiManager apiManager = new BooksApiManager();
        Response bookCreationResponse = apiManager.createBook(bookCreationUpdateRequest);
        assertThat(bookCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        BookCreationUpdateRequest bookUpdateRequest = BookCreationUpdateRequest.builder()
                .id(bookCreationUpdateRequest.getId())
                .title(bookCreationUpdateRequest.getTitle())
                .description("Description Update")
                .pageCount(bookCreationUpdateRequest.getPageCount())
                .excerpt("Excerpt Update")
                .publishDate(bookCreationUpdateRequest.getPublishDate())
                .build();

        Response bookUpdateResponse = apiManager.updateBook(bookUpdateRequest);
        assertThat(bookUpdateResponse.getStatusCode()).as("Status code for update is not correct").isEqualTo(200);

        validateBookData(bookUpdateResponse, bookUpdateRequest);
    }

    @Feature("Simple Book update Negative")
    @Description("Check Book update test Negative")
    @Test(dataProvider = "Simple Book creation data", dataProviderClass = BooksData.class)
    public void checkBookUpdateNegativeTest(BookCreationUpdateRequest bookCreationUpdateRequest) {
        BooksApiManager apiManager = new BooksApiManager();
        Response bookCreationResponse = apiManager.createBook(bookCreationUpdateRequest);
        assertThat(bookCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        BookCreationUpdateRequest bookUpdateRequest = BookCreationUpdateRequest.builder()
                .id(bookCreationUpdateRequest.getId())
                .title(bookCreationUpdateRequest.getTitle())
                .description(bookCreationUpdateRequest.getDescription())
                .pageCount(bookCreationUpdateRequest.getPageCount())
                .excerpt(bookCreationUpdateRequest.getExcerpt())
                .publishDate("publishDate")
                .build();

        Response bookUpdateResponse = apiManager.updateBook(bookUpdateRequest);
        assertThat(bookUpdateResponse.getStatusCode()).as("Status code for update is not correct").isEqualTo(200);

        validateBookData(bookUpdateResponse, bookUpdateRequest);
    }

    @Feature("Simple Book delete")
    @Description("Check Book delete test")
    @Test(dataProvider = "Simple Book creation data", dataProviderClass = BooksData.class)
    public void checkBookDeleteTest(BookCreationUpdateRequest bookCreationUpdateRequest) {
        BooksApiManager apiManager = new BooksApiManager();
        Response bookCreationResponse = apiManager.createBook(bookCreationUpdateRequest);
        assertThat(bookCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        Response bookDeleteResponse = apiManager.deleteBook(String.valueOf(bookCreationUpdateRequest.getId()));
        assertThat(bookDeleteResponse.getStatusCode()).as("Status code for deletion is not correct").isEqualTo(200);
    }

    @Feature("Simple Book delete Negative")
    @Description("Check Book delete test Negative")
    @Test(dataProvider = "Simple Book creation data", dataProviderClass = BooksData.class)
    public void checkBookDeleteNegativeTest(BookCreationUpdateRequest bookCreationUpdateRequest) {
        BooksApiManager apiManager = new BooksApiManager();
        Response bookCreationResponse = apiManager.createBook(bookCreationUpdateRequest);
        assertThat(bookCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        Response bookDeleteResponse = apiManager.deleteBook("jvrkev");
        assertThat(bookDeleteResponse.getStatusCode()).as("Status code for deletion is not correct").isEqualTo(200);
    }

    @Feature("Simple Books getting")
    @Description("Check Books getting test")
    @Test
    public void checkBooksGetTest() {
        BooksApiManager apiManager = new BooksApiManager();
        Response booksResponse = apiManager.getBooks();
        assertThat(booksResponse.getStatusCode()).as("Status code for getting books list is not correct").isEqualTo(200);

        List<Book> books = getBooksList(booksResponse.getBody().asString());
        validateBooks(books);
    }

    @Feature("Simple Book getting")
    @Description("Check Book getting test")
    @Test(dataProvider = "Simple Book id data", dataProviderClass = BooksData.class)
    public void checkBookGetTest(int bookId) {
        List<Book> books = new ArrayList<>();
        BooksApiManager apiManager = new BooksApiManager();
        Response booksResponse = apiManager.getBook(String.valueOf(bookId));
        assertThat(booksResponse.getStatusCode()).as("Status code for getting book is not correct").isEqualTo(200);

        books.add(getBook(booksResponse.getBody().asString()));
        validateBooks(books);
    }

}
