package v1.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum that represents the possible sources of Data.
 *
 */
public enum Source {

    TWITTER("Twitter"),
    FACEBOOK("Facebook"),
    LINKEDIN("Linkedin");

    private final String name;

    Source(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
