package com.berkeerkec.dailynews.db

import androidx.room.TypeConverter
import com.berkeerkec.dailynews.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source : Source) : String{
        return source.name
    }

    @TypeConverter
    fun toSource(name : String) : Source{
        return Source(name,name)
    }
}