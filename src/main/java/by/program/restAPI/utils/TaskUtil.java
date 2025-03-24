package by.program.restAPI.utils;

import by.program.restAPI.dto.TaskDto;
import by.program.restAPI.model.Task;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TaskUtil {

    public static TaskDto fromTaskToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setStartDate(task.getStartDate());
        taskDto.setEndDate(task.getEndDate());
        return taskDto;
    }

    public static List<TaskDto> fromListTaskToListTaskDto(List<Task> tasks) {
        List<TaskDto> taskDtoList = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            taskDtoList.add(fromTaskToTaskDto(tasks.get(i)));
        }

        return taskDtoList;
    }
}
