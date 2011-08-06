package hr.helix.kin

import hr.helix.kin.model.BuildModel
import hr.helix.kin.model.Job

/**
 * Script allows writing DSL code.
 * 
 * @author Miro Bezjak
 * @since 0.1
 */
abstract class BuildModelScript extends Script {

    // prefixed with underscore to avoid name collision
    final _model = new BuildModel()

    def methodMissing(String name, args) {
        if (args.size() == 1 && args[0] instanceof Closure) {
            def job = new Job(name)
            job.with args[0]
            _model.add job
        } else {
            throw new MissingMethodException(name, delegate, args)
        }
    }

}
