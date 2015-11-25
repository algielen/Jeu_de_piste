package be.ipl.groupe17.jeudepiste;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import java.lang.reflect.Method;

public final class Utils {
    private Utils() {
        //aucun sens d'instancier cette classe
    }

    //source : http://stackoverflow.com/a/3681434
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // TODO : deprecated
        //NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi != null) {
            return mWifi.isConnected();
        } else {
            return false;
        }
    }
}
