package by.program.restAPI.dto.userDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import by.program.restAPI.dto.roleDto.RoleDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends AbstractBaseEntityDto {

    private String email;
    private String firstName;
    private String lastName;
    private String aboutMe;
    private Byte[] avatar;
    private boolean isActive;
    private List<RoleDto> roles;
    private int totalTask;
    private int activeTask;
    private int completedTask;
    private int failedTask;
}
