package io.quarkus.logging.json.structured.deployment;

import io.quarkus.logging.json.structured.JsonConfigStructured;
import io.quarkus.logging.json.structured.LoggingJsonStructuredRecorder;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.LogConsoleFormatBuildItem;

class LoggingJsonStructuredProcessor {

    private static final String FEATURE = "logging-json-structured";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public LogConsoleFormatBuildItem setUpFormatter(LoggingJsonStructuredRecorder recorder, JsonConfigStructured config) {
        return new LogConsoleFormatBuildItem(recorder.initializeJsonLogging(config));
    }
}
