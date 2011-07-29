package hr.helix.kin.cmd

import static hr.helix.kin.cmd.Help.printHelpAndExit
import static hr.helix.kin.cmd.Help.printVersionAndExit

/**
 * Parses arguments and returns build file.
 * 
 * @author Miro Bezjak
 * @since 1.0
 */
class ArgumentsProcessor {

    static final String DEFAULT_BUILD_FILE = 'build.kin'

    static File process(String[] args) {
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
                System.err.println "'$name' doesn't exist or is not a file"
                System.exit 3
            }
        }
    }

    /**
     * Returns {@link File} if it exists as a file under given name.
     */
    private static File buildFile(String name, Closure onNotFile) {
        def f = new File(name)
        f.isFile() ? f : onNotFile(name)
    }

}
