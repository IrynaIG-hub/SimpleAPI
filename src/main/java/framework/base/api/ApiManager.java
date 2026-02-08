package framework.base.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiManager {
    private RequestSpecification clientRequestSpecification = null;

    public RequestSpecification createRequestSpecification(String baseUri, String logLevel) {

        switch(logLevel) {
            case "all":
                log.info("Request specification with log level ALL is created");
                clientRequestSpecification =  RestAssured.given().
                        log().all()
                        .baseUri(baseUri)
                        .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                        .expect().log().all().request();
                break;
            case "headers":
                log.info("Request specification with log level HEADERS is created");
                clientRequestSpecification =  RestAssured.given().
                        log().headers()
                        .baseUri(baseUri)
                        .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                        .expect().log().headers().request();
                break;
            case "body":
                log.info("Request specification with log level BODY is created");
                clientRequestSpecification =  RestAssured.given().
                        log().body()
                        .baseUri(baseUri)
                        .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                        .expect().log().body().request();
                break;
            default:
                log.info("Request specification without logging is created");
                clientRequestSpecification = RestAssured.given()
                        .baseUri(baseUri)
                        .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                        .expect().request();
        }

        return clientRequestSpecification;
    }

    public RequestSpecification getClientRequestSpecification() {
        return clientRequestSpecification;
    }

}
