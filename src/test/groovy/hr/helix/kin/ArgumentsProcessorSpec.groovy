package hr.helix.kin

import hr.helix.kin.ArgumentsProcessor;
import hr.helix.kin.Terminal;
import spock.lang.*

class ArgumentsProcessorSpec extends Specification {

    def terminal = Mock(Terminal)
    def processor = new ArgumentsProcessor(terminal)

    @Unroll({ "process should print version and exit on $version switch" })
    def "process should print version and exit on version switch"() {
        when:
        processor.process version

        then:
        1 * terminal.printVersionAndExit()

        where:
        version << ['-v', '--version']
    }

    @Unroll({ "process should print help and exit on $help switch" })
    def "process should print help and exit on help switch"() {
        when:
        processor.process help

        then:
        1 * terminal.printHelpAndExit()

        where:
        help << ['-h', '--help']
    }

    def "process should print help and exit on invalid arguments"() {
        when:
        processor.process 'foo'

        then:
        1 * terminal.printHelpAndExit()
    }

}
