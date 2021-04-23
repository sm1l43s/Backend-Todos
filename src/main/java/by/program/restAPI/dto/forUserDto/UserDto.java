package by.program.restAPI.dto.forUserDto;

import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String aboutMe;
    private Byte[] avatar;
    private Date dateRegistered;
    private int totalTask;
    private int activeTask;
    private int completedTask;
    private int failedTask;
    private List<RoleDto> roles;

    public static UserDto fromUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAboutMe(user.getAboutMe());
        userDto.setAvatar(user.getAvatar());
        userDto.setDateRegistered(user.getCreated());
        if (user.getTaskList() != null) {
            userDto.setTotalTask(user.getTaskList().size() - calculateCountTaskByStatus(user.getTaskList(), Status.DELETED));
            userDto.setActiveTask(calculateCountTaskByStatus(user.getTaskList(), Status.ACTIVE));
            userDto.setCompletedTask(calculateCountTaskByStatus(user.getTaskList(), Status.COMPLETED));
            userDto.setFailedTask(calculateCountTaskByStatus(user.getTaskList(), Status.FAILED));
        }
        userDto.setRoles(transfer(user.getRoles()));

        return userDto;
    }

    public static List<UserDto> fromListUserToListUserDto(List<User> users) {
        Iterator iterator = users.iterator();
        List<UserDto> userDtoList = new ArrayList<>();

        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            userDtoList.add(fromUserToUserDto(user));
        }

        return userDtoList;
    }

    private static List<RoleDto> transfer(List<Role> roles) {
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            roleDtoList.add(RoleDto.fromRoleToRoleDto(roles.get(i)));
        }
        return roleDtoList;
    }

    private static int calculateCountTaskByStatus(List<Task> tasks, Status status) {
        if(tasks.size() == 0) return 0;

        int count = 0;
        for (Task task: tasks) {
            if (task.getStatus().equals(status)) count++;
        }
        return count;
    }
}
