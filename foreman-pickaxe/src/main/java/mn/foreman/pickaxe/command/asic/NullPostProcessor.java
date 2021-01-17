package mn.foreman.pickaxe.command.asic;

import mn.foreman.api.model.CommandStart;
import mn.foreman.pickaxe.command.PostCommandProcessor;

/** A {@link PostCommandProcessor} implementation that does nothing. */
public class NullPostProcessor
        implements PostCommandProcessor {

    @Override
    public void completed(final CommandStart start) {
        // Do nothing
    }
}
