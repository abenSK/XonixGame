/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package a4;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

/**
 *
 * @author Aben
 */
class Sound {

    private AudioClip sound;
    private GameWorldProxy gwp;

    public Sound(String name, GameWorldProxy gwp)
            throws MalformedURLException {
        String filename = "." + File.separator + "Sounds" + File.separator + name;
        this.sound = Applet.newAudioClip(new File(filename).toURI().toURL());
    }

    public void play() {
        this.sound.play();
    }

    public void stop() {
        this.sound.stop();
    }

    public void loop() {
        this.sound.loop();
    }
}
