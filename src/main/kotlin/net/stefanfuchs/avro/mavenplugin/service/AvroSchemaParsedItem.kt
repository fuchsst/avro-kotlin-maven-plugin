package net.stefanfuchs.avro.mavenplugin.service

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.stefanfuchs.avro.mavenplugin.model.input.FieldNames
import net.stefanfuchs.avro.mavenplugin.model.input.FieldType
import net.stefanfuchs.avro.mavenplugin.model.input.LogicalFieldType
import net.stefanfuchs.avro.mavenplugin.model.input.SortOrder


sealed class AvroSchemaItem {

    companion object {
        fun parse(jsonElement: JsonElement): AvroSchemaItem? {
            return when {
                jsonElement.isJsonArray -> AvroSchemaItemUnion(jsonElement.asJsonArray)
                jsonElement.isJsonObject -> parseComplexType(jsonElement)
                else -> AvroSchemaItemPrimitive(jsonElement.asString)
            }
        }

        private fun parseComplexType(json: JsonElement): AvroSchemaItem? {
            require(json is JsonObject)
            require(hasTypeField(json))
            return when (getTypeString(json)?.toLowerCase()) {
                FieldType.RECORD.code -> AvroSchemaItemRecord(json)
                FieldType.ENUM.code -> AvroSchemaItemEnum(json)
                FieldType.MAP.code -> AvroSchemaItemMap(json)
                FieldType.FIXED.code -> AvroSchemaItemFixed(json)
                else -> parseLogicalType(json)
            }
        }

        private fun parseLogicalType(json: JsonElement): AvroSchemaItem? {
            require(json is JsonObject)
            require(hasTypeField(json))
            return when (getLogicalTypeString(json)?.toLowerCase()) {
                LogicalFieldType.DURATION.code -> AvroSchemaItemLogicTypeFixedDuration(json)
                LogicalFieldType.DATE.code -> AvroSchemaItemLogicTypeIntDate(json)
                LogicalFieldType.DECIMAL.code -> AvroSchemaItemLogicTypeBytesDecimal(json)
                LogicalFieldType.TIMESTAMP_MICROSECONDS.code -> AvroSchemaItemLogicTypeIntTimestampMicros(json)
                LogicalFieldType.TIMESTAMP_MILLISECONDS.code -> AvroSchemaItemLogicTypeIntTimestampMillis(json)
                LogicalFieldType.TIME_MICROSECONDS.code -> AvroSchemaItemLogicTypeIntTimeMicros(json)
                LogicalFieldType.TIME_MILLISECONDS.code -> AvroSchemaItemLogicTypeIntTimeMillis(json)
                LogicalFieldType.UUID.code -> AvroSchemaItemLogicTypeStringUuid(json)
                else -> AvroSchemaItemRecord(json)
            }
        }

        fun hasTypeField(jsonObject: JsonObject) = jsonObject.has(FieldNames.TYPE.code)
        fun getTypeString(jsonObject: JsonObject): String? {
            val value: JsonElement? = jsonObject.get(FieldNames.TYPE.code)
            return if (value?.isJsonPrimitive == true)
                value.asString
            else
                null
        }

        fun hasLogicalTypeField(jsonObject: JsonObject): Boolean = jsonObject.has(FieldNames.LOGICALTYPE.code)

        fun getLogicalTypeString(jsonObject: JsonObject): String? {
            val value: JsonElement? = jsonObject.get(FieldNames.LOGICALTYPE.code)
            return if (value != null && value.isJsonPrimitive)
                value.asString
            else
                null
        }
    }


    abstract val fieldType: FieldType
    abstract fun isValid(): Boolean
    abstract val fieldsAsStrings: List<String?>


    override fun toString(): String {
        return fieldsAsStrings.joinToString("")
    }


    abstract class NamedAvroSchemaItem(jsonObject: JsonObject) : AvroSchemaItem() {
        val name: String? =
                if (jsonObject.has(FieldNames.NAME.code)) {
                    jsonObject.get(FieldNames.NAME.code).asString
                } else {
                    null
                }


        val doc: String? =
                if (jsonObject.has(FieldNames.DOC.code)) {
                    jsonObject.get(FieldNames.DOC.code).asString
                } else {
                    null
                }


