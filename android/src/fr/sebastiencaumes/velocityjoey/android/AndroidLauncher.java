package fr.sebastiencaumes.velocityjoey.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import fr.sebastiencaumes.velocityjoey.R;
import fr.sebastiencaumes.velocityjoey.VelocityJoey;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();


		initialize(new VelocityJoey(), config);
	}


}
