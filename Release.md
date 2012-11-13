Release steps:

1. Add `Changelog.md` entry
2. Update version in `build.gradle`
3. Update version in `src/main/groovy/hr/helix/kin/Help.groovy`
4. Gradle work:

       $ ./gradlew clean build

5. Upload `build/distributions/kin-$version.zip` to
`https://github.com/mbezjak/kin/downloads`

6. Git work:

        $ git tag --annotate $version
        $ git push
        $ git push --tags
