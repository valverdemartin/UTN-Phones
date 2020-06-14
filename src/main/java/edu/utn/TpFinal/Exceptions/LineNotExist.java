package edu.utn.TpFinal.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class LineNotExist extends HttpClientErrorException {
    public LineNotExist(HttpStatus statusCode) {
        super(statusCode, "Line Not Exists");
    }
}
