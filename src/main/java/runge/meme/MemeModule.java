package runge.meme;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import runge.meme.ImageService;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.*;


@Module
public class MemeModule {

    @Provides
    public ImageService providesImageService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.imgflip.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit.create(ImageService.class);
    }

    @Provides
    @Named("imageLabel")
    @Singleton
    public JLabel providesImageLabel() {
        return new JLabel();
    }

    @Provides
    @Named("listModel")
    @Singleton
    public DefaultListModel<String> listModel(){
        return new DefaultListModel<>();

    }

}