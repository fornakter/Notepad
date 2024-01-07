package com.app.notepad.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavController,
    onEvents: (NotesEvents) -> Unit
){
    Scaffold(
        floatingActionButton = {
        FloatingActionButton(onClick = {
            NotesEvents.SaveNote(
                title = state.title.value,
                description = state.desctription.value
            )
            navController.popBackStack()
        }) {

            Icon(imageVector = Icons.Rounded.Check,
                contentDescription = "ok"
            )
        }
        }
    ) {
        paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize())
        {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.title.value,
                onValueChange = {
                        state.title.value = it },
                placeholder = {
                    Text(text = "Title")
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.desctription.value,
                onValueChange = {
                    state.desctription.value = it },
                placeholder = {
                    Text(text = "Description")
                }
            )
        }
    }
}