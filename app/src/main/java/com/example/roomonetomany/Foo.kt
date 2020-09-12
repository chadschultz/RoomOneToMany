package com.example.roomonetomany

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// Need to use @JvmOverloads because we're using @Ignore. See
// https://issuetracker.google.com/issues/70762008
@Entity
data class Foo @JvmOverloads constructor (
    @PrimaryKey val id: String,
    @Ignore var bars: List<String> = mutableListOf()
) {
    override fun toString(): String {
        return "Foo (id: $id, bars: [${bars.joinToString()}])"
    }
}
