package by.program.restAPI.utils;

import by.program.restAPI.model.User;
import by.program.restAPI.security.jwt.JwtTokenProvider;
import by.program.restAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public Utils(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public User getUser(String bearerToken) {
        String token = jwtTokenProvider.resolveToken((bearerToken));
        if (token != null && jwtTokenProvider.validateToken(token)) {
            return userService.findByEmail(jwtTokenProvider.getEmail(token));
        }
        return null;
    }

}
