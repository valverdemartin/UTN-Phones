package edu.utn.TpFinal.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class BillNotExists extends HttpClientErrorException {
    public BillNotExists(HttpStatus statusCode) {
        super(statusCode, "BIll Not Exists");
    }
}
