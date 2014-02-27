package cz.semanta.coc.exist.service;

import cz.semanta.coc.exist.EmbeddedExistInstance;
import cz.semanta.coc.exist.ExistInstance;
import cz.semanta.coc.exist.StandaloneExistInstance;
import cz.semanta.coc.util.ResourceLoader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;

import static org.hamcrest.core.IsNot.not;

/**
* Integration tests to verify eXist configuration (conf.xml) and dependencies (JARs).
* Adding test cases as I run into trouble with dependencies, so this is not a exhaustive test suite for configuration yet.
*
* @author vkr
*/
public class ExistConfigurationTest {

    private ExistDB embedded;
    private ExistDB standalone;

    @Before
    public void setUp() throws XMLDBException {
        standalone = new ExistDB(new StandaloneExistInstance("admin", "admin", "tmp", "localhost", "8080"));
        embedded = new ExistDB(new EmbeddedExistInstance());
    }

    @Test
    public void testCounterModulePresentEmbedded() throws XMLDBException {
        counterModulePresent(embedded);
    }

    @Test
    public void testCounterModulePresentStandalone() throws XMLDBException {
        counterModulePresent(standalone);
    }

    private void counterModulePresent(ExistDB existDB) throws XMLDBException {
        String testXQ = readXQueryScript("counter_test.xq");

        Collection tmp = existDB.getRootCollection();
        existDB.executeXQuery(testXQ, tmp);
        // throws exception in case counter module is not present
    }

    @Test
    public void testUtilModulePresentEmbedded() throws XMLDBException {
        utilModulePresent(embedded);
    }

    @Test
    public void testUtilModulePresentStandalone() throws XMLDBException {
        utilModulePresent(standalone);
    }

    private void utilModulePresent(ExistDB existDB) throws XMLDBException {
        String testXQ = readXQueryScript("util_test.xq");

        Collection tmp = existDB.getRootCollection();
        existDB.executeXQuery(testXQ, tmp);
        // throws exception in case util module is not present
    }

    private String readXQueryScript(String name) {
        try {
            return ResourceLoader.readUTF8ResourceAsString(name, this.getClass());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
