package aitt.trainingapp_backend.controller;

import aitt.trainingapp_backend.dto.ExerciseDto;
import aitt.trainingapp_backend.model.ExerciseModel;
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
    public ResponseEntity<ExerciseModel> addExercise(@RequestBody ExerciseDto exerciseDto) {
        log.info("Received request to add new exercise");
        ExerciseModel exercise = exerciseService.addExercise(exerciseDto);
        log.info("Exercise added with ID: {}", exercise.getId());
        return ResponseEntity.ok(exercise);
    }
}
