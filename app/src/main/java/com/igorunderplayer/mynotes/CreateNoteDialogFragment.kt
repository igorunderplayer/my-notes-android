package com.igorunderplayer.mynotes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateNoteDialogFragment : DialogFragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var titleInput: EditText
    private lateinit var infoInput: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_createnote, null)

        auth = Firebase.auth
        db = Firebase.firestore

        titleInput = dialogView.findViewById(R.id.createNoteTitle)
        infoInput = dialogView.findViewById(R.id.createNoteInfo)


        builder.setView(dialogView)
            .setPositiveButton("Criar") { _, _ ->
                Toast.makeText(activity, "Criando...", Toast.LENGTH_LONG).show()
                createNote()
            }
            .setNegativeButton("Cancelar") { _, _ ->
                dialog?.cancel()
            }

        return builder.create()
    }

    private fun createNote() {
        val noteInfo = NoteInfo(
            titleInput.text.toString(),
            infoInput.text.toString()
        )

        db.collection("users/${auth.currentUser?.uid}/notes")
            .add(noteInfo)
            .addOnSuccessListener {
                // Toast.makeText(context, "Nota criada com sucesso", Toast.LENGTH_SHORT).show()
            }
    }

}