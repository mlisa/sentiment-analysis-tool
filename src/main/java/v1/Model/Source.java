package v1.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum that represents the possible sources of Data.
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
