package com.kshitijpatil.hiltdemo.data.local

import androidx.room.*

@Entity(tableName = "blogs")
data class BlogCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    val title: String,
    val body: String,
    val category: String,
    val image: String
)

@Database(entities = [BlogCacheEntity::class], version = 1)
abstract class BlogDatabase : RoomDatabase() {
    abstract val blogDao: BlogDao

    companion object {
        val DATABASE_NAME = "blog_db"
    }
}

@Dao
interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blogEntity: BlogCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(blogs: List<BlogCacheEntity>)

    @Query("SELECT * FROM blogs")
    suspend fun getBlogs(): List<BlogCacheEntity>
}