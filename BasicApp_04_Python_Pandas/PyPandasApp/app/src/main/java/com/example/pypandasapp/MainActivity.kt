package com.example.pypandasapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.pypandasapp.ui.theme.PyPandasAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PyPandasAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CsvAnalyser(
                        context = this@MainActivity,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

private const val LOGTAG = "PyPandasApp"

@Composable
fun CsvAnalyser(context: android.content.Context, modifier: Modifier = Modifier) {
    if (!Python.isStarted()) {
        Python.start(AndroidPlatform(context))
    }
    CsvAnalyzerScreen(modifier = modifier)
}

@Composable
fun CsvAnalyzerScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var result by remember { mutableStateOf("No file selected!") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val csvContent = inputStream?.bufferedReader().use { render ->
                    render?.readText()
                } ?: ""
                val py = Python.getInstance()
                // The module name must be the same as the filename, in this case, 'csv_analyzer.py'.
                val pyModule = py.getModule("csv_analyzer")
                // def table(csv_content: {read_csv, StringIO }): ...
                val pyReturn = pyModule.callAttr("table", csvContent).toString()
                // the first 15 rows of the dataframe in text format
                result = pyReturn
                Log.i(LOGTAG, "Table loaded successfully!")
            } catch (e: Exception) {
                Log.e(LOGTAG, "Error processing the file: ${e.message}", e)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { launcher.launch("text/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select CSV file")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result (Pandas)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        val verticalScrollState = rememberScrollState()
        val horizontalScrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(verticalScrollState)
                .horizontalScroll(horizontalScrollState)
                .padding(8.dp)
        ) {
            Text(
                text = result,
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
