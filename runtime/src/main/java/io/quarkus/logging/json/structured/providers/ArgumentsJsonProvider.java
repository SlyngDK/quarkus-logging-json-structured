package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.StructuredArgument;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;

public class ArgumentsJsonProvider implements JsonProvider {

    public static final String FIELD_ARGUMENTS = "arguments";


    private boolean includeStructuredArguments = true;
    private boolean includeNonStructuredArguments;
    private String nonStructuredArgumentsFieldPrefix = "arg";

    public ArgumentsJsonProvider() {
    }

    public ArgumentsJsonProvider(boolean includeNonStructuredArguments) {
        this.includeNonStructuredArguments = includeNonStructuredArguments;
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
                    if (!hasWrittenFieldName) {
                        generator.writeObjectFieldStart(FIELD_ARGUMENTS);
                        hasWrittenFieldName = true;
                    }
                    StructuredArgument structuredArgument = (StructuredArgument) arg;
                    structuredArgument.writeTo(generator);
                }
            } else if (includeNonStructuredArguments) {
                if (!hasWrittenFieldName) {
                    generator.writeObjectFieldStart(FIELD_ARGUMENTS);
                    hasWrittenFieldName = true;
                }
                String fieldName = nonStructuredArgumentsFieldPrefix + argIndex;
                generator.writeObjectField(fieldName, arg);
            }
        }

        if (hasWrittenFieldName) {
            generator.writeEndObject();
        }
    }
}
