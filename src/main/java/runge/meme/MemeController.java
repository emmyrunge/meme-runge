package runge.meme;

import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;
import java.net.URL;

public class MemeController
{
    private final JLabel imageLabel;
    private final ImageService service;
    private final DefaultListModel<String> listModel;

    @Inject
    public MemeController(
        ImageService service,
        @Named("listModel") DefaultListModel<String> listModel,
        @Named("imageLabel")JLabel imageLabel
        )
    {
        this.imageLabel = imageLabel;
        this.listModel = listModel;
        this.service = service;
    }

    public void updateImage(String imageName) {
        Disposable disposable = service.getMemeImage()
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        memeList -> {
                            for(int i = 0; i < memeList.getData().getMemes().length; i++)
                            {
                                //returns null?
                                if(imageName.equals(memeList.getData().getMemes()[i].getName()))
                                {
                                    String icon = memeList.getData().getMemes()[i].getUrl();
                                    imageLabel.setIcon(new ImageIcon(new URL(icon)));
                                    System.out.println(icon);
                                    break;
                                }
                            }
                        },
                        Throwable::printStackTrace
                );
    }

    public void addToList()
    {
        Disposable disposable = service.getMemeImage()
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        addToList -> {
                            //getData returns null
                            for(int i = 0; i < addToList.getData().getMemes().length; i++)
                            {
                                listModel.addElement(addToList.getData().getMemes()[i].getName());
                            }
                        },
                        Throwable::printStackTrace
                );
    }




    /*
    public void findImage(String imageName) {
        Disposable disposable = service.getMemeImage()
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        memeList -> {
                            for(int i = 0; i < memeList.getData().getMemes().length; i++)
                            {
                                if(imageName.equals(memeList.getData().getMemes()[i].getName()))
                                {
                                    updateImage(i);
                                    break;
                                }
                            }
                        }
                        ,
                        Throwable::printStackTrace
                );
    }
    */

}
