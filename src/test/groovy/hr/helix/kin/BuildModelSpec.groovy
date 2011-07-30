package hr.helix.kin

import spock.lang.*

class BuildModelSpec extends Specification {

    def model = new BuildModel()

    def "add should add job to model"() {
        given:
        def name = 'foo'
        def job = new Job(name)

        when:
        model.add job

        then:
        model.jobs[name] == job 
    }

    def "toString should generate valid dsl code"() {
        given:
        model.add(new Job('foo'))
        model.add(new Job('bar'))

        def expected = """\
        foo {
            inherit([])
            producesConfig = true
            template = null
        }

        bar {
            inherit([])
            producesConfig = true
            template = null
        }
        """.stripIndent()

        expect:
        model.toString() == expected
    }

}
