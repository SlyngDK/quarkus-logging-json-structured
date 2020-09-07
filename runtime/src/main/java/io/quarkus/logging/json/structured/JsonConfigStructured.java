package io.quarkus.logging.json.structured;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

import java.util.Optional;

@ConfigRoot(phase = ConfigPhase.RUN_TIME, name = "log.console.json.structured")
public class JsonConfigStructured {
    /**
     * Determine whether to enable the JSON console formatting extension, which disables "normal" console formatting.
     */
    @ConfigItem(name = ConfigItem.PARENT, defaultValue = "true")
    boolean enable;

    /**
     * Configuration properties to customize fields
     */
    public Optional<FieldConfig> fieldConfig;

    @ConfigGroup
    public class FieldConfig {
        /**
         * Used to customize {@link io.quarkus.logging.json.structured.providers.ArgumentsJsonProvider}
         */
        public Optional<ArgumentsConfig> arguments;
    }

    @ConfigGroup
    public class ArgumentsConfig {

        /**
         * Used to wrap arguments in an json object, with this fieldName on root json.
         */
        @ConfigItem
        public Optional<String> fieldName;
    }
}
