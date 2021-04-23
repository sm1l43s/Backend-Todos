package by.program.restAPI.dto.forUserDto;

import by.program.restAPI.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDto {

    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status status;

}
