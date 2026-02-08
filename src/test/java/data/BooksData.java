package data;

import framework.entity.request.BookCreationUpdateRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class BooksData {

    @DataProvider(name = "Simple Book creation data")
    public Object[][] bookCreationData() {
        int bookId = ThreadLocalRandom.current().nextInt(10, 50 + 1);
        String bookTitle = RandomStringUtils.randomAlphabetic(10);
        String bookDescription = RandomStringUtils.randomAlphabetic(10, 30);
        int pageCount = ThreadLocalRandom.current().nextInt(1, 50 + 1);
        String bookExcerpt = RandomStringUtils.randomAlphabetic(10, 50);
        LocalDateTime myDate = LocalDateTime.now();

        BookCreationUpdateRequest bookCreationUpdateRequest = BookCreationUpdateRequest.builder()
                .id(bookId)
                .title(bookTitle)
                .description(bookDescription)
                .pageCount(pageCount)
                .excerpt(bookExcerpt)
                .publishDate(myDate.toString())
                .build();

        return new Object[][] { { bookCreationUpdateRequest } };
    }

    @DataProvider(name = "Simple Book id data")
    public Object[][] bookData() {
        int bookId = ThreadLocalRandom.current().nextInt(1, 50 + 1);

        return new Object[][] { { bookId } };
    }

}
