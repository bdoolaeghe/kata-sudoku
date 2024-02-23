package io.my.infra;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GridReader {

    public static String readFile(String path) throws IOException {
        return Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8);
    }

}
