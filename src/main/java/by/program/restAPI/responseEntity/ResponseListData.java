package by.program.restAPI.responseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListData  extends CommonResponse{

    private long totalElements;

    public ResponseListData(HttpStatus httpStatus, Object data, String error, int resultCode, long totalElements) {
        super(httpStatus, data, error, resultCode);
        this.totalElements = totalElements;
    }

}
