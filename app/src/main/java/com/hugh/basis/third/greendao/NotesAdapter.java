package com.hugh.basis.third.greendao;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugh.basis.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private NoteClickListener clickListener;
    private List<Note> dataset;

    public interface NoteClickListener {
        void onNoteClick(int position);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public TextView comment;

        public NoteViewHolder(View itemView, final NoteClickListener clickListener) {
            super(itemView);
            text = itemView.findViewById(R.id.textViewNoteText);
            comment = itemView.findViewById(R.id.textViewNoteComment);
            itemView.setOnClickListener(view -> {
                if (clickListener != null) {
                    clickListener.onNoteClick(getAdapterPosition());
                }
            });
        }
    }

    public NotesAdapter(NoteClickListener clickListener) {
        this.clickListener = clickListener;
        this.dataset = new ArrayList<>();
    }

    public void setNotes(@NonNull List<Note> notes) {
        dataset = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position) {
        return dataset.get(position);
    }

    @NonNull
    @Override
    public NotesAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("aaa"," onCreateViewHolder viewType---->"+viewType);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.NoteViewHolder holder, int position) {
        Log.e("aaa"," onBindViewHolder position---->"+position);
        Note note = dataset.get(position);
        holder.text.setText(note.getText());
        holder.comment.setText(note.getComment());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
