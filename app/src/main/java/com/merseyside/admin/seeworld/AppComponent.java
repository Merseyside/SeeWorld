package com.merseyside.admin.seeworld;

import com.merseyside.admin.seeworld.Modules.AppModule;
import com.merseyside.admin.seeworld.Modules.SeaModule;
import com.merseyside.admin.seeworld.Modules.SettingsModule;
import com.merseyside.admin.seeworld.SeeWorld.Cell;
import com.merseyside.admin.seeworld.SeeWorld.Pinguin;
import com.merseyside.admin.seeworld.SeeWorld.Shark;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, SeaModule.class, SettingsModule.class})
@Singleton
public interface AppComponent{
    void inject(GameThread gameThread);
    void inject(Shark shark);
    void inject(Pinguin pinguin);
    void inject(Cell cell);
}
