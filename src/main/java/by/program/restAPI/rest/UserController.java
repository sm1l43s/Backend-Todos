package by.program.restAPI.rest;

import by.program.restAPI.dto.forUserDto.UpdateAboutMeDto;
import by.program.restAPI.dto.forUserDto.UserDto;
import by.program.restAPI.model.User;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    static final String REST_URL = "/api/v1";

    private final UserService userService;

    @GetMapping("/users/{id}")
    public UserDto get(@PathVariable("id") long id) {
        User user = userService.findByIdWithTaskList(id);
        return UserUtil.createDtoWithTaskStatistic(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void update(@PathVariable Long id, @Valid @RequestBody UpdateAboutMeDto updateAboutMeDto) {
        String aboutMe = updateAboutMeDto.getAboutMe();
        userService.updateAboutMe(id, aboutMe);
    }

    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void updateAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        userService.updateAvatar(id, file);
    }

    @GetMapping("/users")
    public Page<UserDto> getAll(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return users.map(UserUtil::createDtoWithTaskStatistic);
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
