package hr.helix.kin.cmd

/**
 * @author Miro Bezjak
 * @since 0.1
 */
class IOOperations {

    static final String DEFAULT_BUILD_FILE = 'build.kin'
    static final String DEFAULT_ENCODING   = 'utf-8'

    private final TerminalOperations terminal

    IOOperations(TerminalOperations terminal) {
        this.terminal = terminal
    }

    String getBuildFileText() {
        def build = new File(DEFAULT_BUILD_FILE)

        if (build.isFile()) {
            build.getText(DEFAULT_ENCODING)
        } else {
            terminal.printNoBuildFileAndExit DEFAULT_BUILD_FILE
        }
    }

}
