package com.suelengc.flickrgrid.view.photos;

import com.google.common.collect.Lists;
import com.suelengc.flickrgrid.model.Photo;
import com.suelengc.flickrgrid.model.PhotosRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class PhotosPresenterTest {

    private static List<Photo> PHOTOS = Lists.newArrayList(
            new Photo("http://farm1.static.flickr.com/578/23451156376_8983a8ebc7.jpg"),
            new Photo("http://farm1.static.flickr.com/578/23451156376_8983a8ebc7.jpg"));

    @Mock
    private PhotosRepository photosRepository;

    @Mock
    private PhotosContract.View photosView;

    private PhotosPresenter photosPresenter;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<PhotosRepository.LoadPhotosCallback> loadPhotosCallbackCaptor;

    @Before
    public void setUpPhotosPresenter() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        this.photosPresenter = new PhotosPresenter(this.photosRepository, this.photosView);
    }

    @Test
    public void loadFirstPageOfPhotosFromRepositoryAndLoadIntoView() throws Exception {
        String query = "kitty";
        int page = 1;

        photosPresenter.loadPhotos(query, page);

        verify(photosView).setProgressIndicator(true);
        verify(photosView).cleanPhotos();

        // Callback is captured and invoked with stubbed notes
        verify(photosRepository).getPhotos(eq(query), eq(page), loadPhotosCallbackCaptor.capture());
        loadPhotosCallbackCaptor.getValue().onPhotosLoaded(PHOTOS);

        // Then progress indicator is hidden and photos are shown in UI
        verify(photosView).setProgressIndicator(false);
        verify(photosView).showPhotos(PHOTOS);
    }

    @Test
    public void loadSecondPageOfPhotosFromRepositoryAndLoadIntoView() throws Exception {
        String query = "cat";
        int page = 2;

        photosPresenter.loadPhotos(query, page);

        verify(photosView).setProgressIndicator(true);

        // Callback is captured and invoked with stubbed notes
        verify(photosRepository).getPhotos(eq(query), eq(page), loadPhotosCallbackCaptor.capture());
        loadPhotosCallbackCaptor.getValue().onPhotosLoaded(PHOTOS);

        // Then progress indicator is hidden and notes are shown in UI
        verify(photosView).setProgressIndicator(false);
        verify(photosView).showPhotos(PHOTOS);
    }
}