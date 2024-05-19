package com.example.thenewsapp.repository

import androidx.room.Query
import androidx.room.util.query
import com.example.thenewsapp.database.NoteDatabase
import com.example.thenewsapp.model.Note

class NoteRepository (private val db:NoteDatabase){
    suspend fun insertNote(note:Note) = db.getNoteDao().inserNote(note)
    suspend fun deleteNote(note:Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note:Note) = db.getNoteDao().updateNote(note)

    fun getAllnotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)
}