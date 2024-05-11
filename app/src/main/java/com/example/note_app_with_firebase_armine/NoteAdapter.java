//package com.example.note_app_with_firebase_armine;
//
//
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.content.Context;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//
//public class NoteAdapter extends FirestoreRecyclerAdapter <Note, NoteAdapter.NoteViewHolder> {
//    Context context;
//    RealmResults<Note> notesList;
//
//    public MyAdapter(Context context, RealmResults<Note> notesList){
//        this.context = context;
//        this.notesList = notesList;
//    }
//
//
//    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options,Context context) {
//        super(options);
//        this.context = context;
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
//        holder.titleView.setText(note.title);
//        holder.contentTextView.setText(note.content); com.google.firebase.Timestamp firebaseTimestamp = new com.google.firebase.Timestamp(note.timestamp.getTime());
//        holder.timestampTextView.setText(Utility.timestampToString(firebaseTimestamp));
//    }
//
//    @NonNull
//    @Override
//    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item,parent, false);
//        return  new NoteViewHolder(view);
//    }
//
//    class NoteViewHolder extends RecyclerView.ViewHolder{
//        TextView titleView, contentTextView, timestampTextView;
//        public NoteViewHolder(@NonNull View itemView) {
//            super(itemView);
//            titleView = itemView.findViewById(R.id.notes_title_text_view);
//            contentTextView = itemView.findViewById(R.id.notes_content_text_view);
//           timestampTextView = itemView.findViewById(R.id.notes_timestamp_text_view);
//        }
//    }
//}
package com.example.note_app_with_firebase_armine;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {

    Context context;


    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.titleTextView.setText(note.title);
        holder.contentTextView.setText(note.content);
        holder.timestampTextView.setText(Utility.timestampToString(note.timestamp));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);

        });

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item,parent, false);
        return  new NoteViewHolder(view);
    }


    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, contentTextView, timestampTextView;

        public NoteViewHolder(@NonNull View itemView){

            super(itemView);
            titleTextView = itemView.findViewById(R.id.notes_title_text_view);
            contentTextView = itemView.findViewById(R.id.notes_content_text_view);
            timestampTextView = itemView.findViewById(R.id.notes_timestamp_text_view);

        }
    }


}