        val aliases: List<String>? =
                if (jsonObject.has(FieldNames.ALIASES.code)) {
                    jsonObject.get(FieldNames.ALIASES.code)
                            .asJsonArray
                            .map { it.asString }
                } else {
                    null
                }


        override fun toString(): String {
            return fieldsAsStrings
                    .filterNotNull()
                    .joinToString(",", "{ ", "}")
        }
    }


    class AvroSchemaItemRecord(jsonObject: JsonObject) : NamedAvroSchemaItem(jsonObject) {
        override val fieldType = FieldType.values().find { getTypeString(jsonObject) == it.code } ?: FieldType.CLASSNAME


        val namespace: String? =
                if (jsonObject.has(FieldNames.NAMESPACE.code)) {
                    jsonObject.get(FieldNames.NAMESPACE.code).asString
                } else {
                    null
                }


        val fields: List<AvroSchemaItemRecordField>? =
                if (jsonObject.has(FieldNames.FIELDS.code)) {
                    jsonObject.get(FieldNames.FIELDS.code)
                            .asJsonArray
                            .map { AvroSchemaItemRecordField(it.asJsonObject) }
                } else {
                    null
                }


        override fun isValid(): Boolean = name != null && namespace != null && fields != null

        override val fieldsAsStrings = listOf(
                if (namespace != null) """ "namespace": "$namespace" """ else null,
                if (name != null) """ "name": "$name" """ else null,
                """ "type": "${fieldType.code}" """,
                if (aliases != null) """ "aliases": $aliases """ else null,
                if (doc != null) """ "doc": "$doc" """ else null,
                if (fields != null) """ "fields": $fields """ else null
        )

    }

    class AvroSchemaItemRecordField(jsonObject: JsonObject) : NamedAvroSchemaItem(jsonObject) {

        val fieldDef: AvroSchemaItem? =
                if (hasTypeField(jsonObject)) {
                    val typeString = getTypeString(jsonObject)
                    val fieldType = FieldType.values().find { it.code == typeString }
                    if (fieldType?.isPrimitive == true) {
                        parse(jsonObject) // primitives may have additional attributes (logical types) that are here on the same level
                    } else {
                        // if's not a primitive, it must be a complex type
                        parseComplexType(jsonObject.getAsJsonObject(FieldNames.TYPE.code))
                    }

                } else {
                    null
                }

        override val fieldType: FieldType = fieldDef?.fieldType ?: FieldType.CLASSNAME

        val order: SortOrder? =
                if (jsonObject.has(FieldNames.SORTORDER.code)) {
                    val jsonElement = jsonObject.get(FieldNames.SORTORDER.code)
                    SortOrder.valueOf(jsonElement.asString.toUpperCase())
                } else {
                    null
                }


        val default: String? =
                if (jsonObject.has(FieldNames.DEFAULT.code)) {
                    val jsonElement = jsonObject.get(FieldNames.DEFAULT.code)
                    jsonElement.asString
                } else {
                    null
                }


        override fun isValid(): Boolean = name != null

        override val fieldsAsStrings = (fieldDef?.fieldsAsStrings ?: emptyList()) + listOf(
                if (aliases != null) """ "aliases": $aliases """ else null,
                if (doc != null) """ "doc": "$doc" """ else null,
                if (default != null) """ "default": "$default" """ else null,
                if (order != null) """ "order": "$order" """ else null
        )
    }

    class AvroSchemaItemEnum(jsonObject: JsonObject) : NamedAvroSchemaItem(jsonObject) {
        override val fieldType = FieldType.ENUM

        val namespace: String? =
                if (jsonObject.has(FieldNames.NAMESPACE.code)) {
                    jsonObject.get(FieldNames.NAMESPACE.code).asString
                } else {
                    null
                }

        val symbols: List<String>? =
                if (jsonObject.has(FieldNames.SYMBOLS.code)) {
                    jsonObject.get(FieldNames.SYMBOLS.code)
                            .asJsonArray
                            .map { it.asString }
                } else {
                    null
                }

        val default: String? =
                if (jsonObject.has(FieldNames.DEFAULT.code)) {
                    val jsonElement = jsonObject.get(FieldNames.DEFAULT.code)
                    jsonElement.asString
                } else {
                    null
                }

