package cz.semanta.coc.com.semanta.coc.exist.util.xmldb;

import com.google.common.base.Preconditions;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.BinaryResource;
import org.xmldb.api.modules.XMLResource;

/**
 * Utility methods to ease Resource creation.
 *
 * @author vkr
 */
public class Resources {

    public static String readResourceContent(Resource resource) throws XMLDBException {
        Preconditions.checkNotNull(resource, "Cannot read from null resouce.");
        if (resource instanceof BinaryResource) {
            return readBinaryResourceContent((BinaryResource) resource);
        } else if (resource instanceof XMLResource) {
            return readXmlResourceContent((XMLResource) resource);
        } else {
            throw new IllegalStateException("Unsupported org.xmldb.api.modules.Resource subclass: " + resource.getClass().getName());
        }
    }

    private static String readBinaryResourceContent(BinaryResource resource) throws XMLDBException {
        byte[] content = (byte[])resource.getContent();
        return new String(content);
    }

    private static String readXmlResourceContent(XMLResource resource) throws XMLDBException {
        String content = (String)resource.getContent();
        return content;
    }

}
