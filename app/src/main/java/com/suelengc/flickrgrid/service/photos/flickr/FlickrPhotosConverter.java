package com.suelengc.flickrgrid.service.photos.flickr;

import com.google.gson.Gson;
import com.suelengc.flickrgrid.model.Photo;

import java.util.ArrayList;
import java.util.List;

class FlickrPhotosConverter {

    public static final String PHOTO_URL_PATTERN = "http://farm%s.static.flickr.com/%s/%s_%s.jpg";

    public List<Photo> fromJsonToPhotos(String json) {
        List<Photo> images = new ArrayList<>();

        Gson gson = new Gson();
        FlickrObject object = gson.fromJson(json, FlickrObject.class);
        List<FlickrPhoto> flickrPhotos = object.getPhotos().getPhoto();

        for (FlickrPhoto flickrPhoto : flickrPhotos) {
            String url = getFlickrPhotoUrl(flickrPhoto);
            images.add(new Photo(url));
        }

        return images;
    }

    private String getFlickrPhotoUrl(FlickrPhoto flickrPhoto) {
        return String.format(PHOTO_URL_PATTERN, flickrPhoto.getFarm(),
                flickrPhoto.getServer(), flickrPhoto.getId(),
                flickrPhoto.getSecret());
    }
}
