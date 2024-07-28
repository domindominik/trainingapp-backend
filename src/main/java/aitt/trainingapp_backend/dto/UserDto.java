package aitt.trainingapp_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 4, message = "Password should have at least 8 characters")
    private String password;
}
