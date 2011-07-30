package hr.helix.kin

import spock.lang.*

class ShellFactorySpec extends Specification {

    def factory = new ShellFactory()

    def "create should return groovy shell that can parse DSL script"() {
        given:
        def shell = factory.create(this.class.classLoader)

        when:
        def script = shell.parse("""
        maven {
            producesConfig = false
            template = 'maven.tpl'
            mavenVersion = '3.0.3'
            groupId = 'com.example'
        }

        foo {
            inherit 'maven'
            scm = 'http://example.com/git/foo'
            artifactId = 'foo-all'
        }

        bar {
            inherit 'maven'
            scm = 'http://example.com/hg/bar'
            artifactId = 'bar-core'
        }
        """)
        script.run()

        then:
        def jobs = script._model.jobs
        jobs.size() == 3

        and:
        jobs.maven.producesConfig == false
        jobs.maven.template == 'maven.tpl'
        jobs.maven.mavenVersion == '3.0.3'
        jobs.maven.groupId == 'com.example'

        and:
        jobs.foo.inheritFromParents == ['maven']
        jobs.foo.scm == 'http://example.com/git/foo'
        jobs.foo.artifactId == 'foo-all'

        and:
        jobs.bar.inheritFromParents == ['maven']
        jobs.bar.scm == 'http://example.com/hg/bar'
        jobs.bar.artifactId == 'bar-core'
    }

}
