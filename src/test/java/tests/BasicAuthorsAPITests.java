package tests;

import data.AuthorsData;
import data.BooksData;
import framework.base.api.authors.AuthorsApiManager;
import framework.base.api.books.BooksApiManager;
import framework.entity.request.AuthorCreationUpdateRequest;
import framework.entity.request.BookCreationUpdateRequest;
import framework.entity.response.author.Author;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.basechecks.AuthorsAPIChecks;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BasicAuthorsAPITests extends AuthorsAPIChecks {

    @Feature("Simple Author creation")
    @Description("Check Author creation test")
    @Test(dataProvider = "Simple Author creation data", dataProviderClass = AuthorsData.class)
    public void checkAuthorCreationTest(AuthorCreationUpdateRequest authorCreationUpdateRequest) {
        AuthorsApiManager apiManager = new AuthorsApiManager();
        Response authorCreationResponse = apiManager.createAuthor(authorCreationUpdateRequest);
        assertThat(authorCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        validateAuthorData(authorCreationResponse, authorCreationUpdateRequest);
    }

    @Feature("Simple Author creation Negative")
    @Description("Check Author creation test Negative")
    @Test(dataProvider = "Simple Author creation data", dataProviderClass = AuthorsData.class)
    public void checkAuthorCreationNegativeTest(AuthorCreationUpdateRequest authorCreationUpdateRequest) {
        AuthorsApiManager apiManager = new AuthorsApiManager();
        AuthorCreationUpdateRequest authorCreationRequest = AuthorCreationUpdateRequest.builder()
                .id(authorCreationUpdateRequest.getId())
                .idBook(authorCreationUpdateRequest.getIdBook())
                .firstName("5665 vve")
                .lastName("%4*&&")
                .build();

        Response authorCreationResponse = apiManager.createAuthor(authorCreationRequest);
        assertThat(authorCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(400);
    }

    @Feature("Simple Author update")
    @Description("Check Author update test")
    @Test(dataProvider = "Simple Author creation data", dataProviderClass = AuthorsData.class)
    public void checkBookUpdateTest(AuthorCreationUpdateRequest authorCreationUpdateRequest) {
        AuthorsApiManager apiManager = new AuthorsApiManager();
        Response authorCreationResponse = apiManager.createAuthor(authorCreationUpdateRequest);
        assertThat(authorCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        AuthorCreationUpdateRequest authorUpdateRequest = AuthorCreationUpdateRequest.builder()
                .id(authorCreationUpdateRequest.getId())
                .idBook(authorCreationUpdateRequest.getIdBook())
                .firstName("Updated First Name")
                .lastName("Updated Last Name")
                .build();

        Response authorUpdateResponse = apiManager.updateAuthor(authorUpdateRequest);
        assertThat(authorUpdateResponse.getStatusCode()).as("Status code for update is not correct").isEqualTo(200);

        validateAuthorData(authorUpdateResponse, authorUpdateRequest);
    }

    @Feature("Simple Author update Negative")
    @Description("Check Author update test Negative")
    @Test(dataProvider = "Simple Author creation data", dataProviderClass = AuthorsData.class)
    public void checkBookUpdateNegativeTest(AuthorCreationUpdateRequest authorCreationUpdateRequest) {
        AuthorsApiManager apiManager = new AuthorsApiManager();
        Response authorCreationResponse = apiManager.createAuthor(authorCreationUpdateRequest);
        assertThat(authorCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        AuthorCreationUpdateRequest authorUpdateRequest = AuthorCreationUpdateRequest.builder()
                .id(authorCreationUpdateRequest.getId())
                .idBook(55)
                .firstName("Updated First Name")
                .lastName("Updated Last Name")
                .build();

        Response authorUpdateResponse = apiManager.updateAuthor(authorUpdateRequest);
        assertThat(authorUpdateResponse.getStatusCode()).as("Status code for update is not correct").isEqualTo(400);

        validateAuthorData(authorUpdateResponse, authorUpdateRequest);
    }

    @Feature("Simple Author delete")
    @Description("Check Author delete test")
    @Test(dataProvider = "Simple Author creation data", dataProviderClass = AuthorsData.class)
    public void checkAuthorDeleteTest(AuthorCreationUpdateRequest authorCreationUpdateRequest) {
        AuthorsApiManager apiManager = new AuthorsApiManager();
        Response authorCreationResponse = apiManager.createAuthor(authorCreationUpdateRequest);
        assertThat(authorCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        Response authorDeleteResponse = apiManager.deleteAuthor(String.valueOf(authorCreationUpdateRequest.getId()));
        assertThat(authorDeleteResponse.getStatusCode()).as("Status code for deletion is not correct").isEqualTo(200);
    }

    @Feature("Simple Author delete Negative")
    @Description("Check Author delete test Negative")
    @Test(dataProvider = "Simple Author creation data", dataProviderClass = AuthorsData.class)
    public void checkAuthorDeleteNegativeTest(AuthorCreationUpdateRequest authorCreationUpdateRequest) {
        AuthorsApiManager apiManager = new AuthorsApiManager();
        Response authorCreationResponse = apiManager.createAuthor(authorCreationUpdateRequest);
        assertThat(authorCreationResponse.getStatusCode()).as("Status code for creation is not correct").isEqualTo(200);

        Response authorDeleteResponse = apiManager.deleteAuthor("vfdvd");
        assertThat(authorDeleteResponse.getStatusCode()).as("Status code for deletion is not correct").isEqualTo(200);
    }

    @Feature("Simple Authors getting")
    @Description("Check Authors getting test")
    @Test
    public void checkBooksGetTest() {
        AuthorsApiManager apiManager = new AuthorsApiManager();
        Response authorsResponse = apiManager.getAuthors();
        assertThat(authorsResponse.getStatusCode()).as("Status code for getting books list is not correct").isEqualTo(200);

        List<Author> authors = getAuthorsList(authorsResponse.getBody().asString());
        validateAuthors(authors);
    }

    @Feature("Simple Author getting")
    @Description("Check Author getting test")
    @Test(dataProvider = "Simple Author id data", dataProviderClass = AuthorsData.class)
    public void checkBookGetTest(int authorId) {
        List<Author> authors = new ArrayList<>();
        AuthorsApiManager apiManager = new AuthorsApiManager();
        Response authorsResponse = apiManager.getAuthor(String.valueOf(authorId));
        assertThat(authorsResponse.getStatusCode()).as("Status code for getting book is not correct").isEqualTo(200);

        authors.add(getAuthor(authorsResponse.getBody().asString()));
        validateAuthors(authors);
    }


}
