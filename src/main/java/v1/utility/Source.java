package v1.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum that represents the possible sources of Data.
 *
 * TODO: quella questione del mapping dei tweet che tecnicamente nemmeno salvo quindi boh
 *
 */
public enum Source {

    TWITTER("Twitter"),
    FACEBOOK("Facebook"),
    LINKEDIN("Linkedin"),
    TEST("Test");

    private final String name;

    Source(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
