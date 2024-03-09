package com.example.notelistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notelistapp.database.NoteDatabase
import com.example.notelistapp.respository.NoteRepository
import com.example.notelistapp.viewmodel.NoteViewFactory
import com.example.notelistapp.viewmodel.NoteViewModel
import com.example.notelistapp.R

class MainActivity : AppCompatActivity() {

    //set up view model created
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    //function set up view model
    private fun setupViewModel(){
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewFactory(application, noteRepository)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}