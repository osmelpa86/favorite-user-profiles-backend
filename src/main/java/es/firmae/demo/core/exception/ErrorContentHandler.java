package es.firmae.demo.core.exception;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ErrorContentHandler {

    @Resource
    private Environment env;

    private static final String NOT_FOUND = "app-error-resource-not-found";
    private static final String ALREADY_EXIST = "app-error-already-exist";
    private static final String UNPROCESSABLE_ENTITY = "app-error-unprocessable-entity";

    public String alreadyExistAlertMessage(String resourceName, String fieldName, String fieldValue) {
        return env.getProperty("resource") + " " + resourceName + " " + env.getProperty("with") + " " +
                fieldName + " = " + fieldValue + " " + env.getProperty("already.exist");
    }

    public String notFoundAlertMessage(String resourceName, String fieldName, String fieldValue) {
        return head(resourceName, fieldName, fieldValue) + env.getProperty("not.found");
    }

    public HttpHeaders alreadyExistAlert(String errorMessage) {
        return createHeaders(ALREADY_EXIST, errorMessage);
    }

    public HttpHeaders notFoundAlert(String errorMessage) {
        return createHeaders(NOT_FOUND, errorMessage);
    }

    public String validationInModelAlertMessage(String errorMessage) {
        return errorMessage.split(",").length > 1 ? env.getProperty("validation.intro") + " " + errorMessage : errorMessage;
    }

    public HttpHeaders validationInModelAlert(String errorMessage) {
        return createHeaders(UNPROCESSABLE_ENTITY, errorMessage);
    }

    private String head(String resourceName, String fieldName, String fieldValue) {
        return env.getProperty("resource") + " " + resourceName + " " + env.getProperty("with") + " " +
                fieldName + " = " + fieldValue + " ";
    }

    private static HttpHeaders createHeaders(String header, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(header, message);
        return headers;
    }
}
