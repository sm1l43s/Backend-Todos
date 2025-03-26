package by.program.restAPI.utils;

import by.program.restAPI.dto.userDto.UserDto;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserUtil {

    public static UserDto createDto(User user) {

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .aboutMe(user.getAboutMe())
                .avatar(user.getAvatar())
                .isActive(user.isActive())
                .roles(RoleUtil.getDtos(user.getRoles()))
                .build();
    }

    public static UserDto createDtoWithTaskStatistic(User user) {
        List<Task> tasks = user.getTasks();

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .aboutMe(user.getAboutMe())
                .avatar(user.getAvatar())
                .isActive(user.isActive())
                .roles(RoleUtil.getDtos(user.getRoles()))
                .totalTask(tasks.size() - calculateCountTaskByStatus(tasks, Status.DELETED))
                .activeTask(calculateCountTaskByStatus(tasks, Status.ACTIVE))
                .completedTask(calculateCountTaskByStatus(tasks, Status.COMPLETED))
                .failedTask(calculateCountTaskByStatus(tasks, Status.FAILED))
                .build();
    }

    public static List<UserDto> convertToDtoListWithStatistics(List<User> users) {
        return users.stream()
                .map(UserUtil::createDtoWithTaskStatistic)
                .collect(Collectors.toList());
    }

    private static int calculateCountTaskByStatus(List<Task> tasks, Status status) {
        return (int) tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .count();
    }
}
