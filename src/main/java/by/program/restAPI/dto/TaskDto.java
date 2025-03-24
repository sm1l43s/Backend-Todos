package by.program.restAPI.dto;

import by.program.restAPI.model.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto extends AbstractBaseEntityDto {

    private String title;
    private String description;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
}
