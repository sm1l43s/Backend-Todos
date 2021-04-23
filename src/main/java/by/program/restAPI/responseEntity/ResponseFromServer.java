package by.program.restAPI.responseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFromServer {

    public static ResponseEntity returnResult(Object response, HttpStatus httpStatus) {
        return new ResponseEntity(response, httpStatus);
    }

}
