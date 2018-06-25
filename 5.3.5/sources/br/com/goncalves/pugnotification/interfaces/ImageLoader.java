package br.com.goncalves.pugnotification.interfaces;

public interface ImageLoader {
    void load(int i, OnImageLoadingCompleted onImageLoadingCompleted);

    void load(String str, OnImageLoadingCompleted onImageLoadingCompleted);
}
