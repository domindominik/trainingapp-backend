package aitt.trainingapp_backend.dto;

import aitt.trainingapp_backend.model.ExerciseCategory;
import jakarta.persistence.*;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name should have 2 to 100 characters")
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<ExerciseCategory> roles;
    private String description;
    private String mediaLink;
    @NotEmpty
    private Long userId;
}
