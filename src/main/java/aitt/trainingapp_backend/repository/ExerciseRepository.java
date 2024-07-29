package aitt.trainingapp_backend.repository;

import aitt.trainingapp_backend.model.ExerciseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseModel, Long> {
}
