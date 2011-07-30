package hr.helix.kin

import groovy.transform.EqualsAndHashCode

/**
 * Model for job parameters.
 * 
 * @author Miro Bezjak
 * @since 1.0
 */
@EqualsAndHashCode
class Job {

    boolean producesConfig = true
    final List<String> inheritFromParents = []
    final Map<String, Object> traits      = [:]

    void inherit(String parent) {
        inherit([parent])
    }

    void inherit(String[] parents) {
        inherit(parents as List)
    }

    void inherit(List<String> parents) {
        inheritFromParents.addAll parents
    }

    def propertyMissing(String name, value) {
        traits[name] = value
    }

    def propertyMissing(String name) {
        traits[name]
    }

}
