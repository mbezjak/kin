package hr.helix.kin

import spock.lang.*

class BuildModelSpec extends Specification {

    def model = new BuildModel()

    def "add should add job to a model"() {
        given:
        def name = 'foo'
        def job = new Job(name)

        when:
        model.add job

        then:
        model.jobs[name] == job 
    }

    def "producers should return only config producing jobs"() {
        given:
        def j1 = new Job('foo')
        def j2 = new Job('bar')
        def j3 = new Job('baz')
        j3.producesConfig = false

        model.add j1
        model.add j2
        model.add j2

        expect:
        model.producers() == [j1, j2]
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
        }""".stripIndent()

        expect:
        model.toString() == expected
    }

}
