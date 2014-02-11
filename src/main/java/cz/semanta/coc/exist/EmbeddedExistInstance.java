package cz.semanta.coc.exist;

/**
 * @author vkr
 */
public class EmbeddedExistInstance implements ExistInstance {

    @Override
    public String getUsername() {
        return "admin";
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getCollectionRoot() {
        return "";
    }

    @Override
    public String getTemporaryCollection() {
        return "tmp";
    }

    @Override
    public String getExistUri() {
        return "xmldb:exist:///db";
    }

}
