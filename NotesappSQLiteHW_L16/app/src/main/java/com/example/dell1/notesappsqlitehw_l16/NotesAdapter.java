package com.example.dell1.notesappsqlitehw_l16;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

     ArrayList<Note> noteArrayList;
     Context ctx;

    public NotesAdapter(ArrayList<Note> noteArrayList, Context ctx) {
        this.noteArrayList = noteArrayList;
        this.ctx = ctx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titletv;
        TextView desctv;
        TextView timetv;
        TextView idtv;

        public ViewHolder(View itemView) {
            super(itemView);
            titletv=itemView.findViewById(R.id.noteTitle);
            desctv=itemView.findViewById(R.id.noteDesc);
            timetv=itemView.findViewById(R.id.noteTime);
            idtv=itemView.findViewById(R.id.noteId);
        }
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(ctx);
        View inflatedView=layoutInflater.inflate(R.layout.item_row,parent,false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {

        Note currentNote=noteArrayList.get(position);

        holder.idtv.setText("ID: "+ String.valueOf(currentNote.getId()));
        holder.titletv.setText(currentNote.getTitle());
        holder.desctv.setText(currentNote.getDesc());
        holder.timetv.setText(currentNote.getDisplayTime());

    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

}
