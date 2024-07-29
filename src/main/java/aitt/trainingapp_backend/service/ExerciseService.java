package aitt.trainingapp_backend.service;

import aitt.trainingapp_backend.dto.ExerciseDto;
import aitt.trainingapp_backend.model.ExerciseModel;

public interface ExerciseService {
    ExerciseModel addExercise(ExerciseDto exerciseDto);
}
