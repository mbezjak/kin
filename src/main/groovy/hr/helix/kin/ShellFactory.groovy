package hr.helix.kin

import org.codehaus.groovy.control.CompilerConfiguration

/**
 * Creates and configures {@link GroovyShell} so that it can parse DSL script.
 * 
 * @author Miro Bezjak
 * @since 1.0
 */
class ShellFactory {

    GroovyShell create(ClassLoader classLoader) {
        new GroovyShell(classLoader, new Binding(), createCompiler())
    }

    private CompilerConfiguration createCompiler() {
        def compiler = new CompilerConfiguration()
        compiler.scriptBaseClass = BuildModelScript.canonicalName
        compiler
    }

}
