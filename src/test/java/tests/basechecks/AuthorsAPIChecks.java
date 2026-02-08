package tests.basechecks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import framework.base.BaseTest;
import framework.entity.request.AuthorCreationUpdateRequest;
import framework.entity.response.author.Author;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AuthorsAPIChecks extends BaseTest {

    protected void validateAuthorData(Response authorCreationResponse, AuthorCreationUpdateRequest authorCreationUpdateRequest) {
        assertThat(JsonPath.from(authorCreationResponse.asString()).getString("id")).as("Author ID is not correct")
                .isEqualTo(String.valueOf(authorCreationUpdateRequest.getId()));
        assertThat(JsonPath.from(authorCreationResponse.asString()).getString("idBook")).as("Author book ID is not correct")
                .isEqualTo(String.valueOf(authorCreationUpdateRequest.getIdBook()));
        assertThat(JsonPath.from(authorCreationResponse.asString()).getString("firstName")).as("Author first name is not correct")
                .isEqualTo(authorCreationUpdateRequest.getFirstName());
        assertThat(JsonPath.from(authorCreationResponse.asString()).getString("lastName")).as("Author last name is not correct")
                .isEqualTo(authorCreationUpdateRequest.getLastName());
    }

    protected List<Author> getAuthorsList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<List<Author>>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return Collections.<Author>emptyList();
    };

    protected Author getAuthor(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<Author>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    };

    protected void validateAuthors(List<Author> authors) {
        authors.stream()
                .forEach(author -> {
                    assertThat(String.valueOf(author.getId()).matches("\\d+")).as("Author ID is not correct").isTrue();
                    assertThat(String.valueOf(author.getIdBook()).matches("\\d+")).as("Author book ID is not correct").isTrue();
                    assertThat(!author.getFirstName().isEmpty()).as("Author first name is not correct").isTrue();
                    assertThat(!author.getLastName().isEmpty()).as("Author last name is not correct").isTrue();
                });
    }


}