        override fun isValid(): Boolean = name != null &&
                namespace != null &&
                symbols?.isNotEmpty() ?: false

        override val fieldsAsStrings = listOf(
                if (namespace != null) """ "namespace": "$namespace" """ else null,
                if (name != null) """ "name": "$name" """ else null,
                """ "type": "${fieldType.code}" """,
                if (aliases != null) """ "aliases": $aliases """ else null,
                if (doc != null) """ "doc": "$doc" """ else null,
                if (default != null) """ "default": "$default" """ else null,
                if (symbols != null) """ "symbols": $symbols """ else null
        )
    }

    class AvroSchemaItemArray(jsonObject: JsonObject) : AvroSchemaItem() {
        override val fieldType: FieldType = FieldType.ARRAY

        val items: AvroSchemaItem? by lazy {
            if (jsonObject.has(FieldNames.ITEMS.code)) {
                parse(jsonObject.get(FieldNames.ITEMS.code))
            } else {
                null
            }
        }

        override fun isValid(): Boolean = items != null

        override val fieldsAsStrings: List<String?>
            get() = listOfNotNull(
                    """ "type" : "${fieldType.code}" """,
                    if (items != null) """ "items": $items """ else null
            )

        override fun toString(): String {
            return fieldsAsStrings
                    .joinToString(",", "{ ", "}")
        }
    }

    class AvroSchemaItemMap(jsonObject: JsonObject) : AvroSchemaItem() {
        override val fieldType: FieldType = FieldType.MAP

        val values: AvroSchemaItem? =
                if (jsonObject.has(FieldNames.VALUES.code)) {
                    parse(jsonObject.get(FieldNames.VALUES.code))
                } else {
                    null
                }

        override fun isValid(): Boolean = values != null

        override val fieldsAsStrings: List<String?>
            get() = listOfNotNull(
                    """ "type" : "${fieldType.code}" """,
                    if (values != null) """ "items": $values """ else null
            )

        override fun toString(): String {
            return fieldsAsStrings
                    .joinToString(",", "{ ", "}")
        }
    }

    class AvroSchemaItemUnion(val jsonArray: JsonArray) : AvroSchemaItem() {
        override val fieldType = FieldType.UNION

        val isNullable: Boolean by lazy {
            val firstElement = jsonArray.firstOrNull()
            if (firstElement != null) {
                val parsedElement = parse(firstElement)
                parsedElement is AvroSchemaItemPrimitive &&
                        parsedElement.fieldType == FieldType.NULL
            } else {
                false
            }
        }

        val types: List<AvroSchemaItem?>? by lazy {
            if (jsonArray.toList().isNotEmpty()) {
                val alltypes = jsonArray.map { parse(it) }
                if (isNullable) {
                    alltypes
                            .filterNot { it is AvroSchemaItemPrimitive && it.fieldType == FieldType.NULL }
                } else {
                    alltypes
                }
            } else {
                null
            }
        }

        override fun isValid(): Boolean {
            return types != null &&
                    types!!.isNotEmpty() &&
                    (types!!.all {
                        it != null &&
                                it.isValid() &&
                                /** "null" primitive type is only allowed at the first position.
                                this has been checked on assignment and the element removed,
                                instead null is indicated by the separate field [isNullable]
                                 **/
                                !(it is AvroSchemaItemPrimitive && it.fieldType == FieldType.NULL)
                    })
        }

        override val fieldsAsStrings: List<String?>
            get() = listOfNotNull(
                    if (isNullable) """"null"""" else null,
                    types?.map { it.toString() }?.joinToString(",")
            )

        override fun toString(): String {
            return fieldsAsStrings
                    .joinToString(",", "[", "]")
        }
    }

    class AvroSchemaItemFixed(val jsonObject: JsonObject) : AvroSchemaItem() {
        override val fieldType = FieldType.FIXED

        val name: String? =
                if (jsonObject.has(FieldNames.SIZE.code)) {
                    jsonObject.get(FieldNames.SIZE.code).asString
                } else {
                    null
                }


        val size: Long? by lazy {
            if (jsonObject.has(FieldNames.SIZE.code)) {
                jsonObject.get(FieldNames.SIZE.code).asLong
            } else {
                null
            }
        }

