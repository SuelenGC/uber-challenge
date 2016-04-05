package com.suelengc.flickrgrid.view.photos;

import android.support.annotation.NonNull;

import com.suelengc.flickrgrid.model.Photo;
import com.suelengc.flickrgrid.model.PhotosRepository;

import java.util.List;

public class PhotosPresenter implements PhotosContract.UserActionListener {

    private PhotosContract.View view;
    private PhotosRepository repository;

    public PhotosPresenter(@NonNull PhotosRepository repository, @NonNull PhotosContract.View view) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadPhotos(String query, int page) {
        this.view.setProgressIndicator(true);

        if (page == 1) {
            this.view.cleanPhotos();
        }

        repository.getPhotos(query, page, new PhotosRepository.LoadPhotosCallback() {
            @Override
            public void onPhotosLoaded(List<Photo> photos) {
                view.setProgressIndicator(false);
                view.showPhotos(photos);
            }
        });
    }
}
