package com.example.thenewsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thenewsapp.MainActivity
import com.example.thenewsapp.R
import com.example.thenewsapp.databinding.FragmentAddNoteBinding
import com.example.thenewsapp.databinding.FragmentEdditNoteBinding
import com.example.thenewsapp.model.Note
import com.example.thenewsapp.viewmodel.NoteViewModel


class EdditNoteFragment : Fragment(R.layout.fragment_eddit_note),MenuProvider {


    private var editNoteBinding: FragmentEdditNoteBinding? =null
    private val binding get() = editNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var  currentNote: Note

    private val args: EdditNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding = FragmentEdditNoteBinding.inflate(inflater,container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        binding.editNoteFab.setOnClickListener{
            val noteTite = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteTitle.text.toString().trim()
            if (noteTite.isNotEmpty()){
                val note = Note(currentNote.id,noteTite,noteDesc)
                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                Toast.makeText(context,"Please enter note title",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun  deleteNote() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete") { _, _ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context, "Not deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
            }.create().show()

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
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



