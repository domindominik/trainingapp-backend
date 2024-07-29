package aitt.trainingapp_backend.dto;

import aitt.trainingapp_backend.model.ExerciseCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
public class ExerciseDto {
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name should have 2 to 100 characters")
    private String name;
    private Set<ExerciseCategory> category;
    private String description;
    private String mediaLink;
    @NotEmpty(message = "User Id cannot be null")
    private Long userId;
}
