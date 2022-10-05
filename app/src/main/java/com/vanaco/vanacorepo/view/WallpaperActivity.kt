package com.vanaco.vanacorepo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.vanaco.vanacorepo.adapter.ImageAdapter
import com.vanaco.vanacorepo.model.UserImage
import com.vanaco.vanacorepo.databinding.ActivityWallpaperBinding

class WallpaperActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesList: ArrayList<UserImage>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.imageRecycler
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        imagesList = arrayListOf()

        databaseReference = FirebaseDatabase.getInstance().getReference("userImages")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapShot in snapshot.children) {
                        val image = dataSnapShot.getValue(UserImage::class.java)
                        imagesList.add(image!!)
                    }
                    recyclerView.adapter = ImageAdapter(imagesList, this@WallpaperActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@WallpaperActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}