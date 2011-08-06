# kin
jenkins/hudson job configuration generator

## Summary
Simplify configuration process for multiple projects by building jenkins/hudson
job configuration from a template.

## Rationale
Say you have 10 projects of similar type. Be that ant, maven, gradle, grails or
any other type projects. They usually have almost identical configuration: log
rotation, source control management, triggers, goals (ant build, maven install,
gradle build, grails war, ...), reports, publishers, etc.

Example, let one maven project be configured as follows:

 * keep last 10 builds
 * use git as SCM
 * poll SCM every 10 minutes
 * trigger build at midnight every day
 * default maven goal is `install`
 * use reporters: DRY, PMD, FindBugs, CheckStyle with configured thresholds
 * mail to `developers@acme.com` on build failure
 * use cobertura publisher
 * collect `target/*.jar` artifacts

Ideally all other maven projects should reuse this configuration while changing:

 * SCM URL
 * group id
 * artifact id

Manually synchronizing configuration across all projects is a waste of time. By
using `kin` you can create templates that are used across projects while
specifying only those properties that differ.

## Example

    # show help
    $ java -jar kin.jar --help

    # show version
    $ java -jar kin.jar --version

    # build job configurations
    $ java -jar kin.jar

## Install
There is no installation procedure. Simply download latest
[distribution](https://github.com/mbezjak/kin/downloads), extract zip, create
`build.kin` file and run `java -jar kin.jar`.

## Required dependencies
Just Java 6. Everything else is bundled in jar itself.

## Flexibility
Remember that `build.kin` is powered by very simple DSL. Underneath all that is
still [groovy](http://groovy.codehaus.org). Unlike `*.properties` or `*.ini`
files when writing `build.kin` you have whole programming language to work with.
Templates are processed by
[SimpleTemplateEngine](http://groovy.codehaus.org/api/groovy/text/SimpleTemplateEngine.html)
- so groovy again. This gives you a great deal of flexibility when creating
templates and building job configurations.

## Source code
Source code is available at github: https://github.com/mbezjak/kin

## License
Project uses MIT license. Check LICENSE file for more info.
