package hr.helix.kin.cmd

import spock.lang.*

class ArgumentsProcessorSpec extends Specification {

    def op = Mock(TerminalOperations)
    def processor = new ArgumentsProcessor(op)

    def "process should return null on empty arguments"() {
        expect:
        processor.process() == null
    }

    @Unroll({ "process should print help and exit on $help switch" })
    def "process should print help and exit on help switch"() {
        when:
        processor.process help

        then:
        1 * op.printHelpAndExit()

        where:
        help << ['-h', '--help']
    }

    @Unroll({ "process should print version and exit on $version switch" })
    def "process should print version and exit on version switch"() {
        when:
        processor.process version

        then:
        1 * op.printVersionAndExit()

        where:
        version << ['-v', '--version']
    }

    def "process should return given build file name"() {
        given:
        def name = 'simple.kin'

        expect:
        processor.process(name) == name
    }

}
