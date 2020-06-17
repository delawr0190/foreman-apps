package mn.foreman.model;

import lombok.Builder;
import lombok.Data;

/** A mining pool. */
@Data
@Builder
public class Pool {

    /** The pool password. */
    private final String password;

    /** The pool URL. */
    private final String url;

    /** The pool username. */
    private final String username;
}
