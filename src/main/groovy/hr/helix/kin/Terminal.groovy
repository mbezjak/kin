package hr.helix.kin

/**
 * Provides methods related to terminal (printing and exiting).
 * 
 * @author Miro Bezjak
 * @since 0.1
 */
class Terminal {

    static final int EXIT_CODE_HELP    = 1
    static final int EXIT_CODE_VERSION = 2
    static final int EXIT_CODE_NO_BUILD_FILE = 3

    void printHelpAndExit() {
        error Help.helpInfo, EXIT_CODE_HELP
    }

    void printVersionAndExit() {
        error Help.versionInfo, EXIT_CODE_VERSION
    }

    /**
     * @param name build file name
     */
    void printNoBuildFileAndExit(String name) {
        error "$name doesn't exist or is not a file!", EXIT_CODE_NO_BUILD_FILE
    }

    void error(String message, int exitCode) {
        System.err.println message
        System.exit exitCode
    }

}
