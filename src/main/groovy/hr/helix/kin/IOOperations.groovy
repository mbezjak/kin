package hr.helix.kin

/**
 * @author Miro Bezjak
 * @since 0.1
 */
class IOOperations {
    /*
     * NOTE: when changing DEFAULT_* constants change helpInfo in Help as well.
     */

    static final String DEFAULT_BUILD_FILE = 'build.kin'
    static final String DEFAULT_ENCODING   = 'utf-8'

    private final Terminal terminal

    IOOperations(Terminal terminal) {
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
