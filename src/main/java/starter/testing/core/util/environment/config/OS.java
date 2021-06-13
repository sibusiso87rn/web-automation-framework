package starter.testing.core.util.environment.config;

import starter.testing.core.util.environment.entity.Entity;

public class OS extends Entity {

    private String os;

    public OS(){
        String operSys = System.getProperty("os.name").toLowerCase();
        if (operSys.contains("win")) {
            os = "windows";
        } else if (operSys.contains("nix") || operSys.contains("nux")
                || operSys.contains("aix")) {
            os = "linux";
        } else if (operSys.contains("mac")) {
            os = "mac";
        } else if (operSys.contains("sunos")) {
            os = "solaris";
        }else{
            os = "undefinied";
        }
    }

    public String getOS() {
        return os;
    }

    public void setOS(String OS) {
        this.os = OS;
    }

}
