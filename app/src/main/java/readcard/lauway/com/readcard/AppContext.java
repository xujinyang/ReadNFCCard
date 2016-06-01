package readcard.lauway.com.readcard;

import android.app.Application;

import me.ele.omniknight.common.tools.AppLogger;

public class AppContext extends Application {
    private static AppContext INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        AppLogger.debug = true;
    }

    public static AppContext getInstance() {
        return INSTANCE;
    }
}
