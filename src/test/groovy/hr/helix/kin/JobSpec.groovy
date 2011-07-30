package hr.helix.kin

import spock.lang.*

class JobSpec extends Specification {

    def job = new Job('simple')

    def "constructor should throw exception if name is falsy"() {
        when:
        new Job('')

        then:
        thrown(IllegalArgumentException)
    }

    def "should allow single inheritance"() {
        when:
        job.with {
            inherit 'foo'
        }

        then:
        job.inheritFromParents == ['foo']
    }

    def "should allow multiple inheritance by multiple inherit statements"() {
        when:
        job.with {
            inherit 'foo'
            inherit 'bar'
        }

        then:
        job.inheritFromParents == ['foo', 'bar']
    }

    def "should allow multiple inheritance by specifying array"() {
        when:
        job.with {
            inherit 'foo', 'bar'
        }

        then:
        job.inheritFromParents == ['foo', 'bar']
    }

    def "should allow multiple inheritance by specifying list"() {
        when:
        job.with {
            inherit(['foo', 'bar'])
        }

        then:
        job.inheritFromParents == ['foo', 'bar']
    }

    def "should allow setting traits"() {
        given:
        def (v1, v2) = ['value', 'another value']

        when:
        job.with {
            a = v1
            b = v2
        }

        then:
        job.a == v1
        job.b == v2
    }

}