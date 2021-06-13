package starter.testing.core.util.string;

import java.util.UUID;

/**
 * Created by Sibusiso Radebe.
 */

public class UuidUtil {

    public static String getuuid(){
        return UUID.randomUUID().toString();
    }

    public static String getUuidNospecialChar(){
        return getuuid().replace("-","");
    }


}
