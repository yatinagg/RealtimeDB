package com.yatinagg.realtimedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yatinagg.realtimedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database1: DatabaseReference
    private val TAG: String = "OUTPUT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener{

            val firstName = "Rohan"
            val lastName = "Sharma"
            val age : String = binding.etAge.text.toString()
            val username = "rohan_sharma"

            database1 = FirebaseDatabase.getInstance().getReference("Users")
            val db = Firebase.database
            val myRef = db.getReference("Users")
            val user = User(firstName,lastName,age,username)
            myRef.child(username).setValue(user).addOnSuccessListener {
                Toast.makeText(this,"Successfully saved",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
            // Read from the database
            myRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.getValue()
                    Log.d(TAG, "Value is: " + value.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }

            })

        }
    }
}