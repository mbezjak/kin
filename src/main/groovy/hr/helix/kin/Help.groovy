package hr.helix.kin

/**
 * Provides help and version information.
 * 
 * @author Miro Bezjak
 * @since 0.1
 */
class Help {

    static final String VERSION  = '0.1'
    static final String DOC_LINK = 'https://github.com/mbezjak/kin'

    static String getHelpInfo() {
        """\
        kin - jenkins/hudson job configuration generator

        USAGE: java -jar kin.jar [option]

        -h, --help      Shows this help message
        -v, --version   Shows project version

        Build file is always searched in current working directory under name
        'build.kin'. Output is written to 'build' directory in current working
        directory. All files are read and written in 'UTF-8' character encoding.

        RUN EXAMPLE:

          \$ ls
          build.kin kin.jar maven.tpl
          \$ java -jar kin.jar
          \$ ls
          build build.kin kin.jar maven.tpl

        EXAMPLE BUILD FILE (build.kin):

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

        License MIT: http://www.opensource.org/licenses/mit-license.php
        Documentation & source code: $DOC_LINK
        """.stripIndent().trim()
    }

    static String getVersionInfo() {
        """\
        kin $VERSION
        License MIT: http://www.opensource.org/licenses/mit-license.php
        Documentation & source code: $DOC_LINK
        """.stripIndent().trim()
    }

}
