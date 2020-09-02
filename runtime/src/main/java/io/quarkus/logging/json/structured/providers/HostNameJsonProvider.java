package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.JsonWritingUtils;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;

public class HostNameJsonProvider implements JsonProvider {

    public static final String FIELD_HOST_NAME = "host_name";

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        if (isNotNullOrEmpty(event.getHostName())) {
            JsonWritingUtils.writeStringField(generator, FIELD_HOST_NAME, event.getHostName());
        }
    }
}
