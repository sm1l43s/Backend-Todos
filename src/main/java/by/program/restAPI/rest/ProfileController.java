package by.program.restAPI.rest;

import by.program.restAPI.dto.taskDto.TaskDto;
import by.program.restAPI.dto.userDto.UpdateAboutMeDto;
import by.program.restAPI.dto.userDto.UserDto;
import by.program.restAPI.model.Task;
import by.program.restAPI.model.User;
import by.program.restAPI.service.TaskService;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.TaskUtil;
import by.program.restAPI.utils.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    static final String REST_URL = "/api/v1/profile";

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping()
    public UserDto getMe(@AuthenticationPrincipal AuthUser authUser) {
        User user = userService.findByIdWithTaskList(authUser.id());
        return UserUtil.createDtoWithTaskStatistic(user);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        userService.delete(authUser.id());
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody UpdateAboutMeDto updateAboutMeDto, @AuthenticationPrincipal AuthUser authUser) {
        String aboutMe = updateAboutMeDto.getAboutMe();
        userService.updateAboutMe(authUser.id(), aboutMe);
    }

    @PatchMapping(value = "/avatar", consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateAvatar(@RequestBody String avatarPath, @AuthenticationPrincipal AuthUser authUser) {
        userService.updateAvatar(authUser.id(), avatarPath);
    }

    @GetMapping("/tasks")
    public Page<TaskDto> getTaskUser(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(size = 50, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Task> tasks = taskService.getTasksForUser(authUser.id(), search, pageable);
        return tasks.map(TaskUtil::createDto);
    }


    // TODO
//    @PutMapping(value = "/{id}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    @Transactional
//    public void changePassword(@Valid @RequestBody UserDto userDto, @PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) {
//        log.debug("Change password for user {} with id={}", userDto, id);
//        if (!username.equals(authUser.getUser().getUsername())) {
//            throw new AuthenticationException("You can't change password for user " + id);
//        }
//        traineeService.changePassword(username, userDto.getNewPassword());
//    }
}
