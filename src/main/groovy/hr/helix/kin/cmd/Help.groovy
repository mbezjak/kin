package hr.helix.kin.cmd

/**
 * Provides version and help information.
 * 
 * @author Miro Bezjak
 * @since 1.0
 */
class Help {

    static final String VERSION  = '1.0'
    static final String DOC_LINK = 'https://github.com/mbezjak/kin'

    static String getHelpInfo() {
        """\
        kin - jenkins/hudson job configuration generator

        USAGE: kin [option] [build_file]

        -h, --help      Shows this help message
        -v, --version   Shows project version
        build_file      Specifies build file. Default value is 'build.kin'

        Example build.kin:

          maven {
              mavenVersion = '3.0.3'
              template = 'maven.tpl'
          }

          maven('foo') {
              scm = 'http://example.com/git/foo'
              groupId = 'com.example.foo'
              artifactId = 'foo'
          }

          maven('bar') {
              scm = 'http://example.com/git/bar'
              mavenVersion = '2.2.1'
              groupId = 'com.example.bar'
              artifactId = 'bar'
          }

          grails('quux') {
              scm = 'http://example.com/hg/quux'
              grailsVersion = '1.3.7'
              deploy = true
              template = 'grails.tpl'
          }

        License MIT: http://www.opensource.org/licenses/mit-license.php
        Documentation & source code: $DOC_LINK
        """.stripIndent()
    }

    static String getVersionInfo() {
        """\
        kin $VERSION
        License MIT: http://www.opensource.org/licenses/mit-license.php
        Documentation & source code: $DOC_LINK
        """.stripIndent()
    }

    static void printHelpAndExit() {
        System.err << helpInfo
        System.exit 2
    }

    static void printVersionAndExit() {
        System.err << versionInfo
        System.exit 1
    }

}
