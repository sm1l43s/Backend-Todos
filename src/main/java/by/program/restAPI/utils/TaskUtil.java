package by.program.restAPI.utils;

import by.program.restAPI.dto.taskDto.TaskDto;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskUtil {

    public static Task createTask(TaskDto taskDto, User user) {

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setEndDate(taskDto.getEndDate());
        task.setUser(user);

        return task;
    }

    public static TaskDto createDto(Task task, User user) {

        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .created(task.getCreated())
                .updated(task.getUpdated())
                .userId(task.getUser().getId())
                .build();
    }
}
