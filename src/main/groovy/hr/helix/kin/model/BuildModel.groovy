package hr.helix.kin.model

/**
 * Contains all jobs defined in build file.
 * 
 * @author Miro Bezjak
 * @since 0.1
 */
class BuildModel {

    final Map<String, Job> jobs = [:]

    void add(Job job) {
        jobs[job.name] = job
    }

    /**
     * @return only config producing jobs
     */
    List<Job> producers() {
        jobs.findResults { name, job ->
            job.producesConfig ? job : null
        }
    }

    /**
     * @return inherited (parent) jobs for specified job
     */
    List<Job> parents(String jobName) {
        jobs[jobName].inheritFromParents.findResults { parent ->
            jobs[parent]
        }
    }

    /**
     * @return all possible template names for specified job
     */
    List<String> templates(String jobName) {
        def job = jobs[jobName]
        def parents = parents(jobName)

        def possible = []
        possible << job.template
        possible += parents*.template
        possible << "${jobName}.tpl"
        possible += parents*.name.collect { "${it}.tpl" }

        possible.findAll { it }
    }

    /**
     * @return traits for specified job; includes inherited traits
     */
    Map<String, Object> traits(String jobName) {
        def traits = [jobName: jobName]
        parents(jobName).reverse().each { parent ->
            traits.putAll parent.traits
        }
        traits.putAll jobs[jobName].traits

        traits
    }

    String toString() {
        jobs.collect { name, job ->
            job as String
        }.join('\n\n')
    }

}
