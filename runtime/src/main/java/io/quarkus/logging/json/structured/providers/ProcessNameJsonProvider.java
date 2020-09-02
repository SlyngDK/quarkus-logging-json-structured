package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.JsonWritingUtils;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;

public class ProcessNameJsonProvider implements JsonProvider {

    public static final String FIELD_PROCESS_NAME = "process_name";

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        if (isNotNullOrEmpty(event.getProcessName())) {
            JsonWritingUtils.writeStringField(generator, FIELD_PROCESS_NAME, event.getProcessName());
        }
    }
}
