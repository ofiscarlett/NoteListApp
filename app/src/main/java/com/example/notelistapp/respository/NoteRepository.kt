package com.example.notelistapp.respository

import androidx.room.Query
import com.example.notelistapp.model.Note
import com.example.notelistapp.database.NoteDatabase


//all container keep data
class NoteRepository(private val db:NoteDatabase) {
    //insert, delete function
    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNotes(query)
}