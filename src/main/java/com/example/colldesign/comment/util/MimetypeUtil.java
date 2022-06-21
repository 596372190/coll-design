package com.example.colldesign.comment.util;

import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import java.io.File;

public class MimetypeUtil {

    private static MimetypesFileTypeMap imgMtftp;

    @PostConstruct
    public void initMtftp() {
        imgMtftp = new MimetypesFileTypeMap();
        imgMtftp.addMimeTypes("image png gif jpg jpeg bmp");
    }

    public static boolean isImage(Object file) {
        String mimetype = "";
        if (file instanceof File) {
            mimetype = imgMtftp.getContentType((File) file);
        } else if (file instanceof MultipartFile) {
            mimetype = ((MultipartFile) file).getContentType();
        }
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

}
