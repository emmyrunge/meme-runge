import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import runge.meme.ImageService;
import runge.meme.MemeList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemeTest
{
    @Test
    public void getImage()
    {
        //given
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.imgflip.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ImageService service = retrofit.create(ImageService.class);

        //when
        MemeList image = service.getMemeImage().blockingFirst();

        //then
        assertNotNull(image);
        assertNotNull(image.getData().getMemes());
        assertTrue(image.getData().getMemes()[0].getHeight() > 0);

    }

}
