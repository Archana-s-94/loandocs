package nz.archana.loandocs.exception;

import java.util.Set;

public class MissingPlaceholderException extends RuntimeException {
    public MissingPlaceholderException(Set<String> missing) {
        super("Missing values for placeholders: " + String.join(", ", missing));
    }
}
