package by.program.restAPI.rest;

import by.program.restAPI.dto.taskDto.TaskDto;
import by.program.restAPI.model.Task;
import by.program.restAPI.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static by.program.restAPI.utils.TaskUtil.createDto;
import static by.program.restAPI.utils.TaskUtil.createTask;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = TaskController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    static final String REST_URL = "/api/v1/users/tasks";

    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public TaskDto get(@PathVariable Long taskId) {
        Task task = taskService.findById(taskId);
        return createDto(task);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TaskDto create(@Valid @RequestBody TaskDto taskDto) {
        Task task = createTask(taskDto);
        Task savedtask = taskService.add(task);

        return createDto(savedtask);

    }

    @PutMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TaskDto update(@PathVariable long taskId, @Valid @RequestBody TaskDto taskDto) {

        Task updatedTask = taskService.update(taskId,
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getStartDate(),
                taskDto.getEndDate(),
                taskDto.getStatus(),
                taskDto.getUser());

        return createDto(updatedTask);
    }

    // Метод получения списка задач для текущего юзера ??
}
