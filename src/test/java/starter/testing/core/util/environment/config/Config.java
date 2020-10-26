package starter.testing.core.util.environment.config;

import starter.testing.core.util.string.toString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Config implements Serializable {

    private Environment environment             = new Environment();
    private OS userOs                           = new OS();
    private List<ConfigValue> configValues      = new ArrayList();
    private List<RestServiceConfig> restServiceList    = new ArrayList();
    private List<DataBaseConfig> dataBaseList          = new ArrayList();

    public List<ConfigValue> getConfigValues() {
        return configValues;
    }

    public void setConfigValues(List<ConfigValue> configValues) {
        this.configValues = configValues;
    }

    public OS getUserOs() {
        return userOs;
    }

    public void setUserOs(OS userOs) {
        this.userOs = userOs;
    }

    public List<DataBaseConfig> getDataBaseList() {
        return dataBaseList;
    }

    public void setDataBaseList(List<DataBaseConfig> dataBaseList) {
        this.dataBaseList = dataBaseList;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<RestServiceConfig> getRestServiceList() {
        return restServiceList;
    }

    public void setRestServiceList(List<RestServiceConfig> restServiceList) {
        this.restServiceList = restServiceList;
    }

    public String toString()
    {
        return new toString(this).toString();
    }
}
