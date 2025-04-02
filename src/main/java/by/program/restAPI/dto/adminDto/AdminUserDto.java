package by.program.restAPI.dto.adminDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import by.program.restAPI.dto.roleDto.RoleDto;
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
public class AdminUserDto extends AbstractBaseEntityDto {

    private String email;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private int totalTask;
    private List<RoleDto> roles;
}
