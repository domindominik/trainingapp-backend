package aitt.trainingapp_backend.repository;

import aitt.trainingapp_backend.model.ExerciseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseModel, Long> {
    List<ExerciseModel> findByUserId(Long userId);
}
