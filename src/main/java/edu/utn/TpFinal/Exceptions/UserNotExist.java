package edu.utn.TpFinal.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.HttpHeadersReturnValueHandler;

public class UserNotExist  extends HttpClientErrorException {
    public UserNotExist(HttpStatus statusCode) {
        super(statusCode, "User not exist!!!");
    }
}
