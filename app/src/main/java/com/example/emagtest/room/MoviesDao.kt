package com.example.emagtest.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.emagtest.models.MoviesModel

@Dao
interface MoviesDao {
    @Query("Select * FROM movieEntity")
    suspend fun getMovies(): List<MoviesModel>

    @Insert
   suspend fun insertMovie(vararg movie: MoviesModel)

    @Delete
    suspend fun deleteMovie(movie: MoviesModel)

    @Update
    suspend fun updateMovie(vararg movie: MoviesModel)

    @Query("SELECT count(id) FROM movieEntity")
    suspend fun numberOfItemsInDB() : Int

    @Query("SELECT EXISTS(SELECT * FROM movieEntity WHERE id = :id)")
    suspend fun elementExists(id: Int) : Boolean
}