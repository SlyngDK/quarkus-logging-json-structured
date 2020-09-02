package io.quarkus.logging.json.structured.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import io.quarkus.logging.json.structured.JsonProvider;
import io.quarkus.logging.json.structured.JsonWritingUtils;
import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimestampJsonProvider implements JsonProvider {

    public static final String FIELD_TIMESTAMP = "@timestamp";
    private final DateTimeFormatter dateTimeFormatter;

    public TimestampJsonProvider() {
        dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault());
    }

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        long millis = event.getMillis();

        JsonWritingUtils.writeStringField(generator, FIELD_TIMESTAMP, dateTimeFormatter.format(Instant.ofEpochMilli(millis)));
    }
}
