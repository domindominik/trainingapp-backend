package aitt.trainingapp_backend.model;

public enum ExerciseCategory {
    Strength,
    Speed,
    Dynamics,
    Endurance;

    public String getName() {
        return this.name();
    }
}
