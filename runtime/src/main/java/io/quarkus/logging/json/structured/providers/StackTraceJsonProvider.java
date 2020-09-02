package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.JsonWritingUtils;
import io.quarkus.logging.json.structured.StringBuilderWriter;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;
import java.io.PrintWriter;

public class StackTraceJsonProvider implements JsonProvider {

    public static final String FIELD_STACK_TRACE = "stack_trace";

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        if (event.getThrown() != null) {
            final StringBuilderWriter w = new StringBuilderWriter();
            event.getThrown().printStackTrace(new PrintWriter(w));
            JsonWritingUtils.writeStringField(generator, FIELD_STACK_TRACE, w.toString());
        }
    }
}
