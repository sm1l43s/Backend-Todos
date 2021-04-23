package by.program.restAPI.responseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {

    private HttpStatus httpStatus;
    private Object data;
    private String messages;
    private int resultCode;

}
