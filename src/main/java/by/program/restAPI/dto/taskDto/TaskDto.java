package by.program.restAPI.dto.taskDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import by.program.restAPI.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Status status;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private long userId;

    private LocalDate created;

    private LocalDate updated;
}
