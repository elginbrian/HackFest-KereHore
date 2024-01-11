package com.hackfest.KereHore

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hackfest.KereHore.database.HF_DatabaseDao
import com.hackfest.KereHore.database.HF_DatabaseDao2
//import androidx.hilt.navigation.compose.hiltViewModel
import com.hackfest.KereHore.navigation.HF_NavHost
import com.hackfest.KereHore.repository.HF_Repository
import com.hackfest.KereHore.repository.HF_Repository2
import com.hackfest.KereHore.screen.HF_ViewModel
import com.hackfest.KereHore.screen.HF_ViewModel2
import com.hackfest.KereHore.ui.theme.KereHoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KereHoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val HF_ViewModel: HF_ViewModel by viewModels()
                    val HF_ViewModel2: HF_ViewModel2 by viewModels()
                    HF_KereHore(content = { HF_NavHost(HF_ViewModel = HF_ViewModel, HF_ViewModel2 = HF_ViewModel2) })
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HF_KereHore(content: @Composable () -> Unit){
    KereHoreTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun HF_KereHorePreview() {
    KereHoreTheme {
        //HF_KereHore(content = { HF_NavHost(
            //HF_ViewModel  = HF_ViewModel(HF_Repository(HF_DatabaseDao)),
            //HF_ViewModel2 = HF_ViewModel2(HF_Repository2(HF_DatabaseDao2))
        //) })
    }
}

//val viewModel = HF_ViewModel.pocketList.collectAsState().value
//val viewModel2 = HF_ViewModel2