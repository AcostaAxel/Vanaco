package com.vanaco.vanacorepo.view

import android.app.WallpaperManager
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.vanaco.vanacorepo.databinding.ActivityFullWallpaperBinding
import kotlinx.coroutines.*
import java.io.File
import java.io.OutputStream
import java.net.URL
import java.util.*
import java.io.IOException as IOException1

class FullWallpaper : AppCompatActivity() {

    lateinit var binding: ActivityFullWallpaperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("userImage")
        val urlImage = URL(url)

        binding.ivFullWallpaper
        Glide.with(this).load(url).into(binding.ivFullWallpaper)

        binding.btnSetWallpaper.setOnClickListener {
            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                wallpaperManager.setBitmap(result.await())
                Toast.makeText(this@FullWallpaper, "Wallpaper Set", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDownload.setOnClickListener {
            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }
            GlobalScope.launch(Dispatchers.Main) {
                saveImage(result.await())
            }
        }
    }

    fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException1) {
            null
        }

    }

    private fun saveImage(image: Bitmap?) {
        val random1 = Random().nextInt(520985)
        val random2 = Random().nextInt(520985)
        val name = "AMOLED-${random1 + random2}"
        val data: OutputStream
        try {
            val resolver = contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "Vanaco"
            )
            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
            image?.compress(Bitmap.CompressFormat.JPEG, 100, data)
            Objects.requireNonNull<OutputStream?>(data)
            Toast.makeText(this, "Image Save", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Image Not Save", Toast.LENGTH_SHORT).show()
        }
    }
}