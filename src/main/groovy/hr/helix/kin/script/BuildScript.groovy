package hr.helix.kin.script

import hr.helix.kin.model.Build
import hr.helix.kin.model.Job

/**
 * Groovy script allows writing DSL code.
 * 
 * @author Miro Bezjak
 * @since 0.1
 */
abstract class BuildScript extends Script {

    // prefixed with underscore to avoid name collision
    final _build = new Build()

    def methodMissing(String name, args) {
        if (args.size() == 1 && args[0] instanceof Closure) {
            addJob name, args[0]
        } else {
            throw new MissingMethodException(name, delegate, args)
        }
    }

    Job addJob(String name, Closure configurer) {
        def job = new Job(name)
        job.with configurer
        _build.add job
        job
    }

}
