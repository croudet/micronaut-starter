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
package io.micronaut.starter.api;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.naming.Described;
import io.micronaut.core.naming.Named;
import io.micronaut.starter.feature.Feature;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents an application feature.
 *
 * @author graemerocher
 * @since 1.0.0
 */
@Introspected
@Schema(name = "Feature")
public class FeatureDTO implements Named, Described {
    private final String name;
    private final String title;
    private final String description;

    /**
     * Default constructor.
     * @param feature The feature
     */
    public FeatureDTO(Feature feature) {
        this.name = feature.getName();
        this.title = feature.getTitle();
        this.description = feature.getDescription();
    }

    /**
     * Default constructor.
     * @param name The name
     * @param title The title
     * @param description The description
     */
    @Creator
    public FeatureDTO(String name, String title, String description) {
        this.name = name;
        this.title = title;
        this.description = description;
    }

    /**
     * @return The name of the feature
     */
    @Schema(description = "The name of the feature")
    public String getName() {
        return name;
    }

    /**
     * @return The title of the feature
     */
    @Schema(description = "The title of the feature")
    public String getTitle() {
        return title;
    }

    /**
     * @return The description of the feature
     */
    @Schema(description = "A description of the feature")
    public String getDescription() {
        return description;
    }
}
