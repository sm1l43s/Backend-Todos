package by.program.restAPI.dto.forUserDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAboutMeDto extends AbstractBaseEntityDto {

    @Size(max = 2000)
    private String aboutMe;
}
