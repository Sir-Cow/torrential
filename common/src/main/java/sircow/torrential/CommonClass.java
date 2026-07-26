package sircow.torrential;

import sircow.torrential.platform.Services;

public class CommonClass {
    public static void init() {
        if (Services.PLATFORM.isModLoaded("torrential")) {
            Constants.LOG.info("Initialising " + Constants.MOD_NAME);
        }
    }
}
