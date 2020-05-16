package com.gitlab.tools;

import java.io.FileOutputStream;
import java.util.Base64;

public class DecodeBase64 {
    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        final Base64.Decoder decoder = Base64.getDecoder();
        byte[] buffer = decoder.decode(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
}
