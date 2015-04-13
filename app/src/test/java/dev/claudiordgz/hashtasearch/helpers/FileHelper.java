package dev.claudiordgz.hashtasearch.helpers;

import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * Created by Claudio on 3/16/2015.
 */
public class FileHelper {

  public static String readFile(String path)
      throws IOException {
    String retVal = null;
    try {
      RandomAccessFile file = new RandomAccessFile(path, "r");
      byte[] bytes = new byte[(int) file.length()];
      file.read(bytes);
      retVal = new String(bytes);
    } catch (Exception e) {
      throw e;
    }
    return retVal;
  }
}
