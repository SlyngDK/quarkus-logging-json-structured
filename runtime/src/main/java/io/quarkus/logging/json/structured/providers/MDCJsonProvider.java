package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.JsonWritingUtils;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;

public class MDCJsonProvider implements JsonProvider {

    public static final String FIELD_MDC = "mdc";

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        JsonWritingUtils.writeMapStringFields(generator, FIELD_MDC, event.getMdcCopy());
    }
}
