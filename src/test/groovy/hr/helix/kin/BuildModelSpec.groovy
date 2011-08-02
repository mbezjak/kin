package hr.helix.kin

import spock.lang.*

class BuildModelSpec extends Specification {

    def model = new BuildModel()
    def foo = new Job('foo')
    def bar = new Job('bar')
    def qux = new Job('qux')

    def "add should add job to a model"() {
        when:
        model.add foo

        then:
        model.jobs[foo.name] == foo 
    }

    def "producers should return only config producing jobs"() {
        given:
        qux.producesConfig = false
        model.add foo
        model.add bar
        model.add qux

        expect:
        model.producers() == [foo, bar]
    }

    def "parents should return all parents of specified job"() {
        given:
        qux.inherit 'foo', 'notexists'

        model.add foo
        model.add bar
        model.add qux

        expect:
        model.parents(qux.name) == [foo]
    }

    def "templates should return all possible templates for specified job"() {
        given:
        bar.template = 'bar-simple.tpl'
        qux.template = 'qux-maven.tpl'
        qux.inherit 'bar', 'foo'

        model.add foo
        model.add bar
        model.add qux

        expect:
        model.templates(foo.name) == ['foo.tpl']
        model.templates(bar.name) == ['bar-simple.tpl', 'bar.tpl']
        model.templates(qux.name) == ['qux-maven.tpl', 'bar-simple.tpl', 'qux.tpl', 'bar.tpl', 'foo.tpl']
    }

    def "traits should merge traits of inherited jobs"() {
        given:
        foo.a = 1
        bar.b = 2
        qux.c = 3
        qux.a = 5
        qux.inherit 'bar', 'foo'

        model.add foo
        model.add bar
        model.add qux

        expect:
        model.traits(foo.name) == [jobName: 'foo', a:1]
        model.traits(bar.name) == [jobName: 'bar', b:2]
        model.traits(qux.name) == [jobName: 'qux', c:3, a:5, b:2]
    }

    def "toString should generate valid dsl code"() {
        given:
        model.add foo
        model.add bar

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
