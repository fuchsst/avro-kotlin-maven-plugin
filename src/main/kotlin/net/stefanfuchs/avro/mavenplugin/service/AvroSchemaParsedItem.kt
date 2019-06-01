package net.stefanfuchs.avro.mavenplugin.service

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.stefanfuchs.avro.mavenplugin.model.FieldNames
import net.stefanfuchs.avro.mavenplugin.model.FieldType
import net.stefanfuchs.avro.mavenplugin.model.LocicalFieldType
import net.stefanfuchs.avro.mavenplugin.model.SortOrder


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
            val typeString = getTypeString(json).toLowerCase()
            return when (typeString) {
                FieldType.RECORD.code -> AvroSchemaItemRecord(json)
                FieldType.ENUM.code -> AvroSchemaItemEnum(json)
                FieldType.MAP.code -> AvroSchemaItemMap(json)
                FieldType.FIXED.code -> AvroSchemaItemFixed(json)
                else -> when (getLogicalTypeString(json)?.toLowerCase()) {
                    LocicalFieldType.DURATION.code -> AvroSchemaItemLogicTypeFixedDuration(json)
                    LocicalFieldType.DATE.code -> AvroSchemaItemLogicTypeIntDate(json)
                    LocicalFieldType.DECIMAL.code -> AvroSchemaItemLogicTypeBytesDecimal(json)
                    LocicalFieldType.TIMESTAMP_MICROSECONDS.code -> AvroSchemaItemLogicTypeIntTimestampMicros(json)
                    LocicalFieldType.TIMESTAMP_MILLISECONDS.code -> AvroSchemaItemLogicTypeIntTimestampMillis(json)
                    LocicalFieldType.TIME_MICROSECONDS.code -> AvroSchemaItemLogicTypeIntTimeMicros(json)
                    LocicalFieldType.TIME_MILLISECONDS.code -> AvroSchemaItemLogicTypeIntTimeMillis(json)
                    LocicalFieldType.UUID.code -> AvroSchemaItemLogicTypeStringUuid(json)
                    else -> null
                }
            }
        }

        fun hasTypeField(jsonObject: JsonObject) = jsonObject.has(FieldNames.TYPE.code)
        fun getTypeString(asJsonObject: JsonObject) =
                asJsonObject.get(FieldNames.TYPE.code).asString

        fun hasLogicalTypeField(jsonObject: JsonObject): Boolean = jsonObject.keySet().map { it.toLowerCase() }.contains(FieldNames.LOGICALTYPE.code)
        fun getLogicalTypeString(jsonObject: JsonObject): String {
            val jsonKey = jsonObject.keySet().find { it.toLowerCase() == FieldNames.LOGICALTYPE.code }
            return jsonObject[jsonKey].asString
        }

    }

    abstract fun isValid(): Boolean


    abstract class NamedAvroSchemaItem(val jsonObject: JsonObject) : AvroSchemaItem() {
        val type: AvroSchemaItem? by lazy {
            if (jsonObject.has(FieldNames.TYPE.code)) {
                val jsonElement = jsonObject.get(FieldNames.TYPE.code)
                parse(jsonElement)
            } else {
                null
            }
        }

        val name: String? by lazy {
            if (jsonObject.has(FieldNames.NAME.code)) {
                jsonObject.get(FieldNames.NAME.code).asString
            } else {
                null
            }
        }

        val doc: String? by lazy {
            if (jsonObject.has(FieldNames.DOC.code)) {
                jsonObject.get(FieldNames.DOC.code).asString
            } else {
                null
            }
        }

        val aliases: List<String>? by lazy {
            if (jsonObject.has(FieldNames.ALIASES.code)) {
                jsonObject.get(FieldNames.ALIASES.code)
                        .asJsonArray
                        .map { it.asString }
            } else {
                null
            }
        }

        abstract val fieldsAsStrings: List<String?>

        override fun toString(): String {
            return fieldsAsStrings
                    .filterNotNull()
                    .joinToString(",", "{ ", "}")
        }
    }


    class AvroSchemaItemRecord(jsonObject: JsonObject) : NamedAvroSchemaItem(jsonObject) {
        val namespace: String? by lazy {
            if (jsonObject.has(FieldNames.NAMESPACE.code)) {
                jsonObject.get(FieldNames.NAMESPACE.code).asString
            } else {
                null
            }
        }

        val fields: List<AvroSchemaItemRecordField>? by lazy {
            if (jsonObject.has(FieldNames.FIELDS.code)) {
                jsonObject.get(FieldNames.FIELDS.code)
                        .asJsonArray
                        .map { AvroSchemaItemRecordField(it.asJsonObject) }
            } else {
                null
            }
        }

        override fun isValid(): Boolean = name != null && namespace != null && fields != null

        override val fieldsAsStrings = listOf(
                if (namespace != null) """ "namespace": "$namespace" """ else null,
                if (name != null) """ "name": "$name" """ else null,
                if (type != null) """ "type": $type """ else null,
                if (aliases != null) """ "aliases": $aliases """ else null,
                if (doc != null) """ "doc": "$doc" """ else null,
                if (fields != null) """ "fields": $fields """ else null
        )

    }

    class AvroSchemaItemRecordField(jsonObject: JsonObject) : NamedAvroSchemaItem(jsonObject) {
        val logicalType: LocicalFieldType? by lazy {
            if (hasLogicalTypeField(jsonObject)) {
                val logicalTypeString = getLogicalTypeString(jsonObject)?.toLowerCase()
                LocicalFieldType.values().firstOrNull { it.code == logicalTypeString }
            } else {
                null
            }
        }

        val order: SortOrder? by lazy {
            if (jsonObject.has(FieldNames.SORTORDER.code)) {
                val jsonElement = jsonObject.get(FieldNames.SORTORDER.code)
                SortOrder.valueOf(jsonElement.asString.toUpperCase())
            } else {
                null
            }
        }


        val default: String? by lazy {
            if (jsonObject.has(FieldNames.DEFAULT.code)) {
                val jsonElement = jsonObject.get(FieldNames.DEFAULT.code)
                jsonElement.asString
            } else {
                null
            }
        }

        override fun isValid(): Boolean = name != null && type != null

        override val fieldsAsStrings = listOf(
                if (name != null) """ "name": "$name" """ else null,
                if (type != null) """ "type": $type """ else null,
                if (aliases != null) """ "aliases": $aliases """ else null,
                if (doc != null) """ "doc": "$doc" """ else null,
                if (default != null) """ "default": "$default" """ else null,
                if (order != null) """ "order": "$order" """ else null
        )
    }

    class AvroSchemaItemEnum(jsonObject: JsonObject) : NamedAvroSchemaItem(jsonObject) {
        val namespace: String? by lazy {
            if (jsonObject.has(FieldNames.NAMESPACE.code)) {
                jsonObject.get(FieldNames.NAMESPACE.code).asString
            } else {
                null
            }
        }

        val symbols: List<String>? by lazy {
            if (jsonObject.has(FieldNames.SYMBOLS.code)) {
                jsonObject.get(FieldNames.SYMBOLS.code)
                        .asJsonArray
                        .map { it.asString }
            } else {
                null
            }
        }

        val default: String? by lazy {
            if (jsonObject.has(FieldNames.DEFAULT.code)) {
                val jsonElement = jsonObject.get(FieldNames.DEFAULT.code)
                jsonElement.asString
            } else {
                null
            }
        }

        override fun isValid(): Boolean = name != null &&
                namespace != null &&
                symbols?.isNotEmpty() ?: false

        override val fieldsAsStrings = listOf(
                if (namespace != null) """ "namespace": "$namespace" """ else null,
                if (name != null) """ "name": "$name" """ else null,
                if (type != null) """ "type": $type """ else null,
                if (aliases != null) """ "aliases": $aliases """ else null,
                if (doc != null) """ "doc": "$doc" """ else null,
                if (default != null) """ "default": "$default" """ else null,
                if (symbols != null) """ "symbols": $symbols """ else null
        )
    }

    class AvroSchemaItemArray(jsonObject: JsonObject) : AvroSchemaItem() {
        val type: AvroSchemaItem? by lazy {
            if (jsonObject.has(FieldNames.TYPE.code)) {
                val jsonElement = jsonObject.get(FieldNames.TYPE.code)
                parse(jsonElement)
            } else {
                null
            }
        }

        val items: AvroSchemaItem? by lazy {
            if (jsonObject.has(FieldNames.ITEMS.code)) {
                parse(jsonObject.get(FieldNames.ITEMS.code))
            } else {
                null
            }
        }

        override fun isValid(): Boolean = items != null

        override fun toString(): String {
            return listOfNotNull(
                    if (type != null) """ "type": $type """ else null,
                    if (items != null) """ "items": $items """ else null
            )
                    .joinToString(",", "{ ", "}")
        }
    }

    class AvroSchemaItemMap(jsonObject: JsonObject) : AvroSchemaItem() {
        val type: AvroSchemaItem? by lazy {
            if (jsonObject.has(FieldNames.TYPE.code)) {
                val jsonElement = jsonObject.get(FieldNames.TYPE.code)
                parse(jsonElement)
            } else {
                null
            }
        }

        val values: AvroSchemaItem? by lazy {
            if (jsonObject.has(FieldNames.VALUES.code)) {
                parse(jsonObject.get(FieldNames.VALUES.code))
            } else {
                null
            }
        }

        override fun isValid(): Boolean = values != null

        override fun toString(): String {
            return listOfNotNull(
                    if (type != null) """ "type": $type """ else null,
                    if (values != null) """ "items": $values """ else null
            )
                    .joinToString(",", "{ ", "}")
        }
    }

    class AvroSchemaItemUnion(val jsonArray: JsonArray) : AvroSchemaItem() {
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

        override fun toString(): String {
            return listOfNotNull(
                    if (isNullable) "null" else null,
                    if (types != null) types!!.map { """"$it """" }.joinToString { "," } else null
            )
                    .joinToString(",", "[ ", "]")
        }
    }

    class AvroSchemaItemFixed(jsonObject: JsonObject) : NamedAvroSchemaItem(jsonObject) {
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

        override val fieldsAsStrings = listOf(
                if (name != null) """ "name": "$name" """ else null,
                if (type != null) """ "type": $type """ else null,
                if (aliases != null) """ "aliases": $aliases """ else null,
                if (doc != null) """ "doc": "$doc" """ else null,
                if (size != null) """ "default": "$size" """ else null
        )
    }

    data class AvroSchemaItemPrimitive(val rawString: String) : AvroSchemaItem() {
        val fieldType: FieldType? by lazy {
            FieldType.values().find { rawString.toLowerCase() == it.code }
        }

        override fun isValid(): Boolean = true
        fun isKnownFieldType(): Boolean = fieldType != null

        override fun toString(): String {
            return """"${if (isKnownFieldType()) fieldType!!.code else rawString}""""
        }
    }


    abstract class AbstractAvroSchemaItemLogicType(jsonObject: JsonObject) : AvroSchemaItem() {
        val type: AvroSchemaItem? by lazy {
            if (jsonObject.has(FieldNames.TYPE.code)) {
                val jsonElement = jsonObject.get(FieldNames.TYPE.code)
                parse(jsonElement)
            } else {
                null
            }
        }

        val logicalType: LocicalFieldType? by lazy {
            if (jsonObject.has(FieldNames.LOGICALTYPE.code)) {
                val logicTypeString = jsonObject.get(FieldNames.LOGICALTYPE.code).asString
                LocicalFieldType.values().find { it.code == logicTypeString }
            } else {
                null
            }
        }

        open val fieldsAsStrings: List<String?> = listOf(
                if (type != null) """ "type": $type """ else null,
                if (logicalType != null) """ "logicalType": $logicalType """ else null
        )

        override fun toString(): String {
            return fieldsAsStrings
                    .filterNotNull()
                    .joinToString(",", "{ ", "}")


        }
    }

    data class AvroSchemaItemLogicTypeBytesDecimal(private val jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        val precision: Int? by lazy {
            if (jsonObject.has(FieldNames.PRECISION.code)) {
                jsonObject.get(FieldNames.PRECISION.code).asInt
            } else {
                null
            }
        }

        val scale: Int? by lazy {
            if (jsonObject.has(FieldNames.SCALE.code)) {
                jsonObject.get(FieldNames.SCALE.code).asInt
            } else {
                null
            }
        }

        override fun isValid(): Boolean = precision != null


        override val fieldsAsStrings: List<String?> = super.fieldsAsStrings + listOf(
                if (precision != null) """ "precision": $precision """ else null,
                if (scale != null) """ "scale": $scale """ else null
        )

    }

    data class AvroSchemaItemLogicTypeStringUuid(private val jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean = true
    }

    class AvroSchemaItemLogicTypeIntDate(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean = true
    }

    class AvroSchemaItemLogicTypeIntTimeMillis(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean = true
    }

    class AvroSchemaItemLogicTypeIntTimeMicros(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean = true
    }

    class AvroSchemaItemLogicTypeIntTimestampMillis(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean = true
    }

    class AvroSchemaItemLogicTypeIntTimestampMicros(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        override fun isValid(): Boolean = true
    }

    class AvroSchemaItemLogicTypeFixedDuration(jsonObject: JsonObject) : AbstractAvroSchemaItemLogicType(jsonObject) {
        val size: Int? by lazy {
            if (jsonObject.has(FieldNames.SIZE.code)) {
                jsonObject.get(FieldNames.SIZE.code).asInt
            } else {
                null
            }
        }
        val name: String? by lazy {
            if (jsonObject.has(FieldNames.NAME.code)) {
                jsonObject.get(FieldNames.NAME.code).asString
            } else {
                null
            }
        }

        override fun isValid(): Boolean = true

        override val fieldsAsStrings: List<String?> = super.fieldsAsStrings + listOf(
                if (name != null) """ "name": "$name" """ else null,
                if (size != null) """ "size": $size """ else null
        )
    }
}
