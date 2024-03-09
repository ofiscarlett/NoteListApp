package com.example.notelistapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notelistapp.MainActivity
//import com.example.notelistapp.R
//import com.example.notelistapp.databinding.FragmentEditBinding
import com.example.notelistapp.model.Note
import com.example.notelistapp.viewmodel.NoteViewModel
import com.example.notelistapp.R
import com.example.notelistapp.databinding.FragmentEditBinding

//extend lay the fragment, delete use menu sp give provider
class EditFragment : Fragment(R.layout.fragment_edit), MenuProvider {
    //save update note and delete note from db
    private var editNoteBinding: FragmentEditBinding? =null
    private val binding get() = editNoteBinding !!

    //decleara view model
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note
    //nav args
    private val args: EditFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // initial data binding
        editNoteBinding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        notesViewModel =(activity as MainActivity).noteViewModel
        currentNote = args.note!!
        //show note des and title
        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        binding.editNoteFab.setOnClickListener{
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()
            if(noteTitle.isNotEmpty()){
                val note = Note(currentNote.id, noteTitle, noteDesc)
                notesViewModel.updateNote(note)
                //after update, back to homepage
                view.findNavController().popBackStack(R.id.homeFragment, false)
            }else {
                Toast.makeText(context, "Please provide note title", Toast.LENGTH_SHORT).show()
            }
        }


    }
    //delete function, click delete button
    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            //dialog function
            setTitle("Note delete.")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){
                _,_->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu ->{
                deleteNote()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}