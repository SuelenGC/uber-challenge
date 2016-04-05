package com.suelengc.flickrgrid.model;

import com.suelengc.flickrgrid.service.photos.flickr.FlickrPhotosApiImp;

public class PhotosRepositories {

    public static PhotosRepository getFlickrPhotosServiceRepository() {
        return new FlickrPhotosServiceRepository(new FlickrPhotosApiImp());
    }
}
