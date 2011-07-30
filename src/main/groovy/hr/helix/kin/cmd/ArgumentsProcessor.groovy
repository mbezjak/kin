package hr.helix.kin.cmd

/**
 * Parses arguments and returns build file.
 * 
 * @author Miro Bezjak
 * @since 1.0
 */
class ArgumentsProcessor {

    static final String DEFAULT_BUILD_FILE = 'build.kin'

    File process(String[] args) {
        if (args.size() == 0) {
            return buildFile(DEFAULT_BUILD_FILE) {
                printHelpAndExit()
            }
        } else if (args.size() > 1 || args[0] in ['-h', '--help']) {
            printHelpAndExit()
        } else if (args[0] in ['-v', '--version']) {
            printVersionAndExit()
        } else {
            return buildFile(args[0]) { name ->
                System.err.println "'$name' doesn't exist or is not a file!"
                System.exit 3
            }
        }
    }

    private void printHelpAndExit() {
        System.err.println Help.helpInfo
        System.exit 1
    }

    private void printVersionAndExit() {
        System.err.println Help.versionInfo
        System.exit 2
    }

    /**
     * Returns {@link File} if it exists as a file under given name.
     */
    private File buildFile(String name, Closure onNotFile) {
        def f = new File(name)
        f.isFile() ? f : onNotFile(name)
    }

}
