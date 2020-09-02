package io.quarkus.logging.json.structured;

import io.quarkus.logging.json.structured.providers.ArgumentsJsonProvider;
import io.quarkus.logging.json.structured.providers.HostNameJsonProvider;
import io.quarkus.logging.json.structured.providers.LogLevelJsonProvider;
import io.quarkus.logging.json.structured.providers.LoggerClassNameJsonProvider;
import io.quarkus.logging.json.structured.providers.LoggerNameJsonProvider;
import io.quarkus.logging.json.structured.providers.MDCJsonProvider;
import io.quarkus.logging.json.structured.providers.MessageJsonProvider;
import io.quarkus.logging.json.structured.providers.ProcessIdJsonProvider;
import io.quarkus.logging.json.structured.providers.ProcessNameJsonProvider;
import io.quarkus.logging.json.structured.providers.SequenceJsonProvider;
import io.quarkus.logging.json.structured.providers.StackTraceJsonProvider;
import io.quarkus.logging.json.structured.providers.ThreadIDJsonProvider;
import io.quarkus.logging.json.structured.providers.ThreadNameJsonProvider;
import io.quarkus.logging.json.structured.providers.TimestampJsonProvider;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InjectableInstance;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Formatter;
import java.util.stream.Collectors;

@Recorder
public class LoggingJsonStructuredRecorder {
    private static final Logger log = LoggerFactory.getLogger(LoggingJsonStructuredRecorder.class);


    public RuntimeValue<Optional<Formatter>> initializeJsonLogging(JsonConfigStructured config) {

        InjectableInstance<JsonProvider> instance = Arc.container().select(JsonProvider.class);

        List<JsonProvider> providers = new ArrayList<>();

        providers.add(new TimestampJsonProvider());
        providers.add(new LogLevelJsonProvider());
        providers.add(new LoggerNameJsonProvider());
        providers.add(new LoggerClassNameJsonProvider());
        providers.add(new SequenceJsonProvider());
        providers.add(new MessageJsonProvider());
        providers.add(new MDCJsonProvider());
        providers.add(new HostNameJsonProvider());
        providers.add(new ProcessNameJsonProvider());
        providers.add(new ProcessIdJsonProvider());
        providers.add(new StackTraceJsonProvider());
        providers.add(new ThreadNameJsonProvider());
        providers.add(new ThreadIDJsonProvider());
        providers.add(new ArgumentsJsonProvider());

        instance.forEach(providers::add);

        if (log.isDebugEnabled()) {
            String installedProviders = providers.stream().map(p -> p.getClass().toString()).collect(Collectors.joining(", ", "[", "]"));
            log.debug("Installed json providers {}", installedProviders);
        }

        return new RuntimeValue<>(Optional.of(new JsonFormatter(providers)));
    }
}
