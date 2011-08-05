package hr.helix.kin

import spock.lang.*

class CommandLineArgumentsSpec extends Specification {

    @Unroll({ "arguments $args should result in hasHelpSwitch = $hasHelpSwitch" })
    def "hasHelpSwitch return true if arguments has help switch"() {
        given:
        def arguments = new CommandLineArguments(args)

        expect:
        arguments.hasHelpSwitch() == hasHelpSwitch

        where:
        args                   | hasHelpSwitch
        ['-h']as String[]     | true
        ['--help']as String[] | true
        ['foo']as String[]    | false
    }

    @Unroll({ "arguments $args should result in hasVersionSwitch = $hasVersionSwitch" })
    def "hasVersionSwitch return true if arguments has version switch"() {
        given:
        def arguments = new CommandLineArguments(args)

        expect:
        arguments.hasVersionSwitch() == hasVersionSwitch

        where:
        args                      | hasVersionSwitch
        ['-v']as String[]        | true
        ['--version']as String[] | true
        ['foo']as String[]       | false
    }

    def "isInvalid should return true on invalid arguments"() {
        given:
        def arguments = new CommandLineArguments(['foo'] as String[])

        expect:
        arguments.invalid
    }

}
