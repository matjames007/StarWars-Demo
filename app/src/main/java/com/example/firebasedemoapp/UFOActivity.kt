package com.example.firebasedemoapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UFOActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference.child("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ufoactivity)
        populateImages()

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            chooseImageFromGallery()
        }
    }

    /**
     * Handles the result of the image selection from the gallery.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            Log.d("UFOActivity", "Selected Image URI: $imageUri")
            uploadImageToFirebaseStorage(imageUri)
        }
    }

    /**
     * Attempts to upload an image to Firebase Storage.
     */
    private fun uploadImageToFirebaseStorage(imageUri: Uri?) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("users/${UUID.randomUUID()}") // Create a unique file name

        if (imageUri == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            return
        }

        val uploadTask = storageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            populateImages()
        }.addOnFailureListener {
            Toast.makeText(this, "Image upload failed", Toast.LENGTH_LONG).show()
            Log.e("UFOActivity", "Image upload failed", it)
        }
    }

    /**
     * Prompts the user to select an image from their device's gallery.
     */
    fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    /**
     * Example of Firebase Storage Read
     *
     * Utilizes Glide to load images into the RecyclerView
     * Refer to ImageAdapter.kt for more details
     */
    @SuppressLint("NotifyDataSetChanged")
    fun populateImages() {
        val list = findViewById<RecyclerView>(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(this, 2) // 2 columns
        list.layoutManager = gridLayoutManager

        storageRef.listAll()
            .addOnSuccessListener { listResult ->
                val imageUrls = mutableListOf<String>()
                for (item in listResult.items) {
                    Log.d("UFOActivity", "Item: ${item.name}")

                    item.downloadUrl.addOnSuccessListener { uri ->
                        imageUrls.add(uri.toString())
                        Log.d("UFOActivity", "Image URL: $uri")
                        list.adapter?.notifyDataSetChanged()
                    }
                    item.downloadUrl.addOnFailureListener {
                        Log.e("UFOActivity", "Error getting image URL", it)
                    }
                }
                list.adapter = ImageAdapter(imageUrls)
            }
            .addOnFailureListener {
                Log.e("UFOActivity", "Error getting images", it)
            }
    }
}