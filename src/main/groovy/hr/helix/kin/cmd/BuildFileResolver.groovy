package hr.helix.kin.cmd

/**
 * @author Miro Bezjak
 * @since 1.0
 */
class BuildFileResolver {

    static final String DEFAULT_BUILD_FILE = 'build.kin'

    private final TerminalOperations op

    BuildFileResolver(TerminalOperations op) {
        this.op = op
    }

    /**
     * Create {@link File} from build file name. Dumps necessary information
     * and <b><code>System.exit</code></b>s if no valid build file can be found. 
     */
    File resolve(String buildFileName) {
        buildFileFromArgument(buildFileName) ?: defaultBuildFile()
    }

    private File buildFileFromArgument(String name) {
        if (name) {
            buildFile(name) { op.printNoBuildFileAndExit name }
        }
    }

    private File defaultBuildFile() {
        buildFile(DEFAULT_BUILD_FILE) { op.printHelpAndExit() }
    }

    private File buildFile(String name, Closure whenNotFile) {
        def f = new File(name)
        f.isFile() ? f : whenNotFile(name)
    }

}
