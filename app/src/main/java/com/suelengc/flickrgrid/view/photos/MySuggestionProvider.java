package com.suelengc.flickrgrid.view.photos;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {

    private static final String AUTHORITY = "com.suelengc.flickrgrid.view.photos.MySuggestionProvider";
    private static final int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    public static void saveRecentQuery(Context context, String query) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(context, AUTHORITY, MODE);
        suggestions.saveRecentQuery(query, null);
    }
}
