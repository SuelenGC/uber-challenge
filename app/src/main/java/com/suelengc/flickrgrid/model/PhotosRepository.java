package com.suelengc.flickrgrid.model;

import android.support.annotation.NonNull;
import java.util.List;

public interface PhotosRepository {

    interface LoadPhotosCallback{
        void onPhotosLoaded(List<Photo> photos);
    }

    void getPhotos(String query, int page, @NonNull LoadPhotosCallback callback);
}
