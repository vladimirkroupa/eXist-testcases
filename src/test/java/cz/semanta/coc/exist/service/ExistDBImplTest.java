package cz.semanta.coc.exist.service;

import com.google.common.collect.ImmutableMap;
import cz.semanta.coc.exist.EmbeddedExistInstance;
import cz.semanta.coc.exist.StandaloneExistInstance;
import cz.semanta.coc.exist.service.ExistDB;
import cz.semanta.coc.exist.service.ExistDBImpl;
import cz.semanta.coc.util.ResourceLoader;
import org.junit.Before;
import org.junit.Test;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

import static org.junit.Assert.assertEquals;

/**
 * @author vkr
 */
public class ExistDBImplTest {

    private ExistDB embeddedEXist;
    private ExistDB standaloneEXist;

    @Before
    public void setup() throws XMLDBException {
        this.embeddedEXist = new ExistDBImpl(new EmbeddedExistInstance());
        this.standaloneEXist = new ExistDBImpl(new StandaloneExistInstance("admin", "admin", "", "localhost", 8080));
    }

    @Test
    public void storeAndRetrieveResourceUsingXQueryEmbedded() throws Exception {
        storeAndRetrieveResourceUsingXQuery(embeddedEXist, "retrieve_embedded.xq");
    }

    @Test
    public void storeAndRetrieveResourceUsingXQueryStandalone() throws Exception {
        storeAndRetrieveResourceUsingXQuery(standaloneEXist, "retrieve_standalone.xq");
    }

    private void storeAndRetrieveResourceUsingXQuery(ExistDB eXist, String xQueryName) throws Exception {
        String resName = eXist.storeTemporaryXmlDocument("<root>humr</root>");
        Collection tmp = eXist.getTemporaryCollection();
        String xQuery = ResourceLoader.readUTF8ResourceAsString(xQueryName, ExistDBImplTest.class);
        eXist.executeXQuery(xQuery, tmp, ImmutableMap.of("resName", resName));
        // throws error in XQuery if not present
    }

}
