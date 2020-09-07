package io.quarkus.logging.json.structured;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

public class StructuredArgumentImpl implements StructuredArgument {
    private final String key;
    private final Object value;

    StructuredArgumentImpl(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static StructuredArgument jsonArg(String key, Object value) {
        return new StructuredArgumentImpl(key, value);
    }

    @Override
    public void writeTo(JsonGenerator generator) throws IOException {
        generator.writeObjectField(key, value);
    }
}
