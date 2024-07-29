package aitt.trainingapp_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
public class ExerciseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name should have 2 to 100 characters")
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<ExerciseCategory> category;
    private String description;
    private String mediaLink;
    @NotEmpty(message = "User Id cannot be null")
    private Long userId;
}
