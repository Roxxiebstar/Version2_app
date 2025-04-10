package com.example.version2_app
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "user")
data class User (
    @PrimaryKey val id: Int = 1, // Siempre será 1 porque es un solo usuario
    val name: String
)