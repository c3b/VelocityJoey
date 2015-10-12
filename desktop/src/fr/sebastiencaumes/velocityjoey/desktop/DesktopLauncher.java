package fr.sebastiencaumes.velocityjoey.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.sebastiencaumes.velocityjoey.VelocityJoey;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = VelocityJoey.WIDTH;
		config.height = VelocityJoey.HEIGHT;
		config.title = VelocityJoey.TITLE;
		System.out.println("Launcher Desktop");
		new LwjglApplication(new VelocityJoey(), config);
	}
}
