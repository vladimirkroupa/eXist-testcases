package cz.semanta.coc.exist.service;

import com.google.common.base.Preconditions;
import cz.semanta.coc.exist.ExistInstance;
import org.exist.xmldb.DatabaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

import java.util.Collections;
import java.util.Map;

/**
 * Created by vkr on 2/11/14.
 */
public class ExistDB {

    private static final Logger log = LoggerFactory.getLogger(ExistDB.class);

    private final ExistInstance instance;

    public ExistDB(ExistInstance instance) throws XMLDBException {
        this.instance = instance;
        init();
    }

    private void init() throws XMLDBException {
        Database database = new DatabaseImpl();
        database.setProperty("create-database", "true");
        database.setProperty("configuration", "conf.xml");
        DatabaseManager.registerDatabase(database);
    }

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

    public ResourceSet executeXQuery(String xQuery, Collection inCollection) throws XMLDBException {
        return executeXQuery(xQuery, inCollection, Collections.<String, String>emptyMap());
    }

    public Collection getRootCollection() throws XMLDBException {
        String uri = instance.getExistUri() + "/" + instance.getCollectionRoot();
        log.debug("Accessing collection at path {}.", uri);
        Collection collection = DatabaseManager.getCollection(uri, instance.getUsername(), instance.getPassword());
        return collection;
    }

}
