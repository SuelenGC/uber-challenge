package com.suelengc.flickrgrid.service.photos.flickr;

class FlickrObject {
    private FlickrPhotos photos;
    private String stat;

    public FlickrPhotos getPhotos() {
        return photos;
    }

    public void setPhotos(FlickrPhotos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
