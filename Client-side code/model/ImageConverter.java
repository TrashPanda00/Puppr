package model;

import java.io.*;

/**
 * The ImageConverter class is a class used to convert a <code>File</code> type object to an array of bytes
 * making for an easier storage and usage of image files.
 *
 * @author Daria-Maria Popa
 * @version 1.0
 */
public class ImageConverter {

    /**
     * A static method converting an image file to a byte[]. The method is static in order to ensure an easier usage in all the
     * other classes of the module.
     * @param file
     *             the image file added from the user's computer
     * @return returns the converted version of the file as an array of bytes (byte[])
     * @throws FileNotFoundException if the reference to the file is <code>null</code>
     */
    public static byte[] ImageToByte(File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try{
            for(int x; (x=fis.read(buf)) != -1 ;){
                bos.write(buf, 0, x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
}
