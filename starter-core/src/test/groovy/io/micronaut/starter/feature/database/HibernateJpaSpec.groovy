package io.micronaut.starter.feature.database

import io.micronaut.context.BeanContext
import io.micronaut.starter.command.CommandContext
import io.micronaut.starter.feature.Features
import io.micronaut.starter.fixture.ContextFixture
import io.micronaut.starter.fixture.ProjectFixture
import io.micronaut.starter.feature.build.gradle.templates.buildGradle
import io.micronaut.starter.feature.build.maven.templates.pom
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class HibernateJpaSpec extends Specification implements ProjectFixture, ContextFixture {

    @Shared @AutoCleanup BeanContext beanContext = BeanContext.run()

    void "test hibernate jpa features"() {
        when:
        Features features = getFeatures(['hibernate-jpa'])

        then:
        features.contains("h2")
        features.contains("jdbc-tomcat")
        features.contains("hibernate-jpa")
    }

    void "test dependencies are present for gradle"() {
        when:
        String template = buildGradle.template(buildProject(), getFeatures(["hibernate-jpa"])).render().toString()

        then:
        template.contains("implementation \"io.micronaut.configuration:micronaut-hibernate-jpa\"")
        template.contains("runtimeOnly \"com.h2database:h2\"")
    }

    void "test dependencies are present for maven"() {
        when:
        String template = pom.template(buildProject(), getFeatures(["hibernate-jpa"]), []).render().toString()

        then:
        template.contains("""
    <dependency>
      <groupId>io.micronaut.configuration</groupId>
      <artifactId>micronaut-hibernate-jpa</artifactId>
      <scope>compile</scope>
    </dependency>
""")
        template.contains("""
    <dependency>
      <groupId>io.micronaut.configuration</groupId>
      <artifactId>micronaut-jdbc-tomcat</artifactId>
      <scope>compile</scope>
    </dependency>
""")
        template.contains("""
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>
""")
    }

    void "test config"() {
        when:
        CommandContext ctx = buildCommandContext(['hibernate-jpa'])

        then:
        ctx.configuration.containsKey('datasources.default.url')
        ctx.configuration.containsKey('jpa.default.properties.hibernate.hbm2ddl.auto')
    }
}

