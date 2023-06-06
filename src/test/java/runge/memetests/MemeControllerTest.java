package runge.memetests;

import hu.akarnokd.rxjava3.swing.RxSwingPlugins;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import runge.meme.*;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class MemeControllerTest
{

    static {
        //makes it so that our service returns right away
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxSwingPlugins.setOnEdtScheduler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void addAndUpdateImage()
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
        controller.addAndUpdateImage("Evil Kermit");

        //then
        verify(service).getMemeImage();
    }

    @Test
    public void updateText()
    {
        //given
        ImageService service = mock();
        DefaultListModel<String> listModel = mock();
        JLabel imageLabel = mock();
        MemeController controller = new MemeController(service, listModel, imageLabel);
        MemeList memeList = mock();
        Observable<MemeList> observable = Observable.just(memeList);
        JTextField upperText = mock();
        JTextField lowerText = mock();
        doReturn(observable).when(service).getMemeImage();

        //when
        controller.updateText(upperText, lowerText);

        //then
        verify(upperText).setSize(800, 50);
    }

    @Test
    public void refreshMainPanel()
    {
        //given
        ImageService service = mock();
        DefaultListModel<String> listModel = mock();
        JLabel imageLabel = mock();
        MemeList memeList = mock();
        JTextField upperText = mock();
        JTextField lowerText = mock();

        MemeController controller = new MemeController(service, listModel, imageLabel);
        Observable<MemeList> observable = Observable.just(memeList);
        doReturn(observable).when(service).getMemeImage();

        //when
        controller.refreshMainPanel(upperText, lowerText);

        //then
        verify(lowerText).setSize(1000, 30);

    }
}
