package hr.helix.kin.cmd

import spock.lang.*

class IOOperationsSpec extends Specification {

    def terminal = Mock(Terminal)
    def op = new IOOperations(terminal)

    def "getBuildFileText should report and exit on no build file"() {
        when:
        op.buildFileText

        then:
        1 * terminal.printNoBuildFileAndExit(_)
    }

    def "getBuildFileText should return build file text"() {
        given:
        def contents = 'foo, bar and quux'
        def buildFile = new File(IOOperations.DEFAULT_BUILD_FILE)
        buildFile.text = contents

        expect:
        op.buildFileText == contents

        cleanup:
        buildFile.delete()
    }

}
