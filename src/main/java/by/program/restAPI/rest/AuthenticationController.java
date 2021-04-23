package by.program.restAPI.rest;

import by.program.restAPI.dto.SignUpDto;
import by.program.restAPI.dto.forUserDto.UserDto;
import by.program.restAPI.model.User;
import by.program.restAPI.responseEntity.CommonResponse;
import by.program.restAPI.responseEntity.ResponseFromServer;
import by.program.restAPI.security.jwt.JwtTokenProvider;
import by.program.restAPI.dto.AuthenticationRequestDto;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final Utils utils;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider, UserService userService, Utils utils) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.utils = utils;
    }

    @PostMapping("signin")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        CommonResponse response = null;
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            User user = userService.findByEmail(email);

            if (user == null) {
                response = new CommonResponse(HttpStatus.NOT_FOUND, null, "User with email" + email + " not found", 1);
                return ResponseFromServer.returnResult(response, HttpStatus.NOT_FOUND);
            }

            String token = jwtTokenProvider.createToken(email, user.getRoles());

            Map<Object, Object> data = new HashMap<>();
            data.put("user", UserDto.fromUserToUserDto(user));
            data.put("token", token);
            response = new CommonResponse(HttpStatus.OK, data, "", 0);

            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            response = new CommonResponse(HttpStatus.NOT_FOUND, null, "Invalid email or password", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("signup")
    public ResponseEntity signUp(@RequestBody SignUpDto signUpDto) {
        CommonResponse response = null;

        User userExistsByEmail = userService.findByEmail(signUpDto.getEmail());

        if (userExistsByEmail != null) {
            response = new CommonResponse(HttpStatus.OK, null, "This email is occupied by another user.", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }

        User user = new User(signUpDto.getEmail(), signUpDto.getFirstname(),
                signUpDto.getLastname(), signUpDto.getPassword(),null,null, null, null);
        User registeredUser = userService.register(user);
         response = new CommonResponse(HttpStatus.OK, UserDto.fromUserToUserDto(registeredUser),
                "New account created", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }

    @GetMapping("me")
    public ResponseEntity getInfoAboutAuthUser(@RequestHeader("Authorization") String bearerToken) {
        User user = utils.getUser(bearerToken);
        CommonResponse response = null;

        if (user == null) {
            response= new CommonResponse(HttpStatus.NOT_FOUND, null, "You are not authorized", 1);
            return ResponseFromServer.returnResult(response, HttpStatus.NOT_FOUND);
        }

        response = new CommonResponse(HttpStatus.OK, UserDto.fromUserToUserDto(user), "", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }
}
