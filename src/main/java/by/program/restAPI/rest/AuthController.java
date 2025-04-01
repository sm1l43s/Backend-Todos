package by.program.restAPI.rest;

import by.program.restAPI.dto.SignUpDto;
import by.program.restAPI.dto.authDto.AuthResponseDto;
import by.program.restAPI.dto.authDto.LoginRequestDto;
import by.program.restAPI.dto.userDto.UserDto;
import by.program.restAPI.model.User;
import by.program.restAPI.security.JWTProvider;
import by.program.restAPI.service.UserService;
import by.program.restAPI.utils.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = AuthController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    static final String REST_URL = "/api/v1/auth";

    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final UserService userService;

    @PostMapping("/login")
    @Transactional
    public AuthResponseDto authenticate(@RequestBody LoginRequestDto loginRequest) {
        String email = loginRequest.getEmail();
        log.info("Attempt to login by user: {}", email);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.createToken(email);
        log.info("Login successful for user: {}", email);

        return new AuthResponseDto(jwt);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoginRequestDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        log.debug("Register a new user {}", signUpDto);

        User newUser = userService.create(
                signUpDto.getEmail(),
                signUpDto.getPassword(),
                signUpDto.getFirstname(),
                signUpDto.getLastname()
        );

        LoginRequestDto loginRequestDto = new LoginRequestDto(
                newUser.getEmail(),
                newUser.getPassword());

        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(UserController.REST_URL + "/{id}")
                .buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(loginRequestDto);
    }

    @GetMapping()
    public UserDto getMe(@AuthenticationPrincipal AuthUser authUser) {
        User user = userService.findById(authUser.id());
        return UserUtil.createDtoWithTaskStatistic(user);
    }
}