package filip.engineer.dockme

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.BatteryManager
import android.util.Log

const val TAG = "ChargingReceiver"

class ChargingReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Fetch the battery status
        Log.d(TAG, "received receiver")
        if (Intent.ACTION_POWER_CONNECTED == intent.action) {
            Log.d(TAG, "power")
            val batteryStatus: Intent? = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1

            // Check if it’s wirelessly charging
            val isWirelessCharging = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS

            // Check if the device is in landscape orientation
            val isLandscape = isDeviceInLandscape(context)

            Log.d(TAG, "isWireless: $isWirelessCharging isLandscape: $isLandscape")

            if (isLandscape) {
                // Log the event or perform the action
                Log.d(TAG, "Wireless charging in landscape, launching app.")

                val launchIntent = Intent(context, OverlayActivity::class.java)
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(launchIntent)
            }
        }
    }

    private fun isDeviceInLandscape(context: Context): Boolean {
        val orientation = context.resources.configuration.orientation
        return orientation == Configuration.ORIENTATION_LANDSCAPE
    }
}