package edu.utn.TpFinal.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ClientNotExist extends HttpClientErrorException {
    public ClientNotExist(HttpStatus statusCode) { super(statusCode, "Client Not Exists");
    }
}
