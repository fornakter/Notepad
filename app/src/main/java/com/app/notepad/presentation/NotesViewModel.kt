package com.app.notepad.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.notepad.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.app.notepad.data.Note
import kotlinx.coroutines.flow.update

class NotesViewModel(
    private val dao: NoteDao
): ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    private val notes = isSortedByDateAdded.flatMapLatest { sort ->
        if (sort) {
            dao.getNotesOrderByDateAdded()
        }else{
            dao.getNotesOrderByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(NoteState())
    val state =
        combine(_state, isSortedByDateAdded, notes){
            state, isSortedByDateAdded, notes ->
            state.copy(
                notes = notes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), NoteState())

    fun onEvent(events: NotesEvents){
        when (events){
            is NotesEvents.DeleteNote ->
            {
                viewModelScope.launch { dao.deleteNote(events.note) }
            }

            is NotesEvents.SaveNote -> {
                val note = Note(
                    title = state.value.title.value,
                    description = state.value.title.value,
                    dateAdded = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertNote(note)
                }

                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        desctription = mutableStateOf("")
                    )
                }
            }

            NotesEvents.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
        }
    }

}