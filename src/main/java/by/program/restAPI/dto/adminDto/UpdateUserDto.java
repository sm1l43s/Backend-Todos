package by.program.restAPI.dto.adminDto;

import by.program.restAPI.dto.AbstractBaseEntityDto;
import by.program.restAPI.dto.roleDto.RoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto extends AbstractBaseEntityDto {

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 1, max = 128)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 128)
    private String lastName;

    @NotNull
    @JsonProperty("isActive")
    private boolean isActive;

    @NotEmpty
    private List<RoleDto> roles;

    @NotBlank
    @Size(max = 2000)
    private String aboutMe;
}
