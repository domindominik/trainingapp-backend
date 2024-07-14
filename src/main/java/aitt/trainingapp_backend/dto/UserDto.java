package aitt.trainingapp_backend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String password;
}
