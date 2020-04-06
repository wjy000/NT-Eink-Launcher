package com.etang.nt_launcher.tool.util.json.weather;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 数据流（我也不知道具体原理和作用）
 */
public class StreamTool {
    public static String decodeStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf)) > 0) {
            baos.write(buf, 0, len);
        }
        String data = baos.toString();
        return data;
    }
}