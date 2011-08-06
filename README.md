Still unfinished!

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

## Example usage

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

## Build process
In order to simplify configuration process `kin` uses the fact that
jenkins/hudson store their job configuration in XML file. So when you create new
job named `foo` via web interface jenkins stores job configuration in
`$JENKINS_HOME/jobs/foo/config.xml` file. `$JENKINS_HOME` location can vary. For
jenkins installed by DEB package on Ubuntu it's `/var/lib/jenkins`. Running
jenkins standalone uses `$HOME/.jenkins`. More information on [jenkins wiki
page](https://wiki.jenkins-ci.org/display/JENKINS/Administering+Jenkins). The
same rule applies for hudson aswell.

To create jenkins/hudson job configuration via `kin` you need to write
`build.kin` file and create necessary templates. Usually one per project type
(ant, maven, gradle, grails, etc.) though that is completely up to you.

## Creating job templates
Easiest way to create a template is to use jenkins/hudson web interface. Simply
create a new job and configure it the way you'd like. Afterwards copy job
configuration into `*.tpl` file. For example, for maven project named `foo` copy
`$JENKINS_HOME/jobs/foo/config.xml` into `maven.tpl`. Now edit template by
replacing variable configuration with `$name` properties. It will make more
sense in example section.

## build.kin
Simple DSL is setup to support `build.kin` file. It looks like this:

    foo { // <- project name
        template = 'maven.tpl'
        mavenVersion = '3.0.3'
        scm = 'http://acme.com/git/foo'
        groupId = 'com.acme.foo'
        artifactId = 'foo'
    }

To share common properties across job configurations:

    maven {
        // doesn't produce jenkins/hudson job configuration but is only used
        // to share common configuration across jobs
        producesConfig = false
        template = 'maven.tpl' // this is the default
        mavenVersion = '3.0.3'
    }

    foo {
        inherit 'maven'
        scm = 'http://acme.com/git/foo'
        groupId = 'com.acme.foo'
        artifactId = 'foo'
    }

    bar {
        inherit 'maven'
        scm = 'http://acme.com/git/bar'
        groupId = 'com.acme.bar'
        artifactId = 'bar'
    }

Remember that groovy is underneath `build.kin` therefor we could write:

    maven {
        producesConfig = false
        mavenVersion = '3.0.3'
    }

    def mavenProject = { name ->
        return {
            inherit 'maven'
            scm = "http://acme.com/git/$name"
            groupId = "com.acme.$name"
            artifactId = name
        }
    }

    foo mavenProject('foo')
    bar mavenProject('bar')

Or you can use `addJob` method as part of API that
`hr.helix.kin.script.BuildScript` provides:

    maven {
        producesConfig = false
        mavenVersion = '3.0.3'
    }

    def mavenProject = { name ->
        addJob name, {
            inherit 'maven'
            scm = "http://acme.com/git/$name"
            groupId = "com.acme.$name"
            artifactId = name
        }
    }

    mavenProject 'foo'
    mavenProject 'bar'

## Example


## How to install created job configurations


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
