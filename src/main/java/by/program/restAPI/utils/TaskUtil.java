package by.program.restAPI.utils;

import by.program.restAPI.dto.taskDto.TaskDto;
import by.program.restAPI.model.Task;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TaskUtil {

    public static Task createTask(TaskDto taskDto) {

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setEndDate(taskDto.getEndDate());
        task.setUser(taskDto.getUser());

        return task;
    }

    public static TaskDto createDto(Task task) {

        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .created(task.getCreated())
                .updated(task.getUpdated())
                .build();
    }


    // TODO

    public static List<TaskDto> fromListTaskToListTaskDto(List<Task> tasks) {
        List<TaskDto> taskDtoList = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            taskDtoList.add(createDto(tasks.get(i)));
        }

        return taskDtoList;
    }
}
