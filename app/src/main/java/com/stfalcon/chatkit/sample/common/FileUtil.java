package com.stfalcon.chatkit.sample.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {
    // 常见图片文件头魔数（16进制）
    private static final String JPEG_HEX = "FFD8FF";
    private static final String PNG_HEX  = "89504E47";
    private static final String GIF_HEX  = "47494638";
    public static boolean isImage(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] headerBytes = new byte[4];
            if (fis.read(headerBytes) != 4) {
                return false;
            }

            // 转换为16进制字符串
            StringBuilder hexBuilder = new StringBuilder();
            for (byte b : headerBytes) {
                hexBuilder.append(String.format("%02X", b));
            }
            String headerHex = hexBuilder.toString();

            // 检查是否匹配已知图片类型
            return headerHex.startsWith(JPEG_HEX) ||
                    headerHex.startsWith(PNG_HEX) ||
                    headerHex.startsWith(GIF_HEX);
        } catch (IOException e) {
            return false;
        }
    }

    public static String getFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        return new File(filePath).getName();
    }
}
