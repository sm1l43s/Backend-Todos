package by.program.restAPI.dto.forUserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTaskDto {

    private String title;
    private String description;
    private Date startDate;
    private Date endDate;

}
