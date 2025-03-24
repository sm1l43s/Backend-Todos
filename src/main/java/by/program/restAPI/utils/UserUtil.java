package by.program.restAPI.utils;

import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.dto.forUserDto.UserDto;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@UtilityClass
public class UserUtil {

    public static UserDto fromUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAboutMe(user.getAboutMe());
        userDto.setAvatar(user.getAvatar());
        if (user.getTasks() != null) {
            userDto.setTotalTask(user.getTasks().size() - calculateCountTaskByStatus(user.getTasks(), Status.DELETED));
            userDto.setActiveTask(calculateCountTaskByStatus(user.getTasks(), Status.ACTIVE));
            userDto.setCompletedTask(calculateCountTaskByStatus(user.getTasks(), Status.COMPLETED));
            userDto.setFailedTask(calculateCountTaskByStatus(user.getTasks(), Status.FAILED));
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
            roleDtoList.add(RoleUtil.fromRoleToRoleDto(roles.get(i)));
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
