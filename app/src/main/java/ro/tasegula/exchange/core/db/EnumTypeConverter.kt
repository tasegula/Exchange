package ro.tasegula.exchange.core.db

import androidx.room.TypeConverter

open class EnumTypeConverter<T : Enum<T>>(private val enumType: Class<T>,
                                          private val default: T? = null) {

    @TypeConverter
    fun toEnum(value: String): T {
        return try {
            java.lang.Enum.valueOf<T>(enumType, value)
        } catch (e: Exception) {
            default ?: throw e
        }
    }

    @TypeConverter
    fun toPrimitive(value: T): String {
        return value.name
    }
}

open class NullableEnumTypeConverter<T : Enum<T>>(private val enumType: Class<T>) {

    @TypeConverter
    fun toEnum(value: String?): T? {
        return if (value.isNullOrEmpty()) null else java.lang.Enum.valueOf<T>(enumType, value)
    }

    @TypeConverter
    fun toPrimitive(value: T?): String? {
        return value?.name
    }
}
