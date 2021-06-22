package com.example.movieapp.models.numClass

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "type")
data class TypeClass(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var type:Int?=null
)