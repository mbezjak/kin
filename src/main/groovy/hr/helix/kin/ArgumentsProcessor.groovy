package hr.helix.kin

/**
 * @author Miro Bezjak
 * @since 0.1
 */
class ArgumentsProcessor {

    private final Terminal terminal

    ArgumentsProcessor(Terminal terminal) {
        this.terminal = terminal
    }

    /**
     * Handle invalid arguments and <code>-h,--help,-v,--version<code> command
     * line switches.
     * 
     * @param args command line arguments
     */
    void process(String[] args) {
        if (hasVersionSwitch(args)) {
            terminal.printVersionAndExit()
        } else if (hasHelpSwitch(args) || args.size() > 0) {
            terminal.printHelpAndExit()
        }
    }

    private boolean hasVersionSwitch(args) {
        firstArgument(args) in ['-v', '--version']
    }

    private boolean hasHelpSwitch(args) {
        firstArgument(args) in ['-h', '--help']
    }

    private String firstArgument(args) {
        args.size() == 1 ? args[0] : null
    }

}
