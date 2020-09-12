package com.example.roomonetomany

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val foos = mutableListOf(
            Foo("one foo", mutableListOf("cat", "hat")),
            Foo("two foo", mutableListOf("chat", "chapeau")),
            Foo("red foo", mutableListOf("gato", "sombrero")),
            Foo("blue foo", mutableListOf("katze", "hut")),
        )

        val database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "roomOneToMany"
        ).build()

        val dao = database.fooDao()

        lifecycleScope.launch(Dispatchers.IO) {
            dao.insertAll(foos)

            val dbFoos = dao.getAll()

            dbFoos.forEach {
                Log.e("RoomOneToMany", it.toString())
            }

            // Output:
            // Foo (id: one foo, bars: [cat, hat])
            // Foo (id: two foo, bars: [chat, chapeau])
            // Foo (id: red foo, bars: [gato, sombrero])
            // Foo (id: blue foo, bars: [katze, hut])
        }
    }
}