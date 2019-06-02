package net.stefanfuchs.avro.mavenplugin.model.input

enum class LocicalFieldType(val code: String) {
    /** nullable field **/
    DECIMAL("decimal"),
    UUID("uuid"),
    DATE("date"),
    TIME_MILLISECONDS("time-millis"),
    TIME_MICROSECONDS("time-micros"),
    TIMESTAMP_MILLISECONDS("timestamp-millis"),
    TIMESTAMP_MICROSECONDS("timestamp-micros"),
    DURATION("duration");
}
