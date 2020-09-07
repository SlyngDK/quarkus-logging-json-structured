package io.quarkus.logging.json.structured;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jboss.logmanager.ExtFormatter;
import org.jboss.logmanager.ExtLogRecord;

import java.util.List;
import java.util.ServiceConfigurationError;

public class JsonFormatter extends ExtFormatter {

    private final StringBuilderWriter writer = new StringBuilderWriter();
    private final List<JsonProvider> providers;
    private final JsonFactory jsonFactory;
    private boolean findAndRegisterJacksonModules = true;
    private char[] lineSeparatorBytes = "\n".toCharArray();

    public JsonFormatter(List<JsonProvider> providers) {
        this.providers = providers;
        this.jsonFactory = createJsonFactory();
    }

    public boolean isFindAndRegisterJacksonModules() {
        return findAndRegisterJacksonModules;
    }

    public void setFindAndRegisterJacksonModules(boolean findAndRegisterJacksonModules) {
        this.findAndRegisterJacksonModules = findAndRegisterJacksonModules;
    }

    private JsonFactory createJsonFactory() {
        ObjectMapper objectMapper = new ObjectMapper()
                /*
                 * Assume empty beans are ok.
                 */
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        if (findAndRegisterJacksonModules) {
            try {
                objectMapper.findAndRegisterModules();
            } catch (ServiceConfigurationError serviceConfigurationError) {
//                addError("Error occurred while dynamically loading jackson modules", serviceConfigurationError);
                System.err.println("Error occurred while dynamically loading jackson modules");
                serviceConfigurationError.printStackTrace();
            }
        }

        JsonFactory jsonFactory = objectMapper
                .getFactory()
                /*
                 * When generators are flushed, don't flush the underlying outputStream.
                 *
                 * This allows some streaming optimizations when using an encoder.
                 *
                 * The encoder generally determines when the stream should be flushed
                 * by an 'immediateFlush' property.
                 *
                 * The 'immediateFlush' property of the encoder can be set to false
                 * when the appender performs the flushes at appropriate times
                 * (such as the end of a batch in the AbstractLogstashTcpSocketAppender).
                 */
                .disable(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM);

        return jsonFactory;
    }

    @Override
    public String format(ExtLogRecord record) {
        try {
            try (JsonGenerator generator = this.jsonFactory.createGenerator(writer)) {
                generator.writeStartObject();
                for (JsonProvider provider : this.providers) {
                    provider.writeTo(generator, record);
                }
                generator.writeEndObject();
                generator.flush();
                writer.write(lineSeparatorBytes);
            }
            return writer.toString();
        } catch (Exception e) {
            // Wrap and rethrow
            throw new RuntimeException(e);
        } finally {
            // Clear the writer for the next format
            writer.clear();
        }
    }
}
