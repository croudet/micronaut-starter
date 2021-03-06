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
package io.micronaut.starter.feature.database;

import io.micronaut.starter.feature.Feature;
import io.micronaut.starter.feature.FeatureContext;
import io.micronaut.starter.options.Language;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class MongoGorm implements Feature {

    private final MongoReactive mongoReactive;

    public MongoGorm(MongoReactive mongoReactive) {
        this.mongoReactive = mongoReactive;
    }

    @Override
    public String getName() {
        return "mongo-gorm";
    }

    @Override
    public String getTitle() {
        return "GORM for MongoDB";
    }

    @Override
    public String getDescription() {
        return "Configures GORM for MongoDB for Groovy applications";
    }

    @Override
    public Optional<Language> getRequiredLanguage() {
        return Optional.of(Language.groovy);
    }

    @Override
    public void processSelectedFeatures(FeatureContext featureContext) {
        if (!featureContext.isPresent(MongoReactive.class)) {
            featureContext.addFeature(mongoReactive);
        }
    }
}
