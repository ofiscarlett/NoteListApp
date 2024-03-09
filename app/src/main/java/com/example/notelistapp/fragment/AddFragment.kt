package com.example.notelistapp.fragment

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
import com.example.notelistapp.MainActivity
//import com.example.notelistapp.databinding.FragmentHomeBinding
import com.example.notelistapp.model.Note
import com.example.notelistapp.viewmodel.NoteViewModel
import com.example.notelistapp.R
import com.example.notelistapp.databinding.FragmentAddBinding


// see the title and description in the add
class AddFragment : Fragment(R.layout.fragment_add), MenuProvider {
    //set up action button
    private var addNoteBinding: FragmentAddBinding? = null
    private val binding get() = addNoteBinding

    //declare note view
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addNoteView: View



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel =(activity as MainActivity).noteViewModel
        addNoteView = view
    }

    private fun saveNote(view: View){
        val noteTitle = binding?.addNoteTitle?.text.toString().trim()
        val noteDesc = binding?.addNoteDesc?.text.toString().trim()

        if (noteTitle.isNotEmpty()){
            val note = Note(0, noteTitle, noteDesc)
            notesViewModel.addNote(note)

            //save note title and back to home
            Toast.makeText(addNoteView.context, "New note be saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        }else{
            Toast.makeText(addNoteView.context, "Please provide Note tittle", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_newnote, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu ->{
                saveNote(addNoteView)
                true
            }
            else ->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }

}