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
    public ExerciseDto addExercise(ExerciseDto exerciseDto) {
        log.info("Adding new exercise with name: {}", exerciseDto.getName());
        ExerciseModel exercise = convertToModel(exerciseDto);
        ExerciseModel savedExercise = exerciseRepository.save(exercise);
        log.info("Exercise added with ID: {}", savedExercise.getId());
        return convertToDto(savedExercise);
    }
    private ExerciseModel convertToModel(ExerciseDto exerciseDto) {
        ExerciseModel exercise = new ExerciseModel();
        exercise.setName(exerciseDto.getName());
        exercise.setCategory(exerciseDto.getCategory());
        exercise.setDescription(exerciseDto.getDescription());
        exercise.setMediaLink(exerciseDto.getMediaLink());
        exercise.setUserId(exerciseDto.getUserId());
        return exercise;
    }
    private ExerciseDto convertToDto(ExerciseModel exerciseModel) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exerciseModel.getId());
        exerciseDto.setName(exerciseModel.getName());
        exerciseDto.setCategory(exerciseModel.getCategory());
        exerciseDto.setDescription(exerciseModel.getDescription());
        exerciseDto.setMediaLink(exerciseModel.getMediaLink());
        exerciseDto.setUserId(exerciseModel.getUserId());
        return exerciseDto;
    }
}