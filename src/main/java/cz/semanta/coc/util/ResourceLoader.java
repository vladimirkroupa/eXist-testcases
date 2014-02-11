package cz.semanta.coc.util;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

/**
 * @author vkr
 */
public class ResourceLoader {

    public static String readUTF8ResourceAsString(String resourceName, Class<?> relativeTo) throws IOException {
        URL url = Resources.getResource(relativeTo, resourceName);
        return Resources.toString(url, Charsets.UTF_8);
    }

}
