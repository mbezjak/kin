package hr.helix.kin

def terminal = new Terminal()
def arguments = new CommandLineArguments(args)
def io = new IO()
def runner = new hr.helix.kin.script.Runner()
def engine = new groovy.text.SimpleTemplateEngine()

if (arguments.hasVersionSwitch()) {
    terminal.printVersionAndExit()
} else if (arguments.hasHelpSwitch() || arguments.invalid) {
    terminal.printHelpAndExit()
}

def dsl = io.buildFileText
if (dsl == null) {
    terminal.printNoBuildFileAndExit IO.DEFAULT_BUILD_FILE
}

def build = runner.run(dsl)
build.producers().each { job ->
    def name = job.name
    def templates = build.templates(name)
    def template = io.findValidTemplate(templates)
    if (!template) {
        terminal.printNoTemplateAndExit(name, templates)
        // System.exited!
    }

    def traits = build.traits(name)
    def config = engine.createTemplate(template).make(traits)

    def configFile = io.configFile(name)
    io.writeConfig config, configFile
    terminal.printJobCreated configFile.absolutePath
}
