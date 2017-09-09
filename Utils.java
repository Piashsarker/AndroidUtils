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
 
   public static String getCurrentDate() {
        String formattedDate = getTimeByFormat("dd-MMM-yyyy hh:mm:ss");
        return formattedDate ;
    }


    public static  String getCurrentTime(){

        String formattedTime =getTimeByFormat("hh:mm");
        return formattedTime ;
    }



    private static String getTimeByFormat(String format){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        String formattedDate = df.format(c.getTime());
        return formattedDate ;
    }
    public static  void notifyUser(Context context,String title,  String msg) {
        int mId = 001;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.teacher)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.teacher))
                        .setContentTitle(title)
                        .setContentText(msg)
                        //.setSubText("Tap to check what they offering!")
                        .setTicker(context.getResources().getString(R.string.student_news))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setAutoCancel(true);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }
 
 
  public static String getGreetings(Context context){
        String greetings = "";
        String formattedTime = getTimeByFormat("HH");

        int hour  = Integer.parseInt(formattedTime);
        if(hour>5 && hour<11){
            greetings = context.getResources().getString(R.string.good_morning);
        }
        else if(hour>11 && hour<17){
            greetings = context.getResources().getString(R.string.good_afternoon);
        }
        else{
            greetings = context.getResources().getString(R.string.good_evening);
        }

        return greetings ;
    }


    private static String getTimeByFormat(String format){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        String formattedDate = df.format(c.getTime());
        return formattedDate ;
    }

    private static   final Pattern IP_ADDRESS
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))");


    public static boolean validateIP(String ip ){
        boolean validate = false ;
        Matcher matcher = IP_ADDRESS.matcher(ip);
        if (matcher.matches()) {
            return true ;
        }
        return validate  ;
    }
    public static  void notifyUser(Context context, String msg) {
        int mId = 001;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.student_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.student_icon))
                        .setContentTitle(context.getResources().getString(R.string.teacher_says))
                        .setContentText(msg)
                        //.setSubText("Tap to check what they offering!")
                        .setTicker(context.getResources().getString(R.string.teacher_says))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setAutoCancel(true);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }

    public static void showMessageDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
            .setMessage(message)
                .setCancelable(false)
                .setIcon(R.mipmap.student_icon)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
 
 
    public static boolean isValidURL(String urlStr) {
        try {
            URI uri = new URI(urlStr);
            return uri.getScheme().equals("http") || uri.getScheme().equals("https");
        } catch (Exception e) {
            return false;
        }
    }

    public static Bitmap getBitmapFromFile(String path) {

        File imageFile = new File(path);
        Bitmap bitmap = null;
        if (imageFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }

        return bitmap;
    }

    public static Bitmap getBitmapFromURI(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static void removeFromArrayListByName(ArrayList<String> arrayList, String name) {

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equalsIgnoreCase(name)) {
                arrayList.remove(i);
            }
        }

    }

    public static ArrayList<String> getValuesFromHashMap(HashMap<String, String> hashMap) {
        ArrayList<String> values = new ArrayList<>();
        for (String key : hashMap.keySet()) {
            values.add(hashMap.get(key));
        }
        return values;
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }


    public static ArrayList<String> getTagList(ArrayList<String> tagList) {

        //declare a ArrayList for storing tag
        ArrayList<String> finalTagList = new ArrayList<>();

        for (int i = 0; i < tagList.size(); i++) {

            //spliting the tagList element
            String[] tag = tagList.get(i).split(",");
            for (int j = 0; j < tag.length; j++) {

               if(!finalTagList.contains(tag[j])){
                   finalTagList.add(tag[j]);
               }
            }
        }
        return finalTagList;
    }

 
 
 
 
}
