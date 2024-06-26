package com.example.note_app_with_firebase_armine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.Timestamp; // Import Firebase Timestamp

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleEditText, contentEditText;
    ImageButton saveNoteBtn;
    TextView pageTitleView;
    String title,content,docId;
    boolean isEditMode = false;
    TextView deleteNoteTextViewBtn = findViewById(R.id.delete_note_text_view_btn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titleEditText = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        pageTitleView = findViewById(R.id.page_title);

        // Receive data
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        // Check if it's in edit mode
        if( docId != null && !docId.isEmpty()){
            isEditMode = true;

        }

        titleEditText.setText(title);
        contentEditText.setText(content);
        if (isEditMode) {
            pageTitleView.setText(R.string.edit_note_title);
            deleteNoteTextViewBtn.setVisibility(View.VISIBLE);
        }


        saveNoteBtn.setOnClickListener((v) -> saveNote());
        deleteNoteTextViewBtn.setOnClickListener((v)->deleteNoteFromFirebase());
    }

    void saveNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();
        if (noteTitle == null || noteTitle.isEmpty()) {
            titleEditText.setError("Title is required");
            return;
        }

        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());


        saveNoteToFirebase(note);
    }

    void saveNoteToFirebase(Note note) {
        DocumentReference documentReference;
        if(isEditMode){
            //update the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        }else {
            //create new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }


        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //note is added
                    Utility.showToast(NoteDetailsActivity.this, "Note added successfully");
                    finish();
                } else {
                    // Failed to add note
                    Utility.showToast(NoteDetailsActivity.this, "Failed while adding note");
                    // Log the error message
                    if (task.getException() != null) {
                        Log.e("NoteDetailsActivity", "Error adding note: " + task.getException().getMessage());
                    }
                }
            }
        });
    }
    void  deleteNoteFromFirebase(){
        DocumentReference documentReference;
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //note is deleted
                    Utility.showToast(NoteDetailsActivity.this, "Note is deleted successfully");
                    finish();
                } else {
                    // Failed to add note
                    Utility.showToast(NoteDetailsActivity.this, "Failed while deleting the  note");
                    // Log the error message
                    if (task.getException() != null) {
                        Log.e("NoteDetailsActivity", "Error adding note: " + task.getException().getMessage());
                    }
                }
            }
        });
    }
}
