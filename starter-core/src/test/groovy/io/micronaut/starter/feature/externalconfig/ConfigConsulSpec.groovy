package io.micronaut.starter.feature.externalconfig

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

class ConfigConsulSpec extends Specification implements ProjectFixture, ContextFixture {

    @Shared
    @AutoCleanup
    BeanContext beanContext = BeanContext.run()

    @Unroll
    void 'test gradle config-consul feature for language=#language'() {
        when:
        String template = buildGradle.template(buildProject(), getFeatures(['config-consul'], language)).render().toString()

        then:
        template.contains('implementation "io.micronaut:micronaut-discovery-client"')

        where:
        language << [Language.java, Language.kotlin, Language.groovy]
    }

    void 'test gradle config-consul multiple features'() {
        when:
        String template = buildGradle.template(buildProject(), getFeatures(['config-consul', 'discovery-consul'])).render().toString()

        then:
        template.count('implementation "io.micronaut:micronaut-discovery-client"') == 1
    }

    @Unroll
    void 'test maven config-consul feature for language=#language'() {
        when:
        String template = pom.template(buildProject(), getFeatures(['config-consul'], language), []).render().toString()

        then:
        template.contains("""
    <dependency>
      <groupId>io.micronaut</groupId>
      <artifactId>micronaut-discovery-client</artifactId>
      <scope>compile</scope>
    </dependency>
""")

        where:
        language << [Language.java, Language.kotlin, Language.groovy]
    }

    void 'test maven config-consul multiple features'() {
        when:
        String template = pom.template(buildProject(), getFeatures(['config-consul', 'discovery-consul']), []).render().toString()

        then:
        template.count("""
    <dependency>
      <groupId>io.micronaut</groupId>
      <artifactId>micronaut-discovery-client</artifactId>
      <scope>compile</scope>
    </dependency>
""") == 1
    }

    void 'test config-consul configuration'() {
        when:
        CommandContext commandContext = buildCommandContext(['config-consul'])

        then:
        commandContext.bootstrapConfig.get('micronaut.application.name'.toString()) == 'foo'
        commandContext.bootstrapConfig.get('micronaut.config-client.enabled'.toString()) == true
        commandContext.bootstrapConfig.get('consul.client.registration.enabled'.toString()) == true
        commandContext.bootstrapConfig.get('consul.client.defaultZone') == '${CONSUL_HOST:localhost}:${CONSUL_PORT:8500}'
    }

}
