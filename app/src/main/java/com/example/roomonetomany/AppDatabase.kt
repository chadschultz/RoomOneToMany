package com.example.roomonetomany

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Foo::class, Bar::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fooDao(): FooDao
}