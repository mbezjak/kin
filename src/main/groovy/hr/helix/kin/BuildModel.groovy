package hr.helix.kin

/**
 * Contains all jobs defined in build file.
 * 
 * @author Miro Bezjak
 * @since 1.0
 */
class BuildModel {

    final Map<String, Job> jobs = [:]

    void add(Job job) {
        jobs[job.name] = job
    }

    String toString() {
        jobs.collect { name, job ->
            job as String
        }.join('\n\n')
    }

}
