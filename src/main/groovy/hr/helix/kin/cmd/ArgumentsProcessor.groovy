package hr.helix.kin.cmd

/**
 * @author Miro Bezjak
 * @since 1.0
 */
class ArgumentsProcessor {

    static final String DEFAULT_BUILD_FILE = 'build.kin'

    private final CommandLineOperations op
    private final String[] args

    ArgumentsProcessor(CommandLineOperations op, String[] args) {
        this.op = op
        this.args = args
    }

    /**
     * Process arguments and return build file.
     */
    File process() {
        printHelpAndExitIfNeeded()
        printVersionAndExitIfNeeded()

        if (hasNoArguments()) {
            return buildFile(DEFAULT_BUILD_FILE) {
                op.printHelpAndExit()
            }
        } else {
            return buildFile(args[0]) { name ->
                op.printNoBuildFileAndExit name
            }
        }
    }

    private void printHelpAndExitIfNeeded() {
        if (hasHelpSwitch() || args.size() > 1) {
            op.printHelpAndExit()
        }
    }

    private void printVersionAndExitIfNeeded() {
        if (hasVersionSwitch()) {
            op.printVersionAndExit()
        }
    }

    private boolean hasNoArguments() {
        args.size() == 0
    }

    private boolean hasHelpSwitch() {
        args.size() == 1 && args[0] in ['-h', '--help']
    }

    private boolean hasVersionSwitch() {
        args.size() == 1 && args[0] in ['-v', '--version']
    }

    /**
     * Returns {@link File} if it exists and is a file
     */
    private File buildFile(String name, Closure onNotFile) {
        def f = new File(name)
        f.isFile() ? f : onNotFile(name)
    }

}
