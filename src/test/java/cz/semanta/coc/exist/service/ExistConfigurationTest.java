package cz.semanta.coc.exist.service;

import cz.semanta.coc.exist.EmbeddedExistInstance;
import cz.semanta.coc.exist.ExistInstance;
import cz.semanta.coc.util.ResourceLoader;
import org.junit.Before;
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

    private ExistDB existDB;

    @Before
    public void setUp() throws XMLDBException {
        ExistInstance instance = new EmbeddedExistInstance();
        //ExistInstance instance = new StandaloneExistInstance("admin", "admin", "");
        existDB = new ExistDB(instance);
    }

    @Test
    public void testCounterModulePresent() throws XMLDBException {
        String testXQ = readXQueryScript("counter_test.xq");

        Collection tmp = existDB.getRootCollection();
        existDB.executeXQuery(testXQ, tmp);
        // throws exception in case counter module is not present
    }

    @Test
    public void testUtilModulePresent() throws XMLDBException {
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
