package com.example.roomonetomany

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bar (
    @PrimaryKey val barId: String,
    val fooId: String
)