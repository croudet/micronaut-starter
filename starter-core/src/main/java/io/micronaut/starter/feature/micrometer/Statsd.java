/*
 * Copyright 2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.starter.feature.micrometer;

import io.micronaut.core.naming.NameUtils;
import io.micronaut.starter.command.CommandContext;
import io.micronaut.starter.feature.other.Management;

import javax.inject.Singleton;

@Singleton
public class Statsd extends MicrometerFeature {

    public Statsd(Core core, Management management) {
        super(core, management);
    }

    @Override
    public String getName() {
        return "micrometer-statsd";
    }

    @Override
    public String getTitle() {
        return NameUtils.camelCase(getName());
    }

    @Override
    public String getDescription() {
        return "Adds support for Micrometer metrics (w/ Statsd reporter)";
    }

    @Override
    public void apply(CommandContext commandContext) {
        commandContext.getConfiguration().put(EXPORT_PREFIX + ".statsd.enabled", true);
        commandContext.getConfiguration().put(EXPORT_PREFIX + ".statsd.flavor", "datadog");
        commandContext.getConfiguration().put(EXPORT_PREFIX + ".statsd.host", "localhost");
        commandContext.getConfiguration().put(EXPORT_PREFIX + ".statsd.port", 8125);
        commandContext.getConfiguration().put(EXPORT_PREFIX + ".statsd.step", "PT1M");
    }
}
