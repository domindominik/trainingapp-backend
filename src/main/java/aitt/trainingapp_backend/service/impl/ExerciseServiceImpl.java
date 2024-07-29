package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.dto.ExerciseDto;
import aitt.trainingapp_backend.model.ExerciseModel;
import aitt.trainingapp_backend.repository.ExerciseRepository;
import aitt.trainingapp_backend.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Override
    public ExerciseModel addExercise(ExerciseDto exerciseDto) {
        log.info("Adding new exercise with name: {}", exerciseDto.getName());
        ExerciseModel exercise = new ExerciseModel();
        exercise.setName(exerciseDto.getName());
        exercise.setRoles(exerciseDto.getRoles());
        exercise.setDescription(exerciseDto.getDescription());
        exercise.setMediaLink(exerciseDto.getMediaLink());
        exercise.setUserId(exerciseDto.getUserId());
        ExerciseModel savedExercise = exerciseRepository.save(exercise);
        log.info("Exercise added with ID: {}", savedExercise.getId());
        return savedExercise;
    }
}