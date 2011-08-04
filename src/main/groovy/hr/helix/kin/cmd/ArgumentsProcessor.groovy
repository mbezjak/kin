package hr.helix.kin.cmd

/**
 * @author Miro Bezjak
 * @since 0.1
 */
class ArgumentsProcessor {

    private final TerminalOperations op

    ArgumentsProcessor(TerminalOperations op) {
        this.op = op
    }

    /**
     * Handle invalid arguments and <code>-h,--help,-v,--version<code> command
     * line switches.
     * 
     * @param args command line arguments
     */
    void process(String[] args) {
        if (hasVersionSwitch(args)) {
            op.printVersionAndExit()
        } else if (hasHelpSwitch(args) || args.size() > 0) {
            op.printHelpAndExit()
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
