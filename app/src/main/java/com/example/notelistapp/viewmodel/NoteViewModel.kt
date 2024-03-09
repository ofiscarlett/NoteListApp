package com.example.notelistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notelistapp.model.Note
import com.example.notelistapp.respository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val noteRepository: NoteRepository): AndroidViewModel(app)
{
    // 5 functions co routin scope
    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }
    fun getAllNotes() = noteRepository.getAllNotes()
    fun searchNote(query: String?) =
        noteRepository.searchNote(query)
}