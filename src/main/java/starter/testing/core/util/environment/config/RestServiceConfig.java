package starter.testing.core.util.environment.config;

import starter.testing.core.util.environment.entity.Entity;

public class RestServiceConfig extends Entity {

    private String serverPort;
    private String baseHost;
    private String basePath;
    private String uniqueKey;

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getBaseHost() {
        return baseHost;
    }

    public void setBaseHost(String baseHost) {
        this.baseHost = baseHost;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getFullPath(){
        return getBaseHost()+getBasePath();
    }

    public String getKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
