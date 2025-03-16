package com.mkarshnas6.karenstudio.karengold

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import com.mkarshnas6.karenstudio.karengold.databinding.ActivityFullScreenBinding

@Suppress("UNREACHABLE_CODE")
class FullScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the status bar and navigation bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.splash_gold)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.splash_gold)

//        show animation ...............
        binding.imageView.animate().alpha(1f).setDuration(1500).start()
        binding.textView.animate().alpha(1f).setDuration(2500).start()
        binding.textView2.animate().alpha(1f).setDuration(2500).start()

        if (!isInternetAvailable()) {
            showNoInternetDialog()
        } else {

            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 2600)
        }

        // Enable full screen mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 and above, use WindowInsetsController
            val windowInsetsController = window.insetsController
            windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            windowInsetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            // For Android versions below Android 11, use View.SYSTEM_UI_FLAG_FULLSCREEN and others
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun showNoInternetDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_no_internet_dialog, null)

        val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val retryButton = dialogView.findViewById<Button>(R.id.retryButton)
        val exitButton = dialogView.findViewById<Button>(R.id.exitButton)

        retryButton.setOnClickListener {
            alertDialog.dismiss()
            if (isInternetAvailable()) {

                Handler().postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1000)

                Toast.makeText(this, "اتصال برقرار شد!", Toast.LENGTH_SHORT).show()
            } else {
                showNoInternetDialog()
            }
        }

        exitButton.setOnClickListener {
            finish()
        }

        alertDialog.show()

        // Set the dialog size to be small (80% width and 25% height of the screen)
        val window = alertDialog.window
        window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(), // 80% width of screen
            (resources.displayMetrics.heightPixels * 0.35).toInt() // 25% height of screen
        )
        window?.setGravity(Gravity.CENTER) // Center the dialog on the screen
    }

}
