package aitt.trainingapp_backend.model;

public enum Role {
    ROLE_COACH,
    ROLE_ADMIN,
    ROLE_MENTEE;

    public String getName() {
        return this.name();
    }
}
