public class Utils{


/** Checking Internet Availability in Android Works Both Mobile and WIFI
 Don't forget to add permision in Manifest.xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
**/ 

public void checkInternetConnection(Context context ) {

  ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isConnected = wifi != null && wifi.isConnectedOrConnecting() ||
                mobile != null && mobile.isConnectedOrConnecting();
        if (isConnected) {
            Log.d("Network Available ", "YES");
        } else {
            Log.d("Network Available ", "NO");

        }

}
/** getNetworkInfo() is deprecated in api 23 try to use below code **/ 
public void CheckInternet(Context context) {
   ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
        if (activeNetworkInfo != null) { // connected to the internet
            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(context, "Disconnected" , Toast.LENGTH_SHORT).show(); 
        }
}
}
