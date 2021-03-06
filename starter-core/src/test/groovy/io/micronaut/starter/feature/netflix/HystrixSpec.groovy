package io.micronaut.starter.feature.netflix

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

class HystrixSpec extends Specification implements ProjectFixture, ContextFixture {

    @Shared
    @AutoCleanup
    BeanContext beanContext = BeanContext.run()

    @Unroll
    void 'test gradle netflix-hystrix feature for language=#language'() {
        when:
        String template = buildGradle.template(buildProject(), getFeatures(['netflix-hystrix'], language)).render().toString()

        then:
        template.contains('implementation "io.micronaut.configuration:micronaut-netflix-hystrix"')

        where:
        language << [Language.java, Language.kotlin, Language.groovy]
    }

    @Unroll
    void 'test maven netflix-hystrix feature for language=#language'() {
        when:
        String template = pom.template(buildProject(), getFeatures(['netflix-hystrix'], language), []).render().toString()

        then:
        template.contains("""
    <dependency>
      <groupId>io.micronaut.configuration</groupId>
      <artifactId>micronaut-netflix-hystrix</artifactId>
      <scope>compile</scope>
    </dependency>
""")

        where:
        language << [Language.java, Language.kotlin, Language.groovy]
    }

    void 'test netflix-hystrix configuration'() {
        when:
        CommandContext commandContext = buildCommandContext(['netflix-hystrix'])

        then:
        commandContext.configuration.get('hystrix.stream.enabled'.toString()) == false
    }

}
