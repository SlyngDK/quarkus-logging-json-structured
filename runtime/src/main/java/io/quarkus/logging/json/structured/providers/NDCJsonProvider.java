package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.JsonWritingUtils;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;

public class NDCJsonProvider implements JsonProvider {

    public static final String FIELD_NDC = "ndc";

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        JsonWritingUtils.writeStringField(generator, FIELD_NDC, event.getNdc());
    }
}
