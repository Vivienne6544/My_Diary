package com.example.myapplication9;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewEntryActivity extends AppCompatActivity {

    EditText etDiaryEntry;
    Button btnSave;
    int entryPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        etDiaryEntry = findViewById(R.id.etDiaryEntry);
        btnSave = findViewById(R.id.btnSave);

        // Get the data from Intent
        String existingText = getIntent().getStringExtra("entry_text");
        entryPosition = getIntent().getIntExtra("entry_position", -1);

        // Display existing text if editing
        if (existingText != null && !existingText.isEmpty()) {
            etDiaryEntry.setText(existingText);
            btnSave.setText("Update Entry");
        }

        btnSave.setOnClickListener(v -> {
            String entry = etDiaryEntry.getText().toString().trim();
            if (entry.isEmpty()) {
                Toast.makeText(this, "Write something first!", Toast.LENGTH_SHORT).show();
            } else {
                if (entryPosition != -1) {
                    // Update existing entry
                    EntryStorage.updateEntry(this, entryPosition, entry);
                    Toast.makeText(this, "Entry Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    // Save new entry
                    EntryStorage.saveEntry(this, entry);
                    Toast.makeText(this, "Entry Saved!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}