package starter.testing.core.util.environment.entity;

import starter.testing.core.util.string.toString;

import java.io.Serializable;

public class Entity
        implements Serializable
{
    public String toString()
    {
        return new toString(this).toString();
    }
}
