# kin
jenkins/hudson job configuration generator

[![Build Status](https://secure.travis-ci.org/mbezjak/kin.png?branch=master)](http://travis-ci.org/mbezjak/kin)

## Summary
Simplify configuration process for multiple projects by building jenkins/hudson
job configuration from a template.

## Rationale
Say you have 10 projects of similar type. Be that ant, maven, gradle, grails or
projects of any other type. They usually have almost identical configuration:
log rotation, source control management, triggers, goals (ant build, maven
install, gradle build, grails war, ...), reports, publishers, etc.

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

Ideally, all other maven projects should reuse this configuration while
changing:

 * SCM URL
 * group id
 * artifact id

Manually synchronizing configuration across all projects is a waste of
time. `kin` allows you to create templates that are used across projects while
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
`$JENKINS_HOME/jobs/foo/config.xml` file. `$JENKINS_HOME` location can vary.
Jenkins installed by DEB package on Ubuntu uses `/var/lib/jenkins`. Running
jenkins standalone uses `$HOME/.jenkins`. More information on [jenkins wiki
page](https://wiki.jenkins-ci.org/display/JENKINS/Administering+Jenkins). The
same rule applies for hudson.

To create jenkins/hudson job configuration via `kin` you need to write
`build.kin` file and create necessary templates. Usually one per project type
(ant, maven, gradle, grails, etc.) though that is completely up to you.

## Creating job templates
Easiest way to create a template is to use jenkins/hudson web interface. Create
a new job and configure it the way you'd like. Afterwards, copy created job
configuration into `*.tpl` file. For example, for maven project named `foo` copy
`$JENKINS_HOME/jobs/foo/config.xml` into `maven.tpl`. Now edit template by
replacing variable configuration properties with `$name`. It will make more
sense in walkthrough section.

## DSL exaplained (build.kin file)
Simple DSL is setup to support `build.kin` file. It looks like this:

```groovy
foo { // <- project name
    template = 'maven.tpl'
    mavenVersion = '3.0.3'
    scm = 'http://acme.com/git/foo'
    groupId = 'com.acme.foo'
    artifactId = 'foo'
}
```

To share common properties across job configurations:

```groovy
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
```

Underneath that DSL is groovy. Therefor we could write more groovy like code to
reduce number of lines further:

```groovy
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
```

Or you can use `addJob` method as part of API that
`hr.helix.kin.script.BuildScript` provides:

```groovy
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
```

## API available in build.kin file
You can step away from DSL if you want to. We've already seen `addJob` method
available in `hr.helix.kin.script.BuildScript`. This block:

```groovy
foo {
    a = 1
    b = 2
}
```

is equivalent to:

```groovy
addJob 'foo', {
    a = 1
    b = 2
}
```

or:

```groovy
addJob('foo') {
    a = 1
    b = 2
}
```

`hr.helix.kin.model.Job` class provides `inherit` method. It can be used to
specified job name from which to inherit parameters. In following example `foo`
job inherits both `mavenVersion` and `groupId` properties.

```groovy
maven {
    producesConfig = false
    mavenVersion = '3.0.3'
    groupId = 'com.acme'
}

foo {
    inherit 'maven'
    artifactId = 'foo-all'
}
```

You can also inherit from multiple jobs. Example:

```groovy
// omitted maven and git configurations

foo {
    inherit 'maven', 'git'
}
```

More API information can be found by looking at API documentation and source
code (it's really small) available at `docs/api` and `src` directories
respectively. Following classes are directly responsible for `build.kin` file:

 * `hr.helix.kin.script.BuildScript`
 * `hr.helix.kin.model.Job`
 * `hr.helix.kin.model.Build`

## Walkthrough
Suppose a `build.kin` file:

```groovy
gradle {
    producesConfig = false
    gradleVersion = '1.0-milestone-4'
    task = 'build'
}

kin {
    inherit 'gradle'
    scm = 'https://github.com/mbezjak/kin'
}

foo {
    inherit 'gradle'
    scm = 'http://acme.com/git/foo'
    task = 'jar'
}

bar {
    inherit 'gradle'
    gradleVersion = '0.9.2'
    scm = 'http://acme.com/git/bar'
}
```

`gradle.tpl` looks like:

```xml
<?xml version='1.0' encoding='UTF-8'?>
<project>
  <actions/>
  <description></description>
  <keepDependencies>false</keepDependencies>
  <properties/>
  <scm class="hudson.plugins.git.GitSCM">
    <configVersion>2</configVersion>
    <userRemoteConfigs>
      <hudson.plugins.git.UserRemoteConfig>
        <name></name>
        <refspec></refspec>
        <url>$scm</url>
      </hudson.plugins.git.UserRemoteConfig>
    </userRemoteConfigs>
    <branches>
      <hudson.plugins.git.BranchSpec>
        <name>**</name>
      </hudson.plugins.git.BranchSpec>
    </branches>
    <recursiveSubmodules>false</recursiveSubmodules>
    <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
    <authorOrCommitter>false</authorOrCommitter>
    <clean>false</clean>
    <wipeOutWorkspace>false</wipeOutWorkspace>
    <pruneBranches>false</pruneBranches>
    <remotePoll>false</remotePoll>
    <buildChooser class="hudson.plugins.git.util.DefaultBuildChooser"/>
    <gitTool>Default</gitTool>
    <submoduleCfg class="list"/>
    <relativeTargetDir></relativeTargetDir>
    <excludedRegions></excludedRegions>
    <excludedUsers></excludedUsers>
    <gitConfigName></gitConfigName>
    <gitConfigEmail></gitConfigEmail>
    <skipTag>false</skipTag>
    <scmName></scmName>
  </scm>
  <canRoam>true</canRoam>
  <disabled>false</disabled>
  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
  <triggers class="vector">
    <hudson.triggers.TimerTrigger>
      <spec>*/10 * * * *</spec>
    </hudson.triggers.TimerTrigger>
  </triggers>
  <concurrentBuild>false</concurrentBuild>
  <builders>
    <hudson.plugins.gradle.Gradle>
      <description></description>
      <switches></switches>
      <tasks>build</tasks>
      <rootBuildScriptDir></rootBuildScriptDir>
      <buildFile></buildFile>
      <gradleName>$gradleVersion</gradleName>
      <useWrapper>false</useWrapper>
    </hudson.plugins.gradle.Gradle>
  </builders>
  <publishers/>
  <buildWrappers/>
</project>
```

After running `java -jar kin.jar` three job configurations (kin, foo, bar) are
created: `build/kin/config.xml`, `build/foo/config.xml` and
`build/bar/config.xml`. When building job configuration, each `$name` property
in `gradle.tpl` is replaced with actual property value specified in `build.kin`.

`kin` job configuration:

 * `$gradleVersion` is replaced with `1.0-milestone-4`
 * `$scm` is replaced with `https://github.com/mbezjak/kin`
 * `$task` is replaced with `build`

`foo` job configuration:

 * `$gradleVersion` is replaced with `1.0-milestone-4`
 * `$scm` is replaced with `http://acme.com/git/foo`
 * `$task` is replaced with `jar`

`bar` job configuration:

 * `$gradleVersion` is replaced with `0.9.2`
 * `$scm` is replaced with `http://acme.com/git/bar`
 * `$task` is replaced with `build`

Note: every template can also use special `jobName` property. It is always bound
to current job configuration. For example, when building job configuration
`foo`, property `$jobName` has a value of `foo`. Same for `bar` and so on.

## How to install created job configurations
What to do after `kin` successfully creates jenkins/hudson job configurations?
Upload created `build` directory to `$JENKINS_HOME/jobs` directory, of
course. `build` directory matches [directory
structure](http://wiki.hudson-ci.org/display/HUDSON/Administering+Hudson) of
`$JENKINS_HOME/jobs`.

Here is a very simple and elegant solution that works when jenkins/hudson is
installed on Linux. Moreover it automates building configuration files. Put
`kin.jar`, `build.kin` and `*.tpl` files into one directory under SCM (`git`,
`mercurial` or any other). Create new jenkins/hudson job, setup SCM and build
triggers (example, poll every 10 minutes). SCM should use *clean build*
option. Add new shell build step with following content:

    java -jar "$WORKSPACE/kin.jar"
    rsync --archive --checksum --verbose "$WORKSPACE/build/" "$HOME/jobs"
    curl "${JENKINS_URL}reload"

Now every time you commit (and push) a change in configuration jenkins/hudson
will update configuration and reload itself automatically. Note that this
approach hasn't been tested in distributed environment!

Hints for other solutions: rsync, scp, wget, nfs, windows (samba) share,
jenkins/hudson script console, [jenkins/hudson remote
API](http://stackoverflow.com/questions/3886892/configure-or-create-hudson-job-automatically),
etc.

For hudson just replace `JENKINS` with `HUDSON`.

## Flexibility
Remember that `build.kin` is powered by very simple DSL. Underneath all that is
still [groovy](http://groovy.codehaus.org). Unlike `*.properties` or `*.ini`
files when writing `build.kin` you have whole programming language to work with.
Templates are processed by
[SimpleTemplateEngine](http://groovy.codehaus.org/api/groovy/text/SimpleTemplateEngine.html)
- so groovy again. This gives you a great deal of flexibility when creating
templates and building job configurations.

## Build from Source
Prerequisites: JDK 6+

    $ ./gradlew clean build

To import project into eclipse:

    $ ./gradlew eclipse

Then: File -> Import... -> Existing Projects into Workspace

## Further Resources

 * Homepage:       https://github.com/mbezjak/kin
 * Issue tracker:  https://github.com/mbezjak/kin/issues
 * Wiki:           https://github.com/mbezjak/kin/wiki
 * CI server:      http://travis-ci.org/mbezjak/kin
 * License:        MIT (see LICENSE file)
