package hr.helix.kin

import spock.lang.*

class IOSpec extends Specification {

    def io = new IO()

    def "getBuildFileText should return null when build file doesnt exist"() {
        expect:
        io.buildFileText == null
    }

    def "getBuildFileText should return build file contents"() {
        given:
        def contents = 'foo, bar and quux'
        def build = new File(IO.DEFAULT_BUILD_FILE)
        build.text = contents

        expect:
        io.buildFileText == contents

        cleanup:
        build.delete()
    }

}
