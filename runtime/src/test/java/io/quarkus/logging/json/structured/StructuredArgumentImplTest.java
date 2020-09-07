package io.quarkus.logging.json.structured;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StructuredArgumentImplTest {

    @Test
    void testStructuredArgumentWriteTo() throws Exception {
        assertEquals("{\"key\":null}", run("key", null));
        assertEquals("{\"anotherKey\":null}", run("anotherKey", null));
        assertEquals("{\"key\":324}", run("key", (short) 324));
        assertEquals("{\"key\":324}", run("key", 324));
        assertEquals("{\"key\":324}", run("key", 324L));
        assertEquals("{\"key\":324.348}", run("key", 324.348));
        assertEquals("{\"key\":324.348}", run("key", 324.348d));
        assertEquals("{\"key\":324}", run("key", BigInteger.valueOf(324)));
        assertEquals("{\"key\":324.348}", run("key", BigDecimal.valueOf(324.348d)));
        assertEquals("{\"key\":\"value\"}", run("key", "value"));
        assertEquals("{\"key\":[\"value\",\"value2\"]}", run("key", new String[]{"value", "value2"}));
        assertEquals("{\"key\":[\"value\",\"value2\"]}", run("key", Arrays.asList("value", "value2")));
        assertEquals("{\"key\":{}}", run("key", new Object()));
        assertEquals("{\"key\":{\"field1\":\"field1\",\"field2\":2389472389}}", run("key", new TestPojo()));
    }

    private String run(String key, Object value) throws IOException {
        JsonFactory factory = new ObjectMapper()
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .getFactory();

        StringBuilderWriter w = new StringBuilderWriter();
        try (JsonGenerator generator = factory.createGenerator(w)) {
            generator.writeStartObject();

            new StructuredArgumentImpl(key, value).writeTo(generator);
            generator.writeEndObject();
            generator.flush();
        }

        return w.toString();
    }

    private static class TestPojo{
        private final String field1 = "field1";
        private final Long field2 = 2389472389L;

        public String getField1() {
            return field1;
        }

        public Long getField2() {
            return field2;
        }
    }
}