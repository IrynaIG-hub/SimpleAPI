package data;

import framework.entity.request.AuthorCreationUpdateRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import java.util.concurrent.ThreadLocalRandom;

public class AuthorsData {

    @DataProvider(name = "Simple Author creation data")
    public Object[][] authorCreationData() {
        int authorId = ThreadLocalRandom.current().nextInt(1, 50 + 1);
        int bookId = ThreadLocalRandom.current().nextInt(1, 50 + 1);
        String authorFirstName = RandomStringUtils.randomAlphabetic(10);
        String authorLastName = RandomStringUtils.randomAlphabetic(10);

        AuthorCreationUpdateRequest authorCreationUpdateRequest = AuthorCreationUpdateRequest.builder()
                .id(authorId)
                .idBook(bookId)
                .firstName(authorFirstName)
                .lastName(authorLastName)
                .build();

        return new Object[][] { { authorCreationUpdateRequest } };
    }

    @DataProvider(name = "Simple Author id data")
    public Object[][] authorData() {
        int authorId = ThreadLocalRandom.current().nextInt(1, 50 + 1);

        return new Object[][] { { authorId } };
    }

}
