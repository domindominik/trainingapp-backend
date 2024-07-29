package aitt.trainingapp_backend.service.impl;

import aitt.trainingapp_backend.dto.ExerciseDto;
import aitt.trainingapp_backend.model.ExerciseModel;
import aitt.trainingapp_backend.repository.ExerciseRepository;
import aitt.trainingapp_backend.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public List<ExerciseDto> getAllExercises() {
        log.info("Fetching all exercises");
        List<ExerciseModel> exercises = exerciseRepository.findAll();
        log.info("Fetched all exercises, count: {}", exercises.size());
        return exercises.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Override
    public List<ExerciseDto> getExercisesByUserId(Long userId) {
        log.info("Fetching exercises for user ID: {}", userId);
        List<ExerciseModel> exercises = exerciseRepository.findByUserId(userId);
        log.info("Fetched exercises for user ID: {}, count: {}", userId, exercises.size());
        return exercises.stream().map(this::convertToDto).collect(Collectors.toList());
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
    private ExerciseDto convertToDto(ExerciseModel exercise) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setName(exercise.getName());
        exerciseDto.setCategory(exercise.getCategory());
        exerciseDto.setDescription(exercise.getDescription());
        exerciseDto.setMediaLink(exercise.getMediaLink());
        exerciseDto.setUserId(exercise.getUserId());
        return exerciseDto;
    }
}