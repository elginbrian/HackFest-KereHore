package com.hackfest.KereHore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.identity.Identity
import com.hackfest.KereHore.database.HF_DatabaseDao
import com.hackfest.KereHore.database.HF_DatabaseDao2
//import androidx.hilt.navigation.compose.hiltViewModel
import com.hackfest.KereHore.navigation.HF_NavHost
import com.hackfest.KereHore.repository.HF_Repository
import com.hackfest.KereHore.repository.HF_Repository2
import com.hackfest.KereHore.screen.HF_SignInViewModel
import com.hackfest.KereHore.screen.HF_SplashScreenViewModel
import com.hackfest.KereHore.screen.HF_ViewModel
import com.hackfest.KereHore.screen.HF_ViewModel2
import com.hackfest.KereHore.signIn.HF_GoogleAuthUIClient
import com.hackfest.KereHore.ui.theme.KereHoreTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val HF_SplashScreenViewModel by viewModels<HF_SplashScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                !HF_SplashScreenViewModel.isReady.value
            }
        }
        setContent {
            KereHoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val HF_ViewModel: HF_ViewModel by viewModels()
                    val HF_ViewModel2: HF_ViewModel2 by viewModels()
                    val HF_SignInViewModel: HF_SignInViewModel by viewModels()
                    val appContext = applicationContext
                    //val HF_SignInViewModels = viewModel<HF_SignInViewModel>()
                    //val state by HF_SignInViewModels.state.collectAsStateWithLifecycle()

                    HF_KereHore(content = { HF_NavHost(
                        HF_ViewModel = HF_ViewModel,
                        HF_ViewModel2 = HF_ViewModel2,
                        HF_SignInViewModel = HF_SignInViewModel,
                        appContext = appContext
                    ) })
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