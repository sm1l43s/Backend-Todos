package by.program.restAPI.rest;


import by.program.restAPI.dto.adminDto.AdminUserDto;
import by.program.restAPI.dto.adminDto.ReportDto;
import by.program.restAPI.dto.adminDto.UpdateUserDto;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import by.program.restAPI.service.TaskService;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.AdminUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    static final String REST_URL = "/api/v1/admin";

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/users")
    public Page<AdminUserDto> getUsers(
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<User> users = search.isBlank()
                ? userService.findAllActive(true, pageable)
                : userService.findAllActiveAndNameContaining(true, search, pageable);

        return users.map(AdminUtil::createDto);
    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto userDto) {
        userService.update(id,
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.isActive(),
                userDto.getRoles(),
                userDto.getAboutMe());
    }

    @GetMapping("/report")
    @Transactional
    public ReportDto getReport(@RequestParam(defaultValue = "1") int days) {
        return new ReportDto(
                userService.countActiveUsers(),
                userService.countNewUsers(days),
                taskService.getCountByStatus(Status.ACTIVE),
                taskService.getCountByStatus(Status.COMPLETED),
                taskService.getCountByStatus(Status.DELETED)
        );
    }
}
