package by.program.restAPI.dto.forAdminDto;

import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    private String email;
    private String firstName;
    private String lastName;
    private Status status;
    private List<RoleDto> roles;

}
