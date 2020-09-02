package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.JsonWritingUtils;
import org.jboss.logmanager.ExtFormatter;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;

public class MessageJsonProvider extends ExtFormatter implements JsonProvider {

    public static final String FIELD_MESSAGE = "message";

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        JsonWritingUtils.writeStringField(generator, FIELD_MESSAGE, formatMessage(event));
    }

    @Override
    public String format(ExtLogRecord record) {
        return null;
    }
}
