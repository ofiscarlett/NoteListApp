package com.example.notelistapp.fragment

import android.os.Bundle
//import android.provider.ContactsContract.CommonDataKinds.Note
//import right note model is important
import com.example.notelistapp.model.Note

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notelistapp.MainActivity
//import com.example.notelistapp.R
import com.example.notelistapp.adapter.NoteAdapter
//import com.example.notelistapp.databinding.FragmentHomeBinding
import com.example.notelistapp.viewmodel.NoteViewModel
import com.example.notelistapp.R
import com.example.notelistapp.databinding.FragmentHomeBinding

//extend fragment layout
class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {
    //data binding and fragment
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!
    //define view model and adapter
    private lateinit var noteViewModel: NoteViewModel
    private  lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
       homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        //initial note view model
        noteViewModel =(activity as MainActivity).noteViewModel
        //put recycle view
        setupHomeRecyclerView()
        //when click and it will lead to add note fragment correct
        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())
        }

    }
    //update
    private fun updateUI(note: List<Note>?){
        if(note != null){
            //if note is not emtpy, the emptyimage will gone
            if(note.isNotEmpty()){
                binding.emptyNotesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            }else {
                binding.emptyNotesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }
    private fun setupHomeRecyclerView(){
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        activity?.let {
            noteViewModel.getAllNotes().observe(viewLifecycleOwner) { note->
                noteAdapter.differ.submitList(note)
                updateUI(note)

            }
        }

    }

    private fun searchNote(query: String?){
        val searchQuery = "%$query"

        noteViewModel.searchNote(searchQuery).observe(this){list ->
            noteAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //user continu keying
        if(newText != null) {
            searchNote(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}