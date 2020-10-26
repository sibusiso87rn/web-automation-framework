package starter.testing.core.util.string;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class toString
{
    private Object o;

    public toString(Object o)
    {
        this.o = o;
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        boolean methodsFound = false;
        try {
            Class clazz = this.o.getClass();
            s.append(new StringBuilder().append(clazz.getSimpleName()).append("(").toString());
            Method[] methods = clazz.getMethods();
            for (int x = 0; x < methods.length; x++) {
                Method method = methods[x];
                if ((!Modifier.isPublic(method.getModifiers())) ||
                        (!method
                                .getName().startsWith("get")) ||
                        (method
                                .getParameterTypes().length != 0)) continue;
                Method m = clazz.getMethod(new StringBuilder().append("set").append(method.getName().substring(3)).toString(), new Class[] { method.getReturnType() });
                if (m != null) {
                    s.append(new StringBuilder().append(method.getName().substring(3)).append("[").toString());
                    Object result = method.invoke(this.o, new Object[0]);
                    s.append(result != null ? result.toString() : "null");
                    s.append("], ");
                    methodsFound = true;
                }
            }
        }
        catch (Exception localException) {
        }
        if (methodsFound) {
            s.replace(s.length() - 2, s.length(), "");
        }
        s.append(")");
        return s.toString();
    }

}