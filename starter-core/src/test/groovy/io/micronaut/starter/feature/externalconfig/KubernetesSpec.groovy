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

class KubernetesSpec extends Specification implements ProjectFixture, ContextFixture {

    @Shared
    @AutoCleanup
    BeanContext beanContext = BeanContext.run()

    @Unroll
    void 'test gradle kubernetes feature for language=#language'() {
        when:
        String template = buildGradle.template(buildProject(), getFeatures(['kubernetes'], language)).render().toString()

        then:
        template.contains('implementation "io.micronaut.kubernetes:micronaut-kubernetes-discovery-client"')
        template.contains('implementation "io.micronaut:micronaut-management"')
        template.contains('id "com.google.cloud.tools.jib" version "2.1.0"')

        where:
        language << [Language.java, Language.kotlin, Language.groovy]
    }

    @Unroll
    void 'test maven kubernetes feature for language=#language'() {
        when:
        String template = pom.template(buildProject(), getFeatures(['kubernetes'], language), []).render().toString()

        then:
        template.contains("""
    <dependency>
      <groupId>io.micronaut.kubernetes</groupId>
      <artifactId>micronaut-kubernetes-discovery-client</artifactId>
      <scope>compile</scope>
    </dependency>
""")

        where:
        language << [Language.java, Language.kotlin, Language.groovy]
    }

    void 'test kubernetes configuration'() {
        when:
        CommandContext commandContext = buildCommandContext(['kubernetes'])

        then:
        commandContext.bootstrapConfig.get('micronaut.application.name'.toString()) == 'foo'
        commandContext.bootstrapConfig.get('micronaut.config-client.enabled'.toString()) == true
        commandContext.templates.get('k8sYaml')
    }

}
