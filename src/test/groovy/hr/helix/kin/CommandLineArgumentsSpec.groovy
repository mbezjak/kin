package hr.helix.kin

import spock.lang.*

class CommandLineArgumentsSpec extends Specification {

    @Unroll
    def "hasHelpSwitch should return true for argument #arg"() {
        given:
        def arguments = new CommandLineArguments([arg]as String[])

        expect:
        arguments.hasHelpSwitch()

        where:
        arg << ['-h', '--help']
    }

    @Unroll
    def "hasVersionSwitch should return true for argument #arg"() {
        given:
        def arguments = new CommandLineArguments([arg]as String[])

        expect:
        arguments.hasVersionSwitch()

        where:
        arg << ['-v', '--version']
    }

    @Unroll
    def "isInvalid should return #invalid on #args"() {
        given:
        def arguments = new CommandLineArguments(args as String[])

        expect:
        arguments.invalid == invalid

        where:
        args          | invalid
        ['-h']        | false
        ['--help']    | false
        ['-v']        | false
        ['--version'] | false
        ['foo']       | true
        ['-h', 'foo'] | true
        ['-v', 'bar'] | true
    }

}
