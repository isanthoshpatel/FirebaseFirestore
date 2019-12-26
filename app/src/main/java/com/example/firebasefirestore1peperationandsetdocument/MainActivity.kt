package com.example.firebasefirestore1peperationandsetdocument

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.*

class MainActivity : AppCompatActivity() {
    var notename: EditText? = null
    var et1title: EditText? = null
    var et2description: EditText? = null
    var btsave: Button? = null
    var btload: Button? = null
    var tv: TextView? = null
    var etload: EditText? = null

    var docRef = FirebaseFirestore.getInstance().document("notebook/note1")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notename = findViewById(R.id.et_noteNmae)
        et1title = findViewById(R.id.et1_title)
        et2description = findViewById(R.id.et2_description)
        btsave = findViewById(R.id.bt1_save)
        btload = findViewById(R.id.bt2_load)
        tv = findViewById(R.id.tv1)
        etload = findViewById(R.id.et_load)


        btsave?.setOnClickListener {
            setData()
        }
        btload?.setOnClickListener {
            getData()
        }

    }

    fun setData() {

        var noteName = notename?.text.toString()
        var t = et1title?.text.toString()
        var d = et2description?.text.toString()

        var map = mutableMapOf<String, String>()

        map["title"] = t
        map["description"] = d

        docRef.set(map).addOnSuccessListener {
            Toast.makeText(this, "Saved Succesfully", Toast.LENGTH_LONG).show()
            notename?.setText("")
            et1title?.setText("")
            et2description?.setText("")
        }.addOnFailureListener {
            Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun getData() {

        var n = etload?.text.toString()
       docRef.get().addOnSuccessListener {
            if (it.exists()) {
                var title = it.getString("title")
                var description = it.getString("description")
                tv?.setText("title:$title\ndescription:$description")
                etload?.setText("")
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        var n = notename?.text.toString()
        docRef.addSnapshotListener(this) { p0, p1 ->
            if (p1 != null) {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            if (p0?.exists()!!) {
                var title = p0.getString("title")
                var description = p0.getString("description")
                tv?.text = title + "\n" + description
            }

        }
    }

}
