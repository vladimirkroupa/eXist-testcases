package cz.semanta.coc.exist.service;

import com.google.common.base.Optional;
import cz.semanta.coc.exist.ExistInstance;
import org.w3c.dom.Document;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import java.util.Map;

/**
 * Attempt to build a slightly higher-level API upon eXist XML:DB API.
 *
 * @author vkr
 */
public interface ExistDB {

    String storeTemporaryXmlDocument(String content) throws XMLDBException;

    Collection createCollection(Collection parent, String collectionName) throws XMLDBException;

    Collection getRootCollection() throws XMLDBException;

    Collection getTemporaryCollection() throws XMLDBException;

    ResourceSet executeXQuery(String xQuery, Collection inCollection) throws XMLDBException;

    ResourceSet executeXQuery(String xQuery, Collection inCollection, Map<String, String> localVariables) throws XMLDBException;

}
