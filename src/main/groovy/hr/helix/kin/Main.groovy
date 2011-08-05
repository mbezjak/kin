package hr.helix.kin

def terminal = new Terminal()
def arguments = new CommandLineArguments(args)

if (arguments.hasVersionSwitch()) {
    terminal.printVersionAndExit()
} else if (arguments.hasHelpSwitch() || arguments.invalid) {
    terminal.printHelpAndExit()
}

def dsl = new IOOperations(terminal).buildFileText
def model = new ScriptRunner().run(dsl)
println model

def file = {
    new File(it).isFile() ? new File(it) : null
}

def engine = new groovy.text.SimpleTemplateEngine()
model.producers().each { job ->
    println model.templates(job.name)
    def template = model.templates(job.name).findResult file
    println "template = $template"
    def traits = model.traits(job.name)
    println "traits = $traits"

    def config = engine.createTemplate(template).make(traits)
    def writer = new File("${job.name}.xml").newWriter('utf-8')
    config.writeTo writer
    writer.close()
}
