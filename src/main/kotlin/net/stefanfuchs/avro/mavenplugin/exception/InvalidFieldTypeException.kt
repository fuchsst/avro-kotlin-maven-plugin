package net.stefanfuchs.avro.mavenplugin.exception

import java.lang.Exception

class InvalidFieldTypeException(override val message:String):Exception(message)
