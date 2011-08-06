package hr.helix.kin

/**
 * Provides IO related methods (reading, writing, making directories).
 * 
 * @author Miro Bezjak
 * @since 0.1
 */
class IO {
    /*
     * NOTE: when changing DEFAULT_* constants change helpInfo in Help as well.
     */

    static final String DEFAULT_BUILD_FILE = 'build.kin'
    static final String DEFAULT_ENCODING   = 'utf-8'

    /**
     * @return contents of build file
     */
    String getBuildFileText() {
        def build = new File(DEFAULT_BUILD_FILE)
        build.isFile() ? build.getText(DEFAULT_ENCODING) : null
    }

    /**
     * @param templateNames names of potential templates
     * @return first template file that exists on a file system
     */
    File findValidTemplate(List<String> templateNames) {
        templateNames.findResult { name ->
            def template = new File(name)
            template.isFile() ? template : null
        }
    }

}
