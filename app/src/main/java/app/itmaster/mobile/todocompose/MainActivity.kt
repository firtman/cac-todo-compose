@file:OptIn(ExperimentalMaterial3Api::class)

package app.itmaster.mobile.todocompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Delete
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Scroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.itmaster.mobile.todocompose.ui.theme.ToDoComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 360)
@Composable
fun App() {
    // dummy data = datos de prueba mientras estás programando
    var collection by remember { mutableStateOf(listOf<String>("a1", "a2", "a3", "a4", "a", "a", "a", "a", "a", "a", "a", "a", )) }
    var itemToAdd by remember { mutableStateOf("") }

    ToDoComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
//                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)) {
                TextField(value = itemToAdd, onValueChange = {itemToAdd = it},
                        modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    collection = collection + itemToAdd
                    itemToAdd = ""
                }) {
                    Text("Add")
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (collection.isEmpty()) {
                    Text("Nothing to do yet")
                }

                LazyColumn {
                    items(collection.size, itemContent = { index ->
                        TodoItem(item = collection[index], onItemDeleted = {
                            collection = collection - it
                        })
                    })
                }


// ineficiente porque "emite" todos los TodoItem que hay en la collección
//                for (item in collection) {
//                    TodoItem(item = item)
//                }
            }

        }
    }
}

@Composable
fun TodoItem(item: String, onItemDeleted: (String)->Unit) {
    var isChecked by remember { mutableStateOf(false) }
    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = isChecked, onCheckedChange = { isChecked = it })
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {
            onItemDeleted(item)
        }) {
            Icon(imageVector = Icons.TwoTone.Delete, contentDescription = "Delete",
                modifier = Modifier.padding(PaddingValues(end=8.dp)))
        }

    }
}
