package framework.base.api.authors;

import framework.base.api.ApiManager;
import framework.entity.request.AuthorCreationUpdateRequest;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static framework.base.BaseTest.getBaseUrl;

@Slf4j
public class AuthorsApiManager extends ApiManager {

    public Response createAuthor(AuthorCreationUpdateRequest request) {
        log.info("Create author request is created");
        return createRequestSpecification(getBaseUrl(), "all")
                .body(AuthorCreationUpdateRequest.builder()
                        .id(request.getId())
                        .idBook(request.getIdBook())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .build())
                .with()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .post("/api/v1/Authors");
    }

    public Response updateAuthor(AuthorCreationUpdateRequest request) {
        log.info("Update author request is created");
        return createRequestSpecification(getBaseUrl(), "body")
                .body(AuthorCreationUpdateRequest.builder()
                        .id(request.getId())
                        .idBook(request.getIdBook())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .build())
                .with()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .put("/api/v1/Authors/" + request.getId());
    }

    public Response getAuthor(String id) {
        log.info("Get author request is created");
        return createRequestSpecification(getBaseUrl(), "all")
                .with()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .get("/api/v1/Authors/" + id);
    }

    public Response getAuthors() {
        log.info("Get authors request is created");
        return createRequestSpecification(getBaseUrl(), "all")
                .with()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .get("/api/v1/Authors");
    }

    public Response deleteAuthor(String id) {
        log.info("Delete author request is created");
        return createRequestSpecification(getBaseUrl(), "headers")
                .with()
                .header("Content-Type", "application/json")
                .delete("/api/v1/Authors/" + id);
    }

}