        override fun isValid(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override val fieldsAsStrings: List<String?>
            get() = listOfNotNull(
                    if (name != null) """ "name": "$name" """ else null,
                    """ "type": "${fieldType.code}" """,
                    if (size != null) """ "default": "$size" """ else null
            )

        override fun toString(): String {
            return fieldsAsStrings.joinToString(",")
        }
    }

    class AvroSchemaItemPrimitive(val rawString: String) : AvroSchemaItem() {
        override val fieldType: FieldType = FieldType.values().find { rawString.toLowerCase() == it.code }
                ?: FieldType.CLASSNAME

        override fun isValid(): Boolean = true
        fun isKnownFieldType(): Boolean = fieldType != FieldType.CLASSNAME

        override val fieldsAsStrings: List<String?>
            get() = listOf(this.toString())


        override fun toString(): String {
            return """"${if (isKnownFieldType()) fieldType.code else rawString}""""

        }
    }


    abstract class AbstractAvroSchemaItemLogicType(jsonObject: JsonObject) : AvroSchemaItem() {
        override val fieldType: FieldType =
                if (jsonObject.has(FieldNames.TYPE.code)) {
                    val jsonElementString = jsonObject.get(FieldNames.TYPE.code).asString.toLowerCase()
                    FieldType.values().find { jsonElementString == it.code }
                } else {
                    null
                } ?: FieldType.CLASSNAME

        val logicalType: LogicalFieldType? =
                if (jsonObject.has(FieldNames.LOGICALTYPE.code)) {
                    val logicTypeString = jsonObject.get(FieldNames.LOGICALTYPE.code).asString
                    LogicalFieldType.values().find { it.code == logicTypeString }
                } else {
                    null
                }

        override val fieldsAsStrings: List<String?> = listOf(
                if (fieldType != FieldType.CLASSNAME) """ "type": "${fieldType.code}" """ else null,
                if (logicalType != null) """ "logicalType": "${logicalType.code}" """ else null
        )

        override fun toString(): String {
            return fieldsAsStrings
                    .filterNotNull()
                    .joinToString(",")


        }
    }

    class AvroSchemaItemLogicTypeBytesDecimal(private val jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        val precision: Int? =
                if (jsonObject.has(FieldNames.PRECISION.code)) {
                    jsonObject.get(FieldNames.PRECISION.code).asInt
                } else {
                    null
                }


        val scale: Int? =
                if (jsonObject.has(FieldNames.SCALE.code)) {
                    jsonObject.get(FieldNames.SCALE.code).asInt
                } else {
                    null
                }


        override fun isValid(): Boolean = logicalType != null && precision != null


        override val fieldsAsStrings: List<String?> = super.fieldsAsStrings + listOf(
                if (precision != null) """ "precision": $precision """ else null,
                if (scale != null) """ "scale": $scale """ else null
        )

    }

    class AvroSchemaItemLogicTypeStringUuid(private val jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean {
            return (logicalType != null)
        }
    }

    class AvroSchemaItemLogicTypeIntDate(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean {
            return (logicalType != null)
        }
    }

    class AvroSchemaItemLogicTypeIntTimeMillis(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean {
            return (logicalType != null)
        }
    }

    class AvroSchemaItemLogicTypeIntTimeMicros(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean {
            return (logicalType != null)
        }
    }

    class AvroSchemaItemLogicTypeIntTimestampMillis(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean {
            return (logicalType != null)
        }
    }

    class AvroSchemaItemLogicTypeIntTimestampMicros(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean {
            return (logicalType != null)
        }
    }

    class AvroSchemaItemLogicTypeFixedDuration(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        val size: Int? =
                if (jsonObject.has(FieldNames.SIZE.code)) {
                    jsonObject.get(FieldNames.SIZE.code).asInt
                } else {
                    null
                }

        val name: String? =
                if (jsonObject.has(FieldNames.NAME.code)) {
                    jsonObject.get(FieldNames.NAME.code).asString
                } else {
                    null
                }

        override fun isValid(): Boolean {
            return (logicalType != null && size != null && name != null)
        }

        override val fieldsAsStrings: List<String?> = super.fieldsAsStrings + listOf(
                if (name != null) """ "name": "$name" """ else null,
                if (size != null) """ "size": $size """ else null
        )
    }
}
