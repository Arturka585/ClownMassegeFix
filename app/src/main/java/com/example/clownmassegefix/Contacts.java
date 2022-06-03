package com.example.clownmassegefix;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Contacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_contacts);

        ListView listView = findViewById (R.id.contactsList);

        Cursor cursor = getContentResolver ().query (ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null);
        startManagingCursor (cursor);

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        int[] to = {R.id.contactName};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter (this,R.layout.contacts,cursor,from,to);
        listView.setAdapter (simpleCursorAdapter);
        listView.setChoiceMode (ListView.CHOICE_MODE_MULTIPLE);
    }
}