package hr.helix.kin

def terminal = new Terminal()
def arguments = new CommandLineArguments(args)
def io = new IO()
def runner = new hr.helix.kin.script.Runner()

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

def file = {
    new File(it).isFile() ? new File(it) : null
}

def engine = new groovy.text.SimpleTemplateEngine()
build.producers().each { job ->
    println build.templates(job.name)
    def template = build.templates(job.name).findResult file
    println "template = $template"
    def traits = build.traits(job.name)
    println "traits = $traits"

    def config = engine.createTemplate(template).make(traits)
    def writer = new File("${job.name}.xml").newWriter('utf-8')
    config.writeTo writer
    writer.close()
}
