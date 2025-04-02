package by.program.restAPI.rest.task;

import by.program.restAPI.dto.taskDto.TaskDto;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import by.program.restAPI.service.TaskService;
import by.program.restAPI.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
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
    private final UserService userService;

    @GetMapping("/{taskId}")
    public TaskDto get(@PathVariable Long taskId) {
        log.debug("Get task with id {}", taskId);
        Task task = taskService.findById(taskId);
        return createDto(task, task.getUser());
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long taskId) {
        log.debug("Delete task with id {}", taskId);
        taskService.delete(taskId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public TaskDto create(@Valid @RequestBody TaskDto taskDto) {
        log.debug("Create new task");

        User user = userService.findById(taskDto.getUserId());
        Task task = createTask(taskDto, user);
        Task savedtask = taskService.add(task);

        return createDto(savedtask, user);
    }

    @PutMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public TaskDto update(@PathVariable long taskId, @Valid @RequestBody TaskDto taskDto) {
        log.debug("Update task with id {}", taskId);
        User user = userService.findById(taskDto.getUserId());

        Task updatedTask = taskService.update(taskId,
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getStartDate(),
                taskDto.getEndDate(),
                taskDto.getStatus(),
                user);

        return createDto(updatedTask, user);
    }
}
