package ca.paulshin.glass.calculator;

import android.app.Application;

public class App extends Application {

	public final static boolean DEBUG = true;
	public final static String TAG = "GlassCalculator";
	static App sInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}

	public static App get() {
		return sInstance;
	}
}
