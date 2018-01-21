package cz.ondrejpittl.utils;

import cz.ondrejpittl.business.cfg.Config;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class IOManager {

    public static String saveFile(String base64) {
        int endIndex = base64.indexOf(";base64,");
        String prefix = base64.substring(0, endIndex),
               fileExtension;

        if(prefix.contains("png")) {
            fileExtension = "png";
        } else {
            fileExtension = "jpg";
        }

        String filename = java.util.UUID.randomUUID().toString() + "." + fileExtension;
        String path = Config.UPLOADED_FILE_DIRECTORY + filename;


        //data:image/jpeg;base64,
        base64 = base64.replace("data:image/jpeg;base64,","");
        byte[] data = Base64.decodeBase64(base64);

        try {
            OutputStream stream = new FileOutputStream(path);
            stream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Config.FRONTEND_UPLOADED_FILE_DIRECTORY + filename;
    }

    public static boolean removeFile(String filename) {
        try {
            String path = Config.FRONTEND_SRC_DIRECTORY + filename;
            File file = new File(path);
            return file.delete();
        } catch(Exception e){
            return false;
        }
    }
}
