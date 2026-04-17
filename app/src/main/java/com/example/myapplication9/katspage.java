package com.example.myapplication9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

public class katspage extends AppCompatActivity {

    Button btnNewEntry, btnDeleteAll;
    ListView listEntries;
    List<String> entries;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katspage);

        btnNewEntry = findViewById(R.id.btnNewEntry);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        listEntries = findViewById(R.id.listEntries);

        loadEntries();

        btnNewEntry.setOnClickListener(v -> {
            Intent intent = new Intent(katspage.this, NewEntryActivity.class);
            startActivity(intent);
        });

        btnDeleteAll.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete All?")
                    .setMessage("This cannot be undone.")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        EntryStorage.deleteAll(this);
                        loadEntries();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // SINGLE click listener for editing
        listEntries.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(katspage.this, NewEntryActivity.class);
            intent.putExtra("entry_text", entries.get(position));
            intent.putExtra("entry_position", position);
            startActivity(intent);
        });

        // LONG press for deleting individual entry
        listEntries.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete?")
                    .setMessage("Delete this entry?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        entries.remove(position);
                        EntryStorage.saveAllEntries(this, entries);
                        loadEntries();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEntries();
    }

    private void loadEntries() {
        entries = EntryStorage.getEntries(this);

        if (adapter == null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entries);
            listEntries.setAdapter(adapter);
        } else {
            adapter.clear();
            if (entries != null && !entries.isEmpty()) {
                adapter.addAll(entries);
            }
            adapter.notifyDataSetChanged();
        }
    }
}