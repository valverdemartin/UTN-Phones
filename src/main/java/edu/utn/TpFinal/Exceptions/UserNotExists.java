package edu.utn.TpFinal.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/*public class UserNotExists extends HttpClientErrorException {
    public UserNotExists(HttpStatus statusCode) {
        super(statusCode, "User not exist!!!");
    }
}*/
public class UserNotExists extends Exception{
}
