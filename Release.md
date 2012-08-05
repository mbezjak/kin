Kin release steps:

1. Update version in `build.gradle`
2. Update version in `src/main/groovy/hr/helix/kin/Help.groovy`
3. Gradle work:

       $ ./gradlew dist

4. Upload `build/distributions/kin-$version.zip` to
`https://github.com/mbezjak/kin/downloads`

5. Git work:

        $ git tag $version
        $ git push
        $ git push --tags
