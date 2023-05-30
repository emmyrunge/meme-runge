package runge.meme;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ImageService
{

    @GET("/get_memes")
    Observable<MemeList> getMemeImage();

}

