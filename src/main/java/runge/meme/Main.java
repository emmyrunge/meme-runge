package runge.meme;

import java.io.*;


public class Main
{
    public static void main(String[] args) {
        MemeComponent component = DaggerMemeComponent
                .builder()
                .build();
        MemeFrame frame = component.providesMemeFrame();
        frame.setVisible(true);
    }
}