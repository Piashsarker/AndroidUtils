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
 
 
    public static void showDialog(Context context ,  String title , String message ){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
       // builder.setIcon(R.drawable.warning);
        builder.setMessage(message);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog alertDialog = builder.create();


        alertDialog.show();

    }
   public static void shortToast(Context context, String message) {
        try {
            Toast.makeText(context,
                    message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void longToast(Context context, String message) {
        try {
            Toast.makeText(context,
                    message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        Log.d("SERVER", message);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static String getWifiIpAddress(Context context) {
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        return Formatter.formatIpAddress(ip);
    }

    public static void writeToSDFile(String string) {

        boolean mExternalStorageAvailable;
        boolean mExternalStorageWritable;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWritable = false;
        }
        Utils.log("External Media: readable=" + mExternalStorageAvailable
                + " writable=" + mExternalStorageWritable);

        // Find the root of the external storage.
        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File(root.getAbsolutePath() + "/SERVER");
        dir.mkdirs();

        File file = new File(dir, "SERVER.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(string);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Utils.log("File not found. Did you" + " add a WRITE_EXTERNAL_STORAGE permission to the manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}
