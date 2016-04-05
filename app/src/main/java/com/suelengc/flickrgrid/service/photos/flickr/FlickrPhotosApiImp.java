package com.suelengc.flickrgrid.service.photos.flickr;

import android.os.Handler;
import android.os.Message;

import com.suelengc.flickrgrid.model.Photo;
import com.suelengc.flickrgrid.service.photos.PhotosApi;

import java.util.List;

public class FlickrPhotosApiImp implements PhotosApi {

    @Override
    public void getAllPhotos(final String query, final int page, final FlickrPhotosCallback<List<Photo>> callback) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Photo> photos = (List<Photo>) msg.obj;
                callback.onLoaded(photos);
            }
        };

        Thread task = new Thread(new SearchFlickrPhotosTask(handler, query, page));
        task.start();
    }
}
