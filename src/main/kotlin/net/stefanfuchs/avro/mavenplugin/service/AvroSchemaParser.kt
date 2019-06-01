package net.stefanfuchs.avro.mavenplugin.service

import com.google.gson.JsonParser
import java.io.Reader

/**
 * Parses a Avro Schema (JSON) as defiened under
 * https://avro.apache.org/docs/current/spec.html#schemas
 * (Apache Avro 1.9.0 Specification)
 */
class AvroSchemaParser(val reader: Reader) {

    /**
     * Stars the parsing. Expects that reader returns a JSON
     * where the top level object is a Avro Record
     */
    fun parse(): AvroSchemaItem.AvroSchemaItemRecord {
        val root = JsonParser().parse(reader).asJsonObject
        return AvroSchemaItem.AvroSchemaItemRecord(root)
    }
}
