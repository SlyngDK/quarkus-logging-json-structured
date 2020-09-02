package io.quarkus.logging.json.structured;

import com.fasterxml.jackson.core.JsonGenerator;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;

public interface JsonProvider {

    void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException;

    default boolean isNotNullOrEmpty(final String value) {
        return value != null && !value.isEmpty();
    }
}
