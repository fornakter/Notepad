package com.app.notepad.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.notepad.data.Note

@Composable
fun NoteScreen(
    state: NoteState,
    navController: NavController,
    onEvents: (NotesEvents) -> Unit
){
    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp), 
                verticalAlignment = Alignment.CenterVertically) {
                
                Text(text = "Title",
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold)
                
                IconButton(onClick = { onEvents(NotesEvents.SortNotes) }) {
                    Icon(imageVector = Icons.Rounded.Sort, contentDescription = "sort"
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                state.title.value = ""
                state.desctription.value = ""
                navController.navigate("AddNoteScreen")
            }) {   
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "add")
            }
        }
    ) {paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(state.notes.size){
                index -> Noteitem(state = state, index = index, onEvents = onEvents)
            }
        }
        
    }
}

@Composable
fun Noteitem(    
    state: NoteState,
    index: Int,
    onEvents: (NotesEvents) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primaryContainer)
        .padding(12.dp)) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(text = state.notes[index].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = state.notes[index].description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
        }

        IconButton(onClick = { onEvents(NotesEvents.DeleteNote(state.notes[index]))
        }) {

            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "del",
                modifier = Modifier.size(35.dp))

        }
    }    
}



