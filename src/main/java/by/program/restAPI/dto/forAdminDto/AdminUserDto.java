package by.program.restAPI.dto.forAdminDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
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
    private Status status;
    private int totalTask;
    private List<RoleDto> roles;
}
