package hr.helix.kin.script

import hr.helix.kin.model.Build
import hr.helix.kin.model.Job

/**
 * Script allows writing DSL code.
 * 
 * @author Miro Bezjak
 * @since 0.1
 */
abstract class BuildScript extends Script {

    // prefixed with underscore to avoid name collision
    final _build = new Build()

    def methodMissing(String name, args) {
        if (args.size() == 1 && args[0] instanceof Closure) {
            def job = new Job(name)
            job.with args[0]
            _build.add job
        } else {
            throw new MissingMethodException(name, delegate, args)
        }
    }

}
