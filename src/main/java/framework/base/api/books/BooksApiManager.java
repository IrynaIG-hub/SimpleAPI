package framework.base.api.books;

import framework.base.api.ApiManager;
import framework.entity.request.BookCreationUpdateRequest;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static framework.base.BaseTest.getBaseUrl;

@Slf4j
public class BooksApiManager extends ApiManager {

    public Response createBook(BookCreationUpdateRequest request) {
        log.info("Create book request is created");
        return createRequestSpecification(getBaseUrl(), "all")
                .body(BookCreationUpdateRequest.builder()
                        .id(request.getId())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .pageCount(request.getPageCount())
                        .excerpt(request.getExcerpt())
                        .publishDate(request.getPublishDate())
                        .build())
                .with()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .post("/api/v1/Books");
    }

    public Response updateBook(BookCreationUpdateRequest request) {
        log.info("Update book request is created");
        return createRequestSpecification(getBaseUrl(), "body")
                .body(BookCreationUpdateRequest.builder()
                        .id(request.getId())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .pageCount(request.getPageCount())
                        .excerpt(request.getExcerpt())
                        .publishDate(request.getPublishDate())
                        .build())
                .with()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .put("/api/v1/Books/" + request.getId());
    }

    public Response getBook(String id) {
        log.info("Get book request is created");
        return createRequestSpecification(getBaseUrl(), "all")
                .with()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .get("/api/v1/Books/" + id);
    }

    public Response getBooks() {
        log.info("Get books request is created");
        return createRequestSpecification(getBaseUrl(), "all")
                .with()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .get("/api/v1/Books");

    }

    public Response deleteBook(String id) {
        log.info("Delete books request is created");
        return createRequestSpecification(getBaseUrl(), "headers")
                .with()
                .header("Content-Type", "application/json")
                .delete("/api/v1/Books/" + id);
    }

}
