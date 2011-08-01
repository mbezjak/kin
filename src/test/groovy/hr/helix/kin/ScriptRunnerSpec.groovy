package hr.helix.kin

import spock.lang.*

class ScriptRunnerSpec extends Specification {

    def runner = new ScriptRunner()

    def "run should execute DSL script and return build model"() {
        when:
        def model = runner.run("""
        maven {
            producesConfig = false
            template = 'maven.tpl'
            mavenVersion = '3.0.3'
        }

        foo {
            inherit 'maven'
            scm = 'http://example.com/git/foo'
            groupId = 'com.example.foo'
            artifactId = 'foo-all'
        }

        bar {
            inherit 'maven'
            scm = 'http://example.com/git/bar'
            mavenVersion = '2.2.1' // overrides parent
            groupId = 'com.example.bar'
            artifactId = 'bar-core'
        }

        quux {
            template = 'grails.tpl'
            scm = 'http://example.com/hg/quux'
            grailsVersion = '1.3.7'
            deploy = true
        }
        """)

        then:
        def jobs = model.jobs
        jobs.size() == 4

        and:
        jobs.maven.producesConfig == false
        jobs.maven.template == 'maven.tpl'
        jobs.maven.mavenVersion == '3.0.3'

        and:
        jobs.foo.inheritFromParents == ['maven']
        jobs.foo.scm == 'http://example.com/git/foo'
        jobs.foo.groupId == 'com.example.foo'
        jobs.foo.artifactId == 'foo-all'

        and:
        jobs.bar.inheritFromParents == ['maven']
        jobs.bar.scm == 'http://example.com/git/bar'
        jobs.bar.mavenVersion == '2.2.1'
        jobs.bar.groupId == 'com.example.bar'
        jobs.bar.artifactId == 'bar-core'

        and:
        jobs.quux.template == 'grails.tpl'
        jobs.quux.scm == 'http://example.com/hg/quux'
        jobs.quux.grailsVersion == '1.3.7'
        jobs.quux.deploy == true
    }

}
