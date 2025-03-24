package by.program.restAPI.dto.forUserDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import by.program.restAPI.utils.RoleUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends AbstractBaseEntityDto {

    private String email;
    private String firstName;
    private String lastName;
    private String aboutMe;
    private Byte[] avatar;
    private boolean isActive;
    private List<RoleDto> roles;
//    Нужно ли делить tasks по статусу выполнения
    private int totalTask;
    private int activeTask;
    private int completedTask;
    private int failedTask;
}
