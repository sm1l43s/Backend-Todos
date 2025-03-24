package by.program.restAPI.dto.forUserDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public class UpdateUserDto extends AbstractBaseEntityDto {

    private String email;
    private String firstname;
    private String lastname;
    private String aboutMe;

}
