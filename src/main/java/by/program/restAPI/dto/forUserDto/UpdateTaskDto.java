package by.program.restAPI.dto.forUserDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import by.program.restAPI.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDto extends AbstractBaseEntityDto {

    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status status;

}
