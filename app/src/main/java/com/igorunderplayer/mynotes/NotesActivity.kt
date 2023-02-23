package com.igorunderplayer.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotesActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var list: RecyclerView
    private lateinit var createNoteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        auth = Firebase.auth
        db = Firebase.firestore


        list = findViewById(R.id.testeView)
        list.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        createNoteButton = findViewById(R.id.createNoteButton)
        createNoteButton.setOnClickListener {
            val dialog = CreateNoteDialogFragment()
            dialog.show(supportFragmentManager, "CreateNoteDialogFragment")
        }
    }

    override fun onStart() {
        super.onStart()

        db.collection("users/${auth.currentUser?.uid}/notes")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val notes = snapshot.documents.map { doc ->
                        NoteInfo(
                            doc["title"] as String,
                            doc["info"] as String
                        )
                    }

                    list.adapter = NotesAdapter(notes)
                }
            }
    }
}