package edu.utn.TpFinal.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class BillNotExist extends HttpClientErrorException {
    public BillNotExist(HttpStatus statusCode) {
        super(statusCode, "BIll Not Exists");
    }
}
