package com.example.notelistapp.database

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notelistapp.model.Note
import kotlin.concurrent.Volatile
@Database(entities = [Note::class], version = 1, exportSchema = false)

//@Database(entities = [Note::class], version = 1)
//abstract class NoteDatabase() :RoomDatabase(), Parcelable {
abstract class NoteDatabase() :RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(Lock)
        {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
        }
    }


