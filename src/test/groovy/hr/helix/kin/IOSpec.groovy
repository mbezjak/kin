package hr.helix.kin

import spock.lang.*

class IOSpec extends Specification {

    def io = new IO()

    def "getBuildFileText should return null when build file doesnt exist"() {
        expect:
        io.buildFileText == null
    }

    def "getBuildFileText should return build file contents"() {
        given:
        def contents = 'foo, bar and quux'
        def build = new File(IO.DEFAULT_BUILD_FILE)
        build.text = contents

        expect:
        io.buildFileText == contents

        cleanup:
        build.delete()
    }

    def "findValidTemplate should return null no valid template can be found"() {
        expect:
        io.findValidTemplate(['foo.tpl', 'bar.tpl', 'qux.tpl']) == null
    }

    def "findValidTemplate should return first template file that exists on a file system"() {
        given:
        def bar = new File('bar.tpl')
        bar.mkdir()

        def qux = new File('qux.tpl')
        qux.createNewFile()

        expect:
        io.findValidTemplate(['foo.tpl', 'bar.tpl', 'qux.tpl']) == qux

        cleanup:
        bar.delete()
        qux.delete()
    }

    def "mkConfigDir should create directory where config file will be written"() {
        given:
        def name = 'foo'

        when:
        io.mkConfigDir(name)

        then:
        new File(IO.DEFAULT_BUILD_DIR, name).isDirectory()

        cleanup:
        new File(IO.DEFAULT_BUILD_DIR, name).deleteDir()
    }

    def "writeConfig should write config file to file system"() {
        given:
        def name = 'foo'
        def text = 'simple text'
        def writable = new Writable() {
            Writer writeTo(Writer out) {
                out.write text
            }
        }

        when:
        io.writeConfig writable, name

        then:
        def dir = new File("${IO.DEFAULT_BUILD_DIR}/$name")
        new File(dir, IO.DEFAULT_CONFIG_FILE).text == text

        cleanup:
        new File(IO.DEFAULT_BUILD_DIR, name).deleteDir()
    }

}
