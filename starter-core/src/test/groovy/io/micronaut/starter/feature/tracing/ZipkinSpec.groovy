package io.micronaut.starter.feature.tracing

import io.micronaut.context.BeanContext
import io.micronaut.starter.command.CommandContext
import io.micronaut.starter.feature.build.gradle.templates.buildGradle
import io.micronaut.starter.feature.build.maven.templates.pom
import io.micronaut.starter.fixture.ContextFixture
import io.micronaut.starter.fixture.ProjectFixture
import io.micronaut.starter.options.Language
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class ZipkinSpec extends Specification implements ProjectFixture, ContextFixture {

    @Shared
    @AutoCleanup
    BeanContext beanContext = BeanContext.run()

    @Unroll
    void 'test gradle tracing-zipkin feature for language=#language'() {
        when:
        String template = buildGradle.template(buildProject(), getFeatures(['tracing-zipkin'], language)).render().toString()

        then:
        template.contains('implementation "io.micronaut:micronaut-tracing"')
        template.contains('implementation "io.opentracing.brave:brave-opentracing"')
        template.contains('runtimeOnly "io.zipkin.brave:brave-instrumentation-http"')
        template.contains('runtimeOnly "io.zipkin.reporter2:zipkin-reporter"')

        where:
        language << [Language.java, Language.kotlin, Language.groovy]
    }

    @Unroll
    void 'test maven tracing-zipkin feature for language=#language'() {
        when:
        String template = pom.template(buildProject(), getFeatures(['tracing-zipkin'], language), []).render().toString()

        then:
        template.contains("""
    <dependency>
      <groupId>io.micronaut</groupId>
      <artifactId>micronaut-tracing</artifactId>
      <scope>compile</scope>
    </dependency>
""")
        template.contains("""
    <dependency>
      <groupId>io.opentracing.brave</groupId>
      <artifactId>brave-opentracing</artifactId>
      <scope>compile</scope>
    </dependency>
""")
        template.contains("""
    <dependency>
      <groupId>io.zipkin.brave</groupId>
      <artifactId>brave-instrumentation-http</artifactId>
      <scope>runtime</scope>
    </dependency>
""")
        template.contains("""
    <dependency>
      <groupId>io.zipkin.reporter2</groupId>
      <artifactId>zipkin-reporter</artifactId>
      <scope>runtime</scope>
    </dependency>
""")

        where:
        language << [Language.java, Language.kotlin, Language.groovy]
    }

    void 'test tracing-zipkin configuration'() {
        when:
        CommandContext commandContext = buildCommandContext(['tracing-zipkin'])

        then:
        commandContext.configuration.get('tracing.zipkin.enabled'.toString()) == true
        commandContext.configuration.get('tracing.zipkin.http.url'.toString()) == 'http://localhost:9411'
        commandContext.configuration.get('tracing.zipkin.sampler.probability'.toString()) == 0.1
    }

}
