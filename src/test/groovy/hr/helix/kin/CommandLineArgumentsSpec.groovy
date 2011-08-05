package hr.helix.kin

import spock.lang.*

class CommandLineArgumentsSpec extends Specification {

    @Unroll({ "hasHelpSwitch should return true for argument $arg" })
    def "hasHelpSwitch should return true if first argument is help switch"() {
        given:
        def arguments = new CommandLineArguments([arg]as String[])

        expect:
        arguments.hasHelpSwitch()

        where:
        arg << ['-h', '--help']
    }

    @Unroll({ "hasVersionSwitch should retrun true for argument $arg" })
    def "hasVersionSwitch should return true if first argument is version"() {
        given:
        def arguments = new CommandLineArguments([arg]as String[])

        expect:
        arguments.hasVersionSwitch()

        where:
        arg << ['-v', '--version']
    }

    @Unroll({ "isInvalid should return $invalid on $args" })
    def "isInvalid should return true on invalid arguments"() {
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
