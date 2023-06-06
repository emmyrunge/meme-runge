package runge.meme;

import dagger.Component;
import javax.inject.Singleton;


@Singleton
@Component(modules = {MemeModule.class})
public
interface MemeComponent {
    MemeFrame providesMemeFrame();

}