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
package io.micronaut.starter.api.create;

import io.micronaut.core.io.Writable;
import io.micronaut.http.HttpResponse;
import io.micronaut.starter.api.ApplicationTypes;
import io.micronaut.starter.options.BuildTool;
import io.micronaut.starter.options.Language;
import io.micronaut.starter.options.TestFramework;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Defines the signature for creating an application.
 *
 * @author graemerocher
 * @since 1.0.0
 */
public interface CreateOperation {

    /**
     * Creates an application.
     * @param name The name of the application
     * @param features The features
     * @param buildTool The build tool
     * @param testFramework The test framework
     * @param lang The lang
     * @return An HTTP response that emits a writable
     */
    HttpResponse<Writable> createApp(
            ApplicationTypes type,
            String name,
            @Nullable List<String> features,
            @Nullable BuildTool buildTool,
            @Nullable TestFramework testFramework,
            @Nullable Language lang
    );
}
