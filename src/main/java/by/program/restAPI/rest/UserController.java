package by.program.restAPI.rest;

import by.program.restAPI.dto.taskDto.TaskDto;
import by.program.restAPI.dto.userDto.UserDto;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import by.program.restAPI.service.TaskService;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.TaskUtil;
import by.program.restAPI.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    static final String REST_URL = "/api/v1/users";

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable("id") long id) {
        User user = userService.findByIdWithTaskList(id);
        return UserUtil.createDtoWithTaskStatistic(user);
    }

    @GetMapping
    public Page<UserDto> getAll(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return users.map(UserUtil::createDtoWithTaskStatistic);
    }

    @GetMapping("/{id}/tasks")
    public Page<TaskDto> getTaskUser(
            @PathVariable Long id,
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(size = 50, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Task> tasks = taskService.getTasksForUser(id, search, pageable);
        User user = userService.findById(id);
        return tasks.map(task -> TaskUtil.createDto(task, user));
    }

}
