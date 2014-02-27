package cz.semanta.coc.exist.service;

import com.google.common.base.Preconditions;
import cz.semanta.coc.exist.util.xmldb.Closer;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

/**
 *
 * @param <T> resource type
 * @param <C> content type
 */
class ResourceCreator<T extends Resource, C> {

    private final String resourceName;
    private final Collection collection;
    private final C content;
    private final ResourceTypeHandler<T, C> resourceHandler;

    ResourceCreator(Collection collection, C content, ResourceTypeHandler<T, C> resourceHandler) {
        this(collection, null, content, resourceHandler);
    }

    ResourceCreator(Collection collection, String resourceName, C content, ResourceTypeHandler<T, C> resourceHandler) {
        this.collection = Preconditions.checkNotNull(collection);
        this.content = Preconditions.checkNotNull(content, "Content cannot be null.");
        this.resourceHandler = Preconditions.checkNotNull(resourceHandler);
        this.resourceName = resourceName;
    }

    String createResource() throws XMLDBException {
        T res = null;
        try {
            res = (T)collection.createResource(resourceName, resourceHandler.resourceType());
            resourceHandler.fillResourceWContent(res, content);
            collection.storeResource(res);
            return res.getId();
        } finally {
            Closer.close(res);
        }
    }

    static interface ResourceTypeHandler<T, C> {

        String resourceType();

        void fillResourceWContent(T resource, C content) throws XMLDBException;

    }

}
