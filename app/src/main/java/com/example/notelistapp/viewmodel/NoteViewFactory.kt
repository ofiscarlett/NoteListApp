package com.example.notelistapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notelistapp.model.Note
import com.example.notelistapp.respository.NoteRepository

//view model factory is a class that instantiate and return view model simple
class NoteViewFactory (val app:Application, private val noteRepository: NoteRepository): ViewModelProvider.Factory{
    //create an instance of the V model,
    // override the create method from the view model provider factory
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        //new instance of the note view model by passing the application and note
        return NoteViewModel(app, noteRepository) as T
    }
}