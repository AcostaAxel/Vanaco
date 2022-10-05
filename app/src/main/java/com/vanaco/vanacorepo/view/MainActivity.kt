package com.vanaco.vanacorepo.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vanaco.vanacorepo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread.sleep(650)
        screenSplash.setKeepOnScreenCondition { false }

        binding.btnWall.setOnClickListener {
            startActivity(Intent(this, WallpaperActivity::class.java))
        }

        binding.btnIG.setOnClickListener {
            val instagram =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/vanacoo"))
            startActivity(instagram)
        }

        binding.btnYT.setOnClickListener {
            val youtube = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/VANACO"))
            startActivity(youtube)
        }

        binding.btnMerch.setOnClickListener {
            val merch = Intent(Intent.ACTION_VIEW, Uri.parse("https://vanaco.flashcookie.com/"))
            startActivity(merch)
        }

        binding.btnMail.setOnClickListener {
            val mail = Intent(Intent.ACTION_SENDTO)
            mail.setData(Uri.parse("mailto: vanaco@hotmail.com"))
            startActivity(Intent.createChooser(mail, "Mail por consultas"))
        }
    }
}