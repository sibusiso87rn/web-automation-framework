package starter.testing.core.util.environment.config;

import starter.testing.core.util.environment.entity.Entity;

public class ConfigValue extends Entity {

    private String key;
    private String value;

    public ConfigValue() {
    }

    public ConfigValue(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String name) {
        this.key = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
