package cz.semanta.coc.com.semanta.coc.exist.util.xmldb;

import org.exist.xmldb.EXistResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

/**
 * Utility methods to ease cleanup of XML:DB objects.
 *
 * @author vkr
 */
public class Closer {

    private static final Logger log = LoggerFactory.getLogger(Closer.class);

    public static void close(Collection collection) {
        if (collection == null) {
            return;
        }
        try {
            if (! collection.isOpen()) {
                log.warn("Attempt to close unopened collection {}.", collection.getName());
                return;
            }
            log.debug("Closing collection {}.", collection.getName());
            collection.close();
        } catch (XMLDBException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(Resource resource) {
        if (resource == null) {
            return;
        }
        EXistResource castResource = (EXistResource) resource;
        try {
            castResource.freeResources();
        } catch (XMLDBException e) {
            throw new RuntimeException(e);
        }
    }

}
