package hr.helix.kin.model

import spock.lang.*

class BuildSpec extends Specification {

    def build = new Build()
    def foo = new Job('foo')
    def bar = new Job('bar')
    def qux = new Job('qux')

    def "add should add job to build"() {
        when:
        build.add foo

        then:
        build.jobs == [(foo.name): foo] 
    }

    def "producers should return only config producing jobs"() {
        given:
        qux.producesConfig = false
        build.add foo
        build.add bar
        build.add qux

        expect:
        build.producers() == [foo, bar]
    }

    def "parents should return all parents of specified job"() {
        given:
        qux.inherit 'foo', 'notexists'

        build.add foo
        build.add bar
        build.add qux

        expect:
        build.parents(qux.name) == [foo]
    }

    def "templates should return all possible templates for specified job"() {
        given:
        bar.template = 'bar-simple.tpl'
        qux.template = 'qux-maven.tpl'
        qux.inherit 'bar', 'foo'

        build.add foo
        build.add bar
        build.add qux

        expect:
        build.templates(foo.name) == ['foo.tpl']
        build.templates(bar.name) == ['bar-simple.tpl', 'bar.tpl']
        build.templates(qux.name) == ['qux-maven.tpl', 'bar-simple.tpl', 'qux.tpl', 'bar.tpl', 'foo.tpl']
    }

    def "traits should merge traits of inherited jobs"() {
        given:
        foo.a = 1
        bar.b = 2
        qux.c = 3
        qux.a = 5
        qux.inherit 'bar', 'foo'

        build.add foo
        build.add bar
        build.add qux

        expect:
        build.traits(foo.name) == [jobName: 'foo', a:1]
        build.traits(bar.name) == [jobName: 'bar', b:2]
        build.traits(qux.name) == [jobName: 'qux', c:3, a:5, b:2]
    }

    def "toString should generate valid dsl code"() {
        given:
        build.add foo
        build.add bar

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
        build.toString() == expected
    }

}
