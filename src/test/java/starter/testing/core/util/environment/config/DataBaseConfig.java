package starter.testing.core.util.environment.config;


import starter.testing.core.util.environment.entity.Entity;

public class DataBaseConfig extends Entity {

    private String servername;
    private String serverport;
    private String databasename;
    private String uniquekey;
    private boolean active;
    private User user;

    public String getDatabaseServer() {
        return servername;
    }

    public void setDatabaseServer(String databaseServer) {
        this.servername = databaseServer;
    }

    public String getDatabasePort() {
        return serverport;
    }

    public void setDatabasePort(String databasePort) {
        this.serverport = databasePort;
    }

    public String getDatabaseName() {
        return databasename;
    }

    public void setDatabaseName(String databaseName) {
        this.databasename = databaseName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getKey() {
        return uniquekey;
    }

    public void setKey(String uniqueKey) {
        this.uniquekey = uniqueKey;
    }
}
