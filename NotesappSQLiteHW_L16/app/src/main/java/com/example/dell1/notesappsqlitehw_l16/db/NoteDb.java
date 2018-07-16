package com.example.dell1.notesappsqlitehw_l16.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dell1.notesappsqlitehw_l16.Note;

import java.util.ArrayList;

import static com.example.dell1.notesappsqlitehw_l16.Constants.*;

public class NoteDb extends SQLiteOpenHelper {

    public NoteDb(Context context) {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query=CREATE+TABLE_NAME+RBR+COLUMN_ID+INTEGER+PRIMARY_KEY+COMMA+COLUMN_TITLE+TEXT+NOT_NULL+COMMA+
                      COLUMN_DESC+TEXT+COMMA+COLUMN_TIME+TEXT+NOT_NULL+COMMA+COLUMN_DONE+INTEGER+NOT_NULL+LBR+TERMINATION;
        sqLiteDatabase.execSQL(query);
    }

    public long insertNote(Note note){

        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_ID,note.getId());
        contentValues.put(COLUMN_TITLE,note.getTitle());
        contentValues.put(COLUMN_DESC,note.getDesc());
        contentValues.put(COLUMN_TIME,note.getDisplayTime());
        contentValues.put(COLUMN_DONE,note.getDone());

        long id=getWritableDatabase().insert(TABLE_NAME,null,contentValues);
        return id;
    }

    public Note getNoteWithId(Long id){
        Note note=null;
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,
                null,
                COLUMN_ID + " = ?",
                new String[]{id.toString()},
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Long fetchedId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID.trim()));
            String fetchedTitle = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE.trim()));
            String fetchedDesc=cursor.getString(cursor.getColumnIndex(COLUMN_DESC.trim()));
            String fetchedTime=cursor.getString(cursor.getColumnIndex(COLUMN_TIME.trim()));
            Integer fetchedDone=cursor.getInt(cursor.getColumnIndex(COLUMN_DONE.trim()));

            note= new Note(fetchedTitle,fetchedDesc,fetchedTime,fetchedId,fetchedDone);
        }

        cursor.close();

        return note;
    }

    public ArrayList<Note> getAllNotes() {

//        String[] projection = new String[]{COLUMN_ID, COLUMN_TITLE};
        ArrayList<Note> notes = new ArrayList<>();

        //Ideally this should run in a separate thread to prevent UI blocking
        Cursor c = getReadableDatabase().query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {

            Long id = c.getLong(c.getColumnIndex(COLUMN_ID.trim()));
            String title = c.getString(c.getColumnIndex(COLUMN_TITLE.trim()));
            String Desc=c.getString(c.getColumnIndex(COLUMN_DESC.trim()));
            String Time=c.getString(c.getColumnIndex(COLUMN_TIME.trim()));
            Integer Done=c.getInt(c.getColumnIndex(COLUMN_DONE.trim()));

            Note note= new Note(title,Desc,Time,id,Done);
            notes.add(note);
        }

        c.close();

        //return an arraylist of all the tasks stored in the db
        return notes;
    }

    public int updateNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(COLUMN_TITLE,note.getTitle());
        values.put(COLUMN_TIME,note.getDisplayTime());
        values.put(COLUMN_DONE,note.getDone());
        values.put(COLUMN_DESC,note.getDesc());

        return db.update(TABLE_NAME,values,COLUMN_ID+" = ? ",new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase() ;
        db.delete(TABLE_NAME,COLUMN_ID+" = ? ",new String[]{String.valueOf(note.getId())});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;

        Log.d("TAG",DROP_TABLE);

        sqLiteDatabase.execSQL(DROP_TABLE);

        onCreate(sqLiteDatabase);
    }
}