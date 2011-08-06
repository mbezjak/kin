package hr.helix.kin

import hr.helix.kin.model.BuildModel

import org.codehaus.groovy.control.CompilerConfiguration

/**
 * @author Miro Bezjak
 * @since 0.1
 */
class ScriptRunner {

    BuildModel run(String dsl) {
        def script = createShell().parse(dsl)
        script.run()
        script._model
    }

    /**
     * Create and configure {@link GroovyShell} so that it can parse DSL script.
     */
    GroovyShell createShell() {
        def compiler = new CompilerConfiguration()
        compiler.scriptBaseClass = BuildModelScript.canonicalName

        new GroovyShell(this.class.classLoader, new Binding(), compiler)
    }

}
