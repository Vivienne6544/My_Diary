package com.example.myapplication9;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntryStorage {
    private static final String PREFS_NAME = "DiaryPrefs";
    private static final String KEY_ENTRIES = "diary_entries";

    // Save a new entry
    public static void saveEntry(Context context, String entry) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Set<String> entries = new HashSet<>(prefs.getStringSet(KEY_ENTRIES, new HashSet<>()));
        entries.add(entry);

        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_ENTRIES);
        editor.commit();
        editor.putStringSet(KEY_ENTRIES, entries);
        editor.commit();
    }

    // Get all entries
    public static List<String> getEntries(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> entries = prefs.getStringSet(KEY_ENTRIES, new HashSet<>());
        return new ArrayList<>(entries);
    }

    // Save entire list (for deletions and updates)
    public static void saveAllEntries(Context context, List<String> entriesList) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> entries = new HashSet<>(entriesList);

        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_ENTRIES);
        editor.commit();
        editor.putStringSet(KEY_ENTRIES, entries);
        editor.commit();
    }

    // Update a single entry at specific position
    public static void updateEntry(Context context, int position, String newEntry) {
        List<String> entries = getEntries(context);
        if (position >= 0 && position < entries.size()) {
            entries.set(position, newEntry);
            saveAllEntries(context, entries);
        }
    }

    // Delete all entries
    public static void deleteAll(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_ENTRIES).apply();
    }
}