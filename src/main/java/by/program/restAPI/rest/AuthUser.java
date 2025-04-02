package by.program.restAPI.rest;

import by.program.restAPI.exception.NotFoundException;
import by.program.restAPI.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    @Getter
    private final User user;

    @Getter
    @Setter
    private String token;

    public AuthUser(@NonNull User user) {
        super(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
        this.user = user;
    }

    public static Optional<AuthUser> safeGet() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(AuthUser.class::isInstance)
                .map(AuthUser.class::cast);
    }

    public static AuthUser get() {
        return safeGet().orElseThrow(() -> new NotFoundException("No authorized user found"));
    }

    public long id() {
        return user.getId();
    }

    @Override
    public String toString() {
        return "AuthUser:" + user.getId() + '[' + user.getEmail() + ']';
    }
}
