package fiuba.tallerdeproyectos2.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    public static boolean isNotNull(String txt){
        return txt != null && txt.trim().length() > 0;
    }

    /**
     * Convierte un bitmap en string.
     *
     * @param bitmap la imagen a ser convertida.
     * @return String la imagen codificada en base64.
     */
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * Convierte un string en bitmap.
     *
     * @param encodedString el string a transformar en bitmap.
     *                      (precondition: encodedString debería estar en base64)
     * @return bitmap (convertido desde el string recibido.)
     */
    public static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            Log.i("WARNING: ", e.getMessage());
            return null;
        }
    }

    /**
     * Get a bitmap from file and try to resize it to be 300x300 pixels.
     *
     * @param filepath Path to the image file.
     * @return Bitmap of image.
     */
    public static Bitmap getBitmap(String filepath) {
        Bitmap bitmap;

        // Decode bitmap to get current dimensions.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(filepath, options);

        // Return resized bitmap.
        return bitmap;
    }

    public static void appendToDebugLog(String activity, String text){
        File logFile = new File(Environment.getExternalStorageDirectory() +  "/clientDebugLog.log");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateAndTime = sdf.format(new Date());
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(currentDateAndTime + " - " + activity + " - " + text);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToErrorLog(String activity, String text){
        File logFile = new File(Environment.getExternalStorageDirectory() +  "/clientErrorLog.log");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateAndTime = sdf.format(new Date());
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(currentDateAndTime + " - " + activity + " - " + text);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToInfoLog(String activity, String text){
        File logFile = new File(Environment.getExternalStorageDirectory() +  "/clientInfoLog.log");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateAndTime = sdf.format(new Date());
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(currentDateAndTime + " - " + activity + " - " + text);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
