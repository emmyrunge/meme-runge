package runge.meme;

import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;
import java.awt.*;
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

    public void addAndUpdateImage(String imageName) {
        Disposable disposable = service.getMemeImage()
                .subscribeOn(Schedulers.io())
                .observeOn(SwingSchedulers.edt())
                .subscribe(
                        memeList -> {
                            Memes[] getInfo = memeList.getData().getMemes();

                            for (int i = 0; i < getInfo.length; i++)
                            {
                                listModel.addElement(getInfo[i].getName());

                                if (imageName.equals(getInfo[i].getName()))
                                {
                                    String updatedIcon = getInfo[i].getUrl();
                                    imageLabel.setIcon(new ImageIcon(new URL(updatedIcon)));
                                }
                            }
                        },
                        Throwable::printStackTrace
                );
    }
    
    public void updateText(JTextField upperText, JTextField lowerText)
    {
        upperText.setEditable(false);
        upperText.setSize(800, 50);
        upperText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        upperText.setForeground(Color.BLACK);

        lowerText.setEditable(false);
        lowerText.setSize(800, 50);
        lowerText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
        lowerText.setBackground(Color.BLACK);
        lowerText.setForeground(Color.WHITE);
    }

    public void refreshMainPanel(JTextField upperText, JTextField lowerText)
    {
        upperText.setText("Enter upper text here");
        upperText.setEditable(true);
        upperText.setSize(1000, 30);
        upperText.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        upperText.setForeground(Color.BLACK);

        lowerText.setText("Enter lower text here");
        lowerText.setEditable(true);
        lowerText.setSize(1000, 30);
        lowerText.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        lowerText.setForeground(Color.BLACK);
        lowerText.setBackground(Color.WHITE);
    }
}
