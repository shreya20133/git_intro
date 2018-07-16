package com.example.dell1.notesappsqlitehw_l16;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell1.notesappsqlitehw_l16.db.NoteDb;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    RecyclerView rv;
    EditText etTitleNote;
    EditText etDescNote;
    NoteDb noteDb;
    Integer done=0;
    ArrayList<Note> noteArrayList=new ArrayList<>();
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab=findViewById(R.id.fab);
        rv=findViewById(R.id.rv);


        noteDb = new NoteDb(this);

        noteArrayList.addAll(noteDb.getAllNotes());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false,null,-1);
            }
        });

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        notesAdapter=new NotesAdapter(noteArrayList,this);
        rv.setAdapter(notesAdapter);
        rv.addOnItemTouchListener(new RecyclerTouchListener(this, rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                showMarkDialog(view,position);
            }

            @Override
            public void onLongClick(View view, int position) {

                showDeleteDialog(position);
            }
        }));
    }

    private void showMarkDialog(final View view, final int position) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("DO YOU WANT TO MARK THIS NOTE AS DONE?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                   markNote(view,position);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    private void markNote(View view,int position) {

        Note note=noteDb.getNoteWithId(noteArrayList.get(position).getId());
        note.setDone(1);
//        TextView textView=view.findViewById(R.id.noteTitle);
//        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView tvdesc=view.findViewById(R.id.noteDesc);
        tvdesc.append("  \u2713");
//        note.setDesc(note.getDesc()+"  "+"\u2713");
        noteArrayList.get(position).setDesc(note.getDesc()+"  "+"\u2713");
        note.setDesc(noteArrayList.get(position).getDesc()+"  "+"\u2713");
        noteDb.updateNote(note);
        notesAdapter.notifyItemChanged(position);
    }

    private void showDeleteDialog(final int position) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("ARE YOU SURE YOU WANT TO DELETE THIS NOTE?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                deleteNote(position);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    private void deleteNote(int position) {

        noteDb.deleteNote(noteArrayList.get(position));
        noteArrayList.remove(position);
        notesAdapter.notifyItemRemoved(position);
    }

    private void showNoteDialog(final boolean shouldUpdate, final Note note, final int position){

        final View dialogView= LayoutInflater.from(this).inflate(R.layout.addnotedialog,null,true);
        etTitleNote=dialogView.findViewById(R.id.etTitleNote);
        etDescNote=dialogView.findViewById(R.id.etDescNote);
        final AlertDialog.Builder addNoteDialog = new AlertDialog.Builder(this);
        addNoteDialog.setTitle("Add a new Note");
        addNoteDialog.setView(dialogView) ;

        if(shouldUpdate && note!=null){
            etTitleNote.setText(note.getTitle());
            etDescNote.setText(note.getDesc());
        }
        addNoteDialog.setCancelable(false).setPositiveButton(shouldUpdate ? "UPDATE" : "ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog alertDialog=addNoteDialog.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(etTitleNote.getText().toString())){
                    Toast.makeText(MainActivity.this,"Please enter Note Title",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    alertDialog.dismiss();
                }

                if(shouldUpdate && note!=null){
                    updateNote(etTitleNote.getText().toString(),etDescNote.getText().toString(),position);
                }
                else{
                    createNote(etTitleNote.getText().toString(),etDescNote.getText().toString());
                }
            }
        });

    }

    private void createNote(String s, String s1) {

        long id=noteDb.insertNote(new Note(s,s1,Calendar.getInstance().getTime().toString(),System.currentTimeMillis(),done));

        Note curentNote=noteDb.getNoteWithId(id);

        if(curentNote!=null){
            noteArrayList.add(curentNote);
            notesAdapter.notifyDataSetChanged();
        }
    }

    private void updateNote(String s, String s1, int position) {

        Note currentNote= noteArrayList.get(position);

        currentNote.setTitle(s);
        currentNote.setDesc(s1);
        currentNote.setDisplayTime(Calendar.getInstance().getTime().toString());
        currentNote.setDone(done);
        currentNote.setId(currentNote.getId());

        noteDb.updateNote(currentNote);

        noteArrayList.set(position,currentNote);
        notesAdapter.notifyItemChanged(position);
    }
}
