package by.program.restAPI.rest;

import by.program.restAPI.dto.RoleDto;
import by.program.restAPI.dto.forAdminDto.AdminUserDto;
import by.program.restAPI.dto.forAdminDto.ReportsDto;
import by.program.restAPI.dto.forAdminDto.UpdateUserDto;
import by.program.restAPI.model.Role;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import by.program.restAPI.responseEntity.CommonResponse;
import by.program.restAPI.responseEntity.ResponseFromServer;
import by.program.restAPI.responseEntity.ResponseListData;
import by.program.restAPI.service.RoleService;
import by.program.restAPI.service.TaskService;
import by.program.restAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminController {

    private final TaskService taskService;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(TaskService taskService, UserService userService, RoleService roleService) {
        this.taskService = taskService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "reports", method = RequestMethod.GET)
    public ResponseEntity getReports(@RequestHeader("Authorization") String bearerToken) {

        ReportsDto reportsDto = new ReportsDto(
                taskService.getCountEntities(),
                userService.countEntities(),
                userService.countUsersByBetweenDate(Date.valueOf(LocalDate.now()), getDateFewDaysAgo(1)),
                taskService.getCountByStatus(Status.ACTIVE),
                taskService.getCountByStatus(Status.COMPLETED),
                taskService.getCountByStatus(Status.DELETED)
        );

        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, reportsDto, "", 0);

        return ResponseFromServer.returnResult(commonResponse, HttpStatus.OK);
    }

    private Date getDateFewDaysAgo(int days) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return new Date(cal.getTimeInMillis());
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity getUsers(@RequestHeader("Authorization") String bearerToken,
                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<User> data = userService.getAllByStatusNot(PageRequest.of(page - 1, size), Status.DELETED);
        CommonResponse response = null;
        System.out.println(data.getContent());
        if (data.isEmpty()) {
            response = new CommonResponse(HttpStatus.OK, null, "No found users", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }
       response = new ResponseListData(HttpStatus.OK, AdminUserDto.fromListUserToListAdminUserDto(data.getContent()), "", 0, data.getTotalElements());
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    // ToDo: Refactor this method
    @RequestMapping(value = "users", method = RequestMethod.PUT)
    public ResponseEntity updateUsers(@RequestHeader("Authorization") String bearerToken,
                                      @RequestBody UpdateUserDto updateUserDto) {
        User user = userService.findByEmail(updateUserDto.getEmail());
        CommonResponse response = null;
        if (user == null) {
            response = new CommonResponse(HttpStatus.NOT_FOUND, null, "Not found user with email:" +
                    updateUserDto.getEmail(), 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }

        user.setRoles(getRoles(updateUserDto.getRoles()));
        user.setStatus(updateUserDto.getStatus());
        user.setEmail(updateUserDto.getEmail());
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());

        User updatedUser = userService.update(user);
        response = new CommonResponse(HttpStatus.OK, AdminUserDto.fromUserToAdminUserDto(updatedUser), "", 0);

        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    private List<Role> getRoles(List<RoleDto> roleDtoList) {
        List<Role> roles = new ArrayList<>();
        for(RoleDto roleDto: roleDtoList) {
            roles.add(roleService.findByName(roleDto.getName()));
        }
        return roles;
    }

}
