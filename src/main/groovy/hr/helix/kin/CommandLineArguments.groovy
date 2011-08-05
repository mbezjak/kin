package hr.helix.kin

/**
 * Provides information on command line switches used or if given arguments are
 * invalid.
 * 
 * @author Miro Bezjak
 * @since 0.1
 */
class CommandLineArguments {

    private final String[] args

    CommandLineArguments(String[] args) {
        this.args = args
    }

    boolean hasHelpSwitch() {
        firstArgument() in ['-h', '--help']
    }

    boolean hasVersionSwitch() {
        firstArgument() in ['-v', '--version']
    }

    boolean isInvalid() {
        !hasHelpSwitch() && !hasVersionSwitch() && args.size() > 0
    }

    private String firstArgument() {
        args.size() == 1 ? args[0] : null
    }

}
