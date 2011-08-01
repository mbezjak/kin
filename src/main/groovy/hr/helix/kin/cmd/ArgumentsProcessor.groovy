package hr.helix.kin.cmd

/**
 * @author Miro Bezjak
 * @since 1.0
 */
class ArgumentsProcessor {

    private final TerminalOperations op

    ArgumentsProcessor(TerminalOperations op) {
        this.op = op
    }

    /**
     * Process <code>-h,--help,-v,--version<code> command line switches.
     * 
     * @param args command line arguments
     * @return given build file name
     */
    String process(String[] args) {
        printHelpAndExitIfNeeded args
        printVersionAndExitIfNeeded args

        firstArgument args
    }

    private void printHelpAndExitIfNeeded(args) {
        if (hasHelpSwitch(args) || args.size() > 1) {
            op.printHelpAndExit()
        }
    }

    private void printVersionAndExitIfNeeded(args) {
        if (hasVersionSwitch(args)) {
            op.printVersionAndExit()
        }
    }

    private boolean hasHelpSwitch(args) {
        firstArgument(args) in ['-h', '--help']
    }

    private boolean hasVersionSwitch(args) {
        firstArgument(args) in ['-v', '--version']
    }

    private String firstArgument(args) {
        args.size() == 1 ? args[0] : null
    }

}
