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
public class BookCreationUpdateRequest {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("pageCount")
    private int pageCount;

    @JsonProperty("excerpt")
    private String excerpt;

    @JsonProperty("publishDate")
    private String publishDate;

    public static BookCreationUpdateRequest setDefaultData(BookCreationUpdateRequest request) {
        return BookCreationUpdateRequest.builder()
                .id(request.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .pageCount(request.getPageCount())
                .excerpt(request.getExcerpt())
                .publishDate(request.getPublishDate())
                .build();
    }
}
