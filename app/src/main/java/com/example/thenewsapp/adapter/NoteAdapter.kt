package com.example.thenewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thenewsapp.databinding.NoteLayoutBinding
import com.example.thenewsapp.fragments.HomeFragmentDirections
import com.example.thenewsapp.model.Note


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){


    class NoteViewHolder(val itemBinding: NoteLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteDesc == newItem.noteDesc &&
                    oldItem.noteTitle == newItem.noteTitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val  currenNote = differ.currentList[position]
        holder.itemBinding.noteTitle.text = currenNote.noteTitle
        holder.itemBinding.noteDesc.text = currenNote.noteDesc

        holder.itemView.setOnClickListener{
            val  direction = HomeFragmentDirections.actionHomeFragmentToEdditNoteFragment(currenNote)
            it.findNavController().navigate(direction)


        }
    }

}