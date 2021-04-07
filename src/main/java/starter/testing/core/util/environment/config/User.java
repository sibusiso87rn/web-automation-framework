package starter.testing.core.util.environment.config;

import starter.testing.core.util.environment.entity.Entity;

public class User extends Entity {

    private String username;
    private String password;
    private String uniquekey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniqueKey() {
        return uniquekey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniquekey = uniqueKey;
    }
}
