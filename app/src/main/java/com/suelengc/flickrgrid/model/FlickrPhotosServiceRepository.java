package com.suelengc.flickrgrid.model;

import android.support.annotation.NonNull;

import com.suelengc.flickrgrid.service.photos.PhotosApi;

import java.util.List;

public class FlickrPhotosServiceRepository implements PhotosRepository {

    PhotosApi photosApi;

    public FlickrPhotosServiceRepository(@NonNull PhotosApi photosApi) {
        this.photosApi = photosApi;
    }

    @Override
    public void getPhotos(String query, int page, @NonNull final LoadPhotosCallback callback) {
        photosApi.getAllPhotos(query, page, new PhotosApi.FlickrPhotosCallback<List<Photo>>() {
            @Override
            public void onLoaded(List<Photo> photos) {
                callback.onPhotosLoaded(photos);
            }
        });
    }
}
