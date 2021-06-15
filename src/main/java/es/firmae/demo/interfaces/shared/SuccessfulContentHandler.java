package es.firmae.demo.interfaces.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuccessfulContentHandler {

    private final Environment env;

    private static final String SUCCESS_HEADER = "app-success";

    public String successCreateAlert(String entityName) {
        return entityName + " " + env.getProperty("add.success");
    }

    public String successUpdateAlert(String entityName) {
        return entityName + " " + env.getProperty("update.success");
    }

    public String successDeleteAlert(String entityName) {
        return entityName + " " + env.getProperty("delete.success");
    }

    public String errorChangePassword() {
        return env.getProperty("updatePassword.error");
    }

    public String successChangePassword() {
        return env.getProperty("success.error");
    }

    public String successDeleteBlockAlert(String entityName) {
        return entityName + " " + env.getProperty("deleteAll.success");
    }

    public static HttpHeaders createHeaders(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SUCCESS_HEADER, message);
        return headers;
    }
}
