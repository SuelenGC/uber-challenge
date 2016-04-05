package com.suelengc.flickrgrid.service.photos.flickr;

import java.util.List;

class FlickrPhotos {
    private String page;
    private String pages;
    private String perpage;
    private String total;
    private List<FlickrPhoto> photo;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<FlickrPhoto> getPhoto() {
        return photo;
    }

    public void setPhoto(List<FlickrPhoto> photo) {
        this.photo = photo;
    }
}
