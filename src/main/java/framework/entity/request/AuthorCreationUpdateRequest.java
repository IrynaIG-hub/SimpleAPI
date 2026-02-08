package framework.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorCreationUpdateRequest {
    @JsonProperty("id")
    private int id;

    @JsonProperty("idBook")
    private int idBook;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    public static AuthorCreationUpdateRequest setDefaultData(AuthorCreationUpdateRequest request) {
        return AuthorCreationUpdateRequest.builder()
                .id(request.getId())
                .idBook(request.getIdBook())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

}
