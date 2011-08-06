package hr.helix.kin

import hr.helix.kin.model.Build

import org.codehaus.groovy.control.CompilerConfiguration

/**
 * @author Miro Bezjak
 * @since 0.1
 */
class ScriptRunner {

    Build run(String dsl) {
        def script = createShell().parse(dsl)
        script.run()
        script._build
    }

    /**
     * Create and configure {@link GroovyShell} so that it can parse DSL script.
     */
    GroovyShell createShell() {
        def compiler = new CompilerConfiguration()
        compiler.scriptBaseClass = BuildScript.canonicalName

        new GroovyShell(this.class.classLoader, new Binding(), compiler)
    }

}
