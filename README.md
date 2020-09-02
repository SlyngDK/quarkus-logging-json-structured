# Quarkus Logging Json Structured
Quarkus logging extension inspired of the work in [logstash-logback-encoder](https://github.com/logstash/logstash-logback-encoder)

# State of project
This is POC, to show a possible solution to do structured json logging in Quarkus.

#Structured argument
If you want to do structured logging of arguments, then the argument send with your logging, can implement `io.quarkus.logging.json.structured.StructuredArgument`. Then it is possible to use the JsonGenerator to format the argument in json. 

# Custom Json Provider
It is possible to implement your own custom json provider.

Just extend `io.quarkus.logging.json.structured.JsonProvider`, and provide it using CDI.