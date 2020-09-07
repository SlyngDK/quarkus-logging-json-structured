package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonConfigStructured;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.StructuredArgument;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;

public class ArgumentsJsonProvider implements JsonProvider {
    private boolean includeStructuredArguments = true;
    private boolean includeNonStructuredArguments;
    private String nonStructuredArgumentsFieldPrefix = "arg";
    private String fieldName;

    public ArgumentsJsonProvider(JsonConfigStructured config) {
        if (config.fieldConfig.isPresent() && config.fieldConfig.get().arguments.isPresent()) {
            JsonConfigStructured.ArgumentsConfig arguments = config.fieldConfig.get().arguments.get();
            arguments.fieldName.ifPresent(f -> fieldName = f);
        }
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setIncludeStructuredArguments(boolean includeStructuredArguments) {
        this.includeStructuredArguments = includeStructuredArguments;
    }

    public void setIncludeNonStructuredArguments(boolean includeNonStructuredArguments) {
        this.includeNonStructuredArguments = includeNonStructuredArguments;
    }

    public void setNonStructuredArgumentsFieldPrefix(String nonStructuredArgumentsFieldPrefix) {
        this.nonStructuredArgumentsFieldPrefix = nonStructuredArgumentsFieldPrefix;
    }

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {

        if (!includeStructuredArguments && !includeNonStructuredArguments) {
            // Short-circuit if nothing is included
            return;
        }

        Object[] args = event.getParameters();

        if (args == null || args.length == 0) {
            return;
        }

        boolean hasWrittenFieldName = false;

        for (int argIndex = 0; argIndex < args.length; argIndex++) {

            Object arg = args[argIndex];

            if (arg instanceof StructuredArgument) {
                if (includeStructuredArguments) {
                    if (!hasWrittenFieldName && fieldName != null) {
                        generator.writeObjectFieldStart(fieldName);
                        hasWrittenFieldName = true;
                    }
                    StructuredArgument structuredArgument = (StructuredArgument) arg;
                    structuredArgument.writeTo(generator);
                }
            } else if (includeNonStructuredArguments) {
                if (!hasWrittenFieldName && fieldName != null) {
                    generator.writeObjectFieldStart(fieldName);
                    hasWrittenFieldName = true;
                }
                String innerFieldName = nonStructuredArgumentsFieldPrefix + argIndex;
                generator.writeObjectField(innerFieldName, arg);
            }
        }

        if (hasWrittenFieldName) {
            generator.writeEndObject();
        }
    }
}
