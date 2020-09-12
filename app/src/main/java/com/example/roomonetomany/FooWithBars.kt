package com.example.roomonetomany

import androidx.room.Embedded
import androidx.room.Relation

data class FooWithBars (
    @Embedded val foo: Foo,
    @Relation(
        parentColumn = "id",
        entityColumn = "fooId"
    )
    val bars: List<Bar>
) {
    fun toFoo(): Foo {
        foo.bars = bars.map { it.barId }
        return foo
    }
}
