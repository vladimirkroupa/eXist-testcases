package cz.semanta.coc.exist.service;

import com.google.common.base.Preconditions;
import cz.semanta.coc.exist.ExistInstance;
import org.exist.xmldb.DatabaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import java.util.Collections;
import java.util.Map;

/**
 * Created by vkr on 2/11/14.
 */
public class ExistDBImpl implements ExistDB {

    private static final Logger log = LoggerFactory.getLogger(ExistDBImpl.class);

    private final ExistInstance instance;

    public ExistDBImpl(ExistInstance instance) throws XMLDBException {
        this.instance = instance;
        init();
    }

    private void init() throws XMLDBException {
        Database database = new DatabaseImpl();
        database.setProperty("create-database", "true");
        database.setProperty("configuration", "conf.xml");
        DatabaseManager.registerDatabase(database);
    }

    @Override
    public String storeTemporaryXmlDocument(String content) throws XMLDBException {
        ResourceCreator.ResourceTypeHandler<XMLResource, String> handler = new StringXMLResourceHandler();
        Collection collection = getTemporaryCollection();
        ResourceCreator<XMLResource, String> rc = new ResourceCreator(collection, content, handler);
        String documentName = rc.createResource();
        return documentName;
    }

    @Override
    public Collection getRootCollection() throws XMLDBException {
        String uri = instance.getExistUri() + "/" + instance.getCollectionRoot();
        log.debug("Accessing collection at path {}.", uri);
        Collection collection = DatabaseManager.getCollection(uri, instance.getUsername(), instance.getPassword());
        return collection;
    }

    @Override
    public Collection getTemporaryCollection() throws XMLDBException {
        Collection tmp = findCollectionInRoot(instance.getTemporaryCollection());
        if (tmp == null) {
            tmp = createCollection(getRootCollection(), instance.getTemporaryCollection());
        }
        return tmp;
    }

    @Override
    public Collection createCollection(Collection parent, String name) throws XMLDBException {
        CollectionManagementService mgt = getCollectionManagementService(parent);
        Collection result = mgt.createCollection(name);
        log.debug("Created collection {} in parent collection {}.", result.getName(), parent.getName());
        return result;
    }

    @Override
    public ResourceSet executeXQuery(String xQuery, Collection inCollection) throws XMLDBException {
        return executeXQuery(xQuery, inCollection, Collections.<String, String>emptyMap());
    }

    @Override
    public ResourceSet executeXQuery(String xQuery, Collection inCollection, Map<String, String> localVariables) throws XMLDBException {
        XQueryService xqs = (XQueryService) inCollection.getService("XQueryService", "1.0");
        for (Map.Entry<String, String> entry : localVariables.entrySet()) {
            String varName = Preconditions.checkNotNull(entry.getKey());
            String varValue = Preconditions.checkNotNull(entry.getValue());
            xqs.declareVariable(varName, varValue);
        }
        CompiledExpression ce = xqs.compile(xQuery);
        ResourceSet resourceSet = xqs.execute(ce);
        log.debug("ResourceSet contains {} items.", resourceSet.getSize());
        return resourceSet;
    }

    private Collection findCollectionInRoot(String collectionPath) throws XMLDBException {
        Collection root = getRootCollection();
        log.debug("Trying to access collection {} in root collection {}.", collectionPath, root.getName());
        Collection collection = root.getChildCollection(collectionPath);
        if (collection != null) {
            log.debug("Found collection " + collection.getName());
        } else {
            log.error("Could not find collection " + collectionPath);
        }
        return collection;
    }

    private CollectionManagementService getCollectionManagementService(Collection collection) throws XMLDBException {
        CollectionManagementService mgt = (CollectionManagementService) collection.getService("CollectionManagementService", "1.0");
        return mgt;
    }

}
