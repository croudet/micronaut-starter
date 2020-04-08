package io.micronaut.starter.feature.metricometer;

import io.micronaut.starter.command.CommandContext;
import io.micronaut.starter.feature.other.Management;

import javax.inject.Singleton;

@Singleton
public class AzureMonitor extends MicrometerFeature {

    public AzureMonitor(Core core, Management management) {
        super(core, management);
    }

    @Override
    public String getName() {
        return "micrometer-azure-monitor";
    }

    @Override
    public void apply(CommandContext commandContext) {
        commandContext.getConfiguration().put(EXPORT_PREFIX + ".azuremonitor.enabled", true);
        commandContext.getConfiguration().put(EXPORT_PREFIX + ".azuremonitor.instrumentationKey", "${AZUREMONITOR_INSTRUMENTATION_KEY}");
        commandContext.getConfiguration().put(EXPORT_PREFIX + ".azuremonitor.step", "PT1M");
    }
}