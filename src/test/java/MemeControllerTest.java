import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import runge.meme.*;

import javax.swing.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class MemeControllerTest
{

    static {
        //makes it so that our service returns right away
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void updateImage()
    {
        //given
        ImageService service = mock();
        DefaultListModel<String> listModel = mock();
        JLabel imageLabel = mock();
        MemeController controller = new MemeController(service, listModel, imageLabel);
        MemeList memeList = mock();
        Observable<MemeList> observable = Observable.just(memeList);
        doReturn(observable).when(service).getMemeImage();


        //when
        controller.updateImage("Evil Kermit");


        //then
        verify(service).getMemeImage();

    }

    @Test
    public void addToList()
    {
        //given
        ImageService service = mock();
        DefaultListModel<String> listModel = mock();
        JLabel imageLabel = mock();
        MemeController controller = new MemeController(service, listModel, imageLabel);

        Memes meme = new Memes("Evil Kermit", "https://i.imgflip.com/1e7ql7.jpg", 700, 325);
        Data data = new Data(new Memes[]{meme});
        MemeList memeList = new MemeList(data);

        //Data data  = memeList.getData();
        //BUG: is null
        //Memes memes = data.getMemes()[0];
        //String name  = memes.getName();
        Observable<MemeList> observable = Observable.just(memeList);
        doReturn(observable).when(service).getMemeImage();

        //when
        controller.addToList();

        //then
        verify(listModel).addElement("Evil Kermit");

    }
}
