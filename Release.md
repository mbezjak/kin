Things to do when releasing:

 * Mention changes in `Changelog.md`
 * Update version in `build.gradle`
 * Update version in `src/main/groovy/hr/helix/kin/Help.groovy`
 * `gradle build` and upload `build/distributions/kin-$version.zip` to
   `https://github.com/mbezjak/kin/downloads`

git work:

    $ git tag $version
    $ git push
    $ git push --tags
