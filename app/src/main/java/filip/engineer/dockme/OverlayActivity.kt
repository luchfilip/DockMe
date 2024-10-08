package filip.engineer.dockme

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import filip.engineer.dockme.ui.theme.nunitoFamily
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class OverlayActivity : ComponentActivity() {

    private lateinit var chargingReceiver: ChargingReceiver

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver to avoid leaks
        unregisterReceiver(chargingReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemBars()

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)

        chargingReceiver = ChargingReceiver()
        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(chargingReceiver, filter)

        setContent {
            val configuration = LocalConfiguration.current

            var currentTime by remember { mutableStateOf(LocalTime.now()) }
            var currentQuote by remember { mutableStateOf(quotes.random()) }

            DisposableEffect(configuration) {
//                if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
//                    finish() // Close the activity if it's not in landscape
//                }
                onDispose { }
            }

            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                        fontSize = 160.sp,
                        color = Color(0xFFA8D5BA),
                        fontWeight = FontWeight.Black,
                        fontFamily = nunitoFamily,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentQuote,
                        fontSize = 24.sp,
                        color = Color(0xFFF4A9A8),
                        fontWeight = FontWeight.Medium,
                        fontFamily = nunitoFamily,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(60000)
                    currentTime = LocalTime.now()
                    currentQuote = quotes.random()
                }
            }
        }


    }

    private fun hideSystemBars() {
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        insetsController.hide(WindowInsetsCompat.Type.statusBars())
    }

}

val quotes = listOf(
    "\"Dream big, start small.\" Alex T.",
    "\"Failure is a step toward success.\" Jamie L.",
    "\"Life begins at the end of your comfort zone.\" Sam W.",
    "\"Take the risk or lose the chance.\" Riley K.",
    "\"Believe you can, and you're halfway there.\" Casey M.",
    "\"Work smart, not just hard.\" Jordan P.",
    "\"Your only limit is you.\" Taylor S.",
    "\"Stay curious and keep exploring.\" Morgan B.",
    "\"Every day is a new adventure.\" Avery D.",
    "\"Challenges are opportunities in disguise.\" Cameron F.",
    "\"Embrace the journey, not just the destination.\" Reese G.",
    "\"Courage is the key to great endeavors.\" Peyton H.",
    "\"Success is a series of small wins.\" Devon J.",
    "\"Don't wait; the time will never be just right.\" Sidney L.",
    "\"Learn from yesterday, live for today.\" Bailey N.",
    "\"Make today count.\" Quinn O.",
    "\"Turn your can'ts into cans.\" Robin P.",
    "\"Persistence guarantees that results are inevitable.\" Skyler R.",
    "\"Doubt kills more dreams than failure ever will.\" Drew S.",
    "\"Stay focused and never give up.\" Casey T.",
    "\"Opportunities don't happen; you create them.\" Jamie U.",
    "\"Your attitude determines your direction.\" Kendall V.",
    "\"Great things never come from comfort zones.\" Alex W.",
    "\"Believe in yourself and all that you are.\" Jordan X.",
    "\"Success doesn't come to you; you go to it.\" Taylor Y.",
    "\"If you can dream it, you can do it.\" Morgan Z.",
    "\"The best way out is always through.\" Avery A.",
    "\"Hard work beats talent when talent doesn't work hard.\" Cameron B.",
    "\"The journey of a thousand miles begins with one step.\" Reese C.",
    "\"You miss 100% of the shots you don't take.\" Peyton D.",
    "\"Aim for the moon. If you miss, you may hit a star.\" Devon E.",
    "\"Strive not to be a success, but rather to be of value.\" Sidney F.",
    "\"Don't watch the clock; do what it does. Keep going.\" Bailey G.",
    "\"Success is not final; failure is not fatal.\" Quinn H.",
    "\"The harder you work for something, the greater you'll feel when you achieve it.\" Robin I.",
    "\"Don't stop when you're tired. Stop when you're done.\" Skyler J.",
    "\"Wake up with determination. Go to bed with satisfaction.\" Drew K.",
    "\"Little things make big days.\" Casey L.",
    "\"It's going to be hard, but hard does not mean impossible.\" Jamie M.",
    "\"Don't wait for opportunity. Create it.\" Kendall N.",
    "\"Sometimes later becomes never. Do it now.\" Alex O.",
    "\"Great things take time.\" Jordan P.",
    "\"Dream it. Wish it. Do it.\" Taylor Q.",
    "\"Your limitation—it's only your imagination.\" Morgan R.",
    "\"Push yourself because no one else is going to do it for you.\" Avery S.",
    "\"Success doesn't just find you; you have to go out and get it.\" Cameron T.",
    "\"The key to success is to focus on goals, not obstacles.\" Reese U.",
    "\"Believe in your infinite potential.\" Peyton V.",
    "\"Don't limit your challenges. Challenge your limits.\" Devon W.",
    "\"Action is the foundational key to all success.\" Sidney X."
)