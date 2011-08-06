package hr.helix.kin

def arguments = new CommandLineArguments(args)
if (arguments.hasVersionSwitch()) {
    System.err.println Help.versionInfo
    System.exit 1
} else if (arguments.hasHelpSwitch() || arguments.invalid) {
    System.err.println Help.helpInfo
    System.exit 2
}


def io = new IO()
def runner = new hr.helix.kin.script.Runner()
def engine = new groovy.text.SimpleTemplateEngine()

def dsl = io.buildFileText
if (dsl == null) {
    System.err.println "${IO.DEFAULT_BUILD_FILE} doesn't exist or is not a file!"
    System.exit 3
}


def build = runner.run(dsl)
build.producers().each { job ->
    def name = job.name
    def templates = build.templates(name)
    def template = io.findValidTemplate(templates)
    if (!template) {
        System.err.println "No template $templates for job '$name'!"
        System.exit 4
    }

    def traits = build.traits(name)
    def config = engine.createTemplate(template).make(traits)

    def configFile = io.configFile(name)
    io.writeConfig config, configFile
    println configFile.absolutePath
}
