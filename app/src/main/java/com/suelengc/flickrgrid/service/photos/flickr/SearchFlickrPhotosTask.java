package com.suelengc.flickrgrid.service.photos.flickr;

import android.os.Handler;
import android.os.Message;

import com.suelengc.flickrgrid.model.Photo;
import com.suelengc.flickrgrid.service.infraestructure.WebClient;

import java.io.IOException;
import java.util.List;

class SearchFlickrPhotosTask implements Runnable {

    private static final String FLICKR_PHOTO_SEARCH_URI = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=%s&page=%s";

    private Handler handler;
    private String query;
    private int page;

    public SearchFlickrPhotosTask(Handler handler, String query, int page) {
        this.handler = handler;
        this.query = query;
        this.page = page;
    }

    @Override
    public void run() {
        try {
            String queryUri = String.format(FLICKR_PHOTO_SEARCH_URI, query, page);

            WebClient webClient = new WebClient(queryUri);
            String json = webClient.get();

            FlickrPhotosConverter flickrPhotosConverter = new FlickrPhotosConverter();
            List<Photo> photos = flickrPhotosConverter.fromJsonToPhotos(json);

            Message msg = new Message();
            msg.obj = photos;
            handler.sendMessage(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
