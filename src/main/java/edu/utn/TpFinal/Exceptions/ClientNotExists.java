package edu.utn.TpFinal.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ClientNotExists extends HttpClientErrorException {
    public ClientNotExists(HttpStatus statusCode) { super(statusCode, "Client Not Exists");
    }
}
