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
                clientRequestSpecification =  RestAssured.given().
                        log().all()
                        .baseUri(baseUri)
                        .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                        .expect().log().all().request();
                log.info("Request specification with log level ALL is created");
                break;
            case "headers":
                clientRequestSpecification =  RestAssured.given().
                        log().headers()
                        .baseUri(baseUri)
                        .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                        .expect().log().headers().request();
                log.info("Request specification with log level HEADERS is created");
                break;
            case "body":
                clientRequestSpecification =  RestAssured.given().
                        log().body()
                        .baseUri(baseUri)
                        .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                        .expect().log().body().request();
                log.info("Request specification with log level BODY is created");
                break;
            default:
                clientRequestSpecification = RestAssured.given().
                        log().all()
                        .baseUri(baseUri)
                        .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                        .expect().request();
                log.info("Request specification with log level ALL is created");
        }

        return clientRequestSpecification;
    }

    public RequestSpecification getClientRequestSpecification() {
        return clientRequestSpecification;
    }

}
