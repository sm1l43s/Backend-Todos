package by.program.restAPI.rest;

import by.program.restAPI.dto.forUserDto.UserDto;
import by.program.restAPI.dto.forUserDto.UpdateUserDto;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import by.program.restAPI.responseEntity.CommonResponse;
import by.program.restAPI.responseEntity.ResponseFromServer;
import by.program.restAPI.responseEntity.ResponseListData;
import by.program.restAPI.security.jwt.JwtTokenProvider;
import by.program.restAPI.service.RoleService;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class UsersController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final Utils utils;
    private final RoleService roleService;

    @Autowired
    public UsersController(JwtTokenProvider jwtTokenProvider, UserService userService, Utils utils, RoleService roleService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.utils = utils;
        this.roleService = roleService;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity getAllUsers(@RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size,
                                      @RequestParam(name = "typeOrder", defaultValue = "asc") String typeOrder,
                                      @RequestParam(name = "orderFields", defaultValue = "id") String orderFields,
                                      @RequestParam(name = "search", defaultValue = "") String search) {
        CommonResponse response = null;
        Page<User> data = null;

        if (search.isEmpty()) {
            if (typeOrder.equals("asc")) {
                data = userService.getAllByStatusNot(PageRequest.of(page - 1, size, Sort.by(orderFields).ascending()),
                        Status.DELETED);
            } else {
                data = userService.getAllByStatusNot(PageRequest.of(page - 1, size, Sort.by(orderFields).descending()),
                        Status.DELETED);
            }

        } else {
            data = userService.getAllByStatusNotAndFirstNameContainingOrLastNameContaining(PageRequest.of(page - 1, size),
                    Status.DELETED, search, search);
        }

        if (data.isEmpty() || size > 100) {
            response = new CommonResponse(HttpStatus.NO_CONTENT, null, "No found content", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.NO_CONTENT);
        }

        List<UserDto> userDtoList = UserDto.fromListUserToListUserDto(data.getContent());
        response = new ResponseListData(HttpStatus.OK, userDtoList, "", 0, data.getTotalElements());
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserById(@PathVariable("id") long id) {
        User user = userService.findByIdAndStatusNot(id, Status.DELETED);
        CommonResponse response = null;

        if (user == null) {
            response = new CommonResponse(HttpStatus.NOT_FOUND, null, "Not found user by id: " + id, 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }
        response = new CommonResponse(HttpStatus.OK, UserDto.fromUserToUserDto(user), "", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @RequestMapping(value = "users", method = RequestMethod.PUT)
    public ResponseEntity updateDataUser(@RequestHeader("Authorization") String bearerToken,
                                         @RequestBody UpdateUserDto updateUserDto) {
        User user = utils.getUser(bearerToken);
        CommonResponse response = null;

        if (user == null) {
            response = new CommonResponse(HttpStatus.NOT_FOUND, null, "No found user", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.NOT_FOUND);
        }
        user.setEmail(updateUserDto.getEmail());
        user.setFirstName(updateUserDto.getFirstname());
        user.setLastName(updateUserDto.getLastname());
        user.setAboutMe(updateUserDto.getAboutMe());

        User updateUser = userService.update(user);
        response = new CommonResponse(HttpStatus.OK, UserDto.fromUserToUserDto(updateUser), "", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @RequestMapping(value = "users/avatar", method = RequestMethod.POST)
    public ResponseEntity updateAvatarUser(@RequestHeader("Authorization") String bearerToken,
                                           @RequestParam("file") MultipartFile file) {
        System.out.println("***************");
        System.out.println(file);
        User user = utils.getUser(bearerToken);
        CommonResponse response = null;

        if (user == null) {
            response = new CommonResponse(HttpStatus.NOT_FOUND, null, "No found user", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.NOT_FOUND);
        }
        Byte[] byteObjects = new Byte[0];
        try {
            byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setAvatar(byteObjects);
        User updateUser = userService.update(user);
        response = new CommonResponse(HttpStatus.OK, UserDto.fromUserToUserDto(updateUser), "", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @RequestMapping(value = "users", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String bearerToken) {
        User user = utils.getUser(bearerToken);

        user.setStatus(Status.DELETED);
        userService.update(user);

        CommonResponse response = new CommonResponse(HttpStatus.OK, null, "User deleted", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

}
