package aitt.trainingapp_backend.controller;

import aitt.trainingapp_backend.dto.ExerciseDto;
import org.springframework.http.HttpStatus;
import aitt.trainingapp_backend.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
@Slf4j
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH')")
    public ResponseEntity<ExerciseDto> addExercise(@RequestBody ExerciseDto exerciseDto) {
        log.info("Received request to add new exercise");
        ExerciseDto savedExercise = exerciseService.addExercise(exerciseDto);
        log.info("Exercise added with ID: {}", savedExercise.getId());
        return ResponseEntity.ok(savedExercise);
    }
}
