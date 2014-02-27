package cz.semanta.coc.exist.service;

import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

/**
 * @author vkr
 */
class StringXMLResourceHandler implements ResourceCreator.ResourceTypeHandler<XMLResource, String> {

    @Override
    public String resourceType() {
        return XMLResource.RESOURCE_TYPE;
    }

    @Override
    public void fillResourceWContent(XMLResource resource, String content) throws XMLDBException {
        resource.setContent(content);
    }

}
