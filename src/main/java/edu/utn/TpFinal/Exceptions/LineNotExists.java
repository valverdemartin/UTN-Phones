package edu.utn.TpFinal.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class LineNotExists extends HttpClientErrorException {
    public LineNotExists(HttpStatus statusCode) {
        super(statusCode, "Line Not Exists");
    }
}
