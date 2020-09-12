package com.example.roomonetomany

import androidx.room.*

// Normally the Room DAOs are interfaces, but we need to make it an abstract class to have protected
// methods. We don't want to expose use of FooWithBars outside the DAO. Its use is strictly limited
// to database operations.
@Dao
abstract class FooDao {

    // It's nice to have Room work with LiveData, but I can't just return LiveData,
    // due to running my own code instead of just using @Query
    fun getAll(): List<Foo> = allWithBars().map { it.toFoo() }

    // Pairing two entities (Foo and Bar) requires two database queries, so Google recommends
    // use of @Transaction. https://developer.android.com/training/data-storage/room/relationships#kotlin
    @Transaction
    @Query("SELECT * FROM foo")
    protected abstract fun allWithBars(): List<FooWithBars>

    fun getFoo(id: String) = getFooWithBars(id).toFoo()

    @Transaction
    @Query("SELECT * FROM foo WHERE id = :id")
    protected abstract fun getFooWithBars(id: String): FooWithBars

    // Without making this "open", I get this error:
    // "Method annotated with @Transaction must not be private, final, or abstract.
    // It can be abstract only if the method is also annotated with @Query"
    @Transaction
    open fun insertAll(all: List<Foo>) {
        all.forEach {
            insert(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun internalInsert(foo: Foo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun internalInsertBars(bars: List<Bar>)

    @Transaction
    open fun insert(foo: Foo) {
        internalInsert(foo)
        internalInsertBars(foo.bars.map {
            Bar(barId = it, fooId = foo.id)
        })
    }

}