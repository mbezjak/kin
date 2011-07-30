package hr.helix.kin

import spock.lang.*

class JobSpec extends Specification {

    def model = new Job('simple')

    def "constructor should throw exception if name is falsy"() {
        when:
        new Job('')

        then:
        thrown(IllegalArgumentException)
    }

    def "should allow single inheritance"() {
        when:
        model.with {
            inherit 'foo'
        }

        then:
        model.inheritFromParents == ['foo']
    }

    def "should allow multiple inheritance by multiple inherit statements"() {
        when:
        model.with {
            inherit 'foo'
            inherit 'bar'
        }

        then:
        model.inheritFromParents == ['foo', 'bar']
    }

    def "should allow multiple inheritance by specifying array"() {
        when:
        model.with {
            inherit 'foo', 'bar'
        }

        then:
        model.inheritFromParents == ['foo', 'bar']
    }

    def "should allow multiple inheritance by specifying list"() {
        when:
        model.with {
            inherit(['foo', 'bar'])
        }

        then:
        model.inheritFromParents == ['foo', 'bar']
    }

    def "should allow setting traits"() {
        given:
        def (v1, v2) = ['value', 'another value']

        when:
        model.with {
            a = v1
            b = v2
        }

        then:
        model.a == v1
        model.b == v2
    }

}
