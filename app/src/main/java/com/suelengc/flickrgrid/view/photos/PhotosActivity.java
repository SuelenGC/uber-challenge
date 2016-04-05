package com.suelengc.flickrgrid.view.photos;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.suelengc.flickrgrid.R;
import com.suelengc.flickrgrid.model.Photo;
import com.suelengc.flickrgrid.model.PhotosRepositories;

import java.util.ArrayList;
import java.util.List;

public class PhotosActivity extends AppCompatActivity implements PhotosContract.View {

    static final int FIRST_PAGE = 1;
    static final int GRID_COLUMNS = 3;
    static final String PHOTOS = "photos";
    static final String PAGE = "page";
    static final String QUERY = "query";
    static final String FIRST_VISIBLE_PHOTO_POSITION = "firstVisiblePhotoPosition";

    private RecyclerView photosGrid;
    private ProgressDialog loading;

    private PhotosAdapter photosAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Photo> photos = new ArrayList<>();

    private int firstVisiblePhotoPosition;

    int page;
    String query = "";
    PhotosContract.UserActionListener userActionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_photos);

        this.userActionListener = new PhotosPresenter(PhotosRepositories.getFlickrPhotosServiceRepository(), this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(PHOTOS, this.photos);
        outState.putInt(PAGE, this.page);
        outState.putString(QUERY, this.query);
        outState.putInt(FIRST_VISIBLE_PHOTO_POSITION, this.gridLayoutManager.findFirstVisibleItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.photos = (ArrayList<Photo>) savedInstanceState.getSerializable(PHOTOS);
        this.page = savedInstanceState.getInt(PAGE);
        this.query = savedInstanceState.getString(QUERY);
        this.firstVisiblePhotoPosition = savedInstanceState.getInt(FIRST_VISIBLE_PHOTO_POSITION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.setupProgressDialog();
        this.setupPhotosGrid();

        this.handleIntent();

        if (hasPhotos()) {
            scrollToPosition(this.firstVisiblePhotoPosition);
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String newQuery = intent.getStringExtra(SearchManager.QUERY);

            if (isNewSearch(newQuery)) {
                this.query = newQuery;
                this.page = FIRST_PAGE;

                this.userActionListener.loadPhotos(query, page);
                MySuggestionProvider.saveRecentQuery(this, query);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        return true;
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        this.photos.addAll(photos);
        this.photosAdapter.notifyDataSetChanged();
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if(active) {
            this.loading.show();
        } else {
            this.loading.dismiss();
        }
    }

    @Override
    public void cleanPhotos() {
        this.photos.clear();
        this.photosAdapter.notifyDataSetChanged();
    }

    void setNextPage() {
        this.page++;
    }

    private void setupPhotosGrid() {
        this.photosGrid = (RecyclerView) findViewById(R.id.photos_grid);
        this.photosGrid.setHasFixedSize(true);

        this.gridLayoutManager = new GridLayoutManager(this, GRID_COLUMNS);
        this.photosGrid.setLayoutManager(this.gridLayoutManager);

        this.photosAdapter = new PhotosAdapter(this, this.photos);
        this.photosGrid.setAdapter(this.photosAdapter);

        this.photosGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isGridsEnd()) {
                    setNextPage();
                    userActionListener.loadPhotos(query, page);
                }
            }
        });
    }

    private void setupProgressDialog() {
        this.loading = new ProgressDialog(this);
        this.loading.setTitle(getString(R.string.progress_dialog_title));
        this.loading.setMessage(getString(R.string.progress_dialog_message));
    }

    private boolean hasPhotos() {
        return this.photos.size() > 0;
    }

    private boolean isNewSearch(String newQuery) {
        return !this.query.equals(newQuery);
    }

    private void scrollToPosition(int firstVisiblePhotoPosition) {
        this.photosGrid.scrollToPosition(firstVisiblePhotoPosition);
    }

    private boolean isGridsEnd() {
        int totalPhotos = this.gridLayoutManager.getItemCount();
        int lastPhotoPosition = this.gridLayoutManager.findLastVisibleItemPosition();

        return !this.loading.isShowing() && totalPhotos <= (lastPhotoPosition + GRID_COLUMNS);
    }
}
