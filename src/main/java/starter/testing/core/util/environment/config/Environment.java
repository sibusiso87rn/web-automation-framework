package starter.testing.core.util.environment.config;

import starter.testing.core.util.environment.entity.Entity;

public class Environment extends Entity {

    private String id;

    public String getEnvironmentName() {
        return id;
    }

    public void setEnvironment(String id) {
        this.id = id;
    }
}
