package by.program.restAPI.dto;

import by.program.restAPI.model.Status;
import by.program.restAPI.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private long id;
    private String title;
    private String description;
    private Status status;
    private Date startDate;
    private Date endDate;

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
