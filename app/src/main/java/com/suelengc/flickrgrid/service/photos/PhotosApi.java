package com.suelengc.flickrgrid.service.photos;

import com.suelengc.flickrgrid.model.Photo;

import java.util.List;

public interface PhotosApi {

    interface FlickrPhotosCallback<T> {
        void onLoaded(T photos);
    }

    void getAllPhotos(String query, int page, FlickrPhotosCallback<List<Photo>> callback);
}
