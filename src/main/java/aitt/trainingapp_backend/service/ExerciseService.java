package aitt.trainingapp_backend.service;

import aitt.trainingapp_backend.dto.ExerciseDto;

import java.util.List;

public interface ExerciseService {
    ExerciseDto addExercise(ExerciseDto exerciseDto);
    List<ExerciseDto> getAllExercises();
    List<ExerciseDto> getExercisesByUserId(Long userId);
}
