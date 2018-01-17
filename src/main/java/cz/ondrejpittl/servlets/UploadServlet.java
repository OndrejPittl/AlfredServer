package cz.ondrejpittl.servlets;

import cz.ondrejpittl.business.cfg.Config;
import cz.ondrejpittl.dev.Dev;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

//@WebServlet("/upload")
class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        //String description = request.getParameter("description");
        // Retrieves <input type="text" name="description">

        //Part filePart = request.getPart("file");
        // Retrieves <input type="file" name="file">

        ////Dev.printObject(request.getParameter("file"));
        //byte[] pic = (request.getParameter("file")).getBytes();
        ////Dev.printObject(pic);

        /*
        Map<String, String[]> map = request.getParameterMap();

        for(String s : map.keySet()) {
            //System.out.print(s);
        }

        //Dev.printObject(map.keySet());
        //Dev.printObject(map.values());
        */



        InputStream in = new ByteArrayInputStream(this.getBody(request).getBytes());
        BufferedImage bImageFromConvert = ImageIO.read(in);

        ImageIO.write(bImageFromConvert, "jpg", new File(
                Config.IMAGES_DESTINATION + "new-darksouls.jpg"));





        //String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        //InputStream fileContent = filePart.getInputStream();



    }

    private String getBody(HttpServletRequest request) {
        String body = "";
        if (request.getMethod().equals("POST") )
        {
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = null;

            try {
                bufferedReader =  request.getReader();
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                    sb.append(charBuffer, 0, bytesRead);
                }
            } catch (IOException ex) {
                // swallow silently -- can't get body, won't
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        // swallow silently -- can't get body, won't
                    }
                }
            }
            body = sb.toString();
        }
        return body;
    }


}