package com.suelengc.flickrgrid.view.photos;

import com.suelengc.flickrgrid.model.Photo;

import java.util.List;

public interface PhotosContract {

    interface View {
        void showPhotos(List<Photo> photos);
        void setProgressIndicator(boolean active);
        void cleanPhotos();
    }

    interface UserActionListener {
        void loadPhotos(String query, int page);
    }
}
