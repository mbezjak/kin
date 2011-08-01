package hr.helix.kin.cmd

import spock.lang.*

class BuildFileResolverSpec extends Specification {

    def op = Mock(TerminalOperations)
    def resolver = new BuildFileResolver(op)

    def "resolve should print help and exit when build file cant be resolved"() {
        when:
        resolver.resolve null

        then:
        1 * op.printHelpAndExit()
    }

    def "resolve should return default build file if non is given"() {
        given:
        def file = new File(BuildFileResolver.DEFAULT_BUILD_FILE)
        file.createNewFile()

        expect:
        resolver.resolve(null).name == BuildFileResolver.DEFAULT_BUILD_FILE

        cleanup:
        file.delete()
    }

    def "resolve should print error message and exit when given build file name cant be resolved"() {
        given:
        def name = 'simple.kin'

        when:
        resolver.resolve name

        then:
        1 * op.printNoBuildFileAndExit(name)
    }

    def "resolve should return build file from given name"() {
        given:
        def buildFileName = 'simple.kin'
        def file = new File(buildFileName)
        file.createNewFile()

        expect:
        resolver.resolve(buildFileName).name == buildFileName

        cleanup:
        file.delete()
    }

}
