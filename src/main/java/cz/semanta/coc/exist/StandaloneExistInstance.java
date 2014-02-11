package cz.semanta.coc.exist;

/**
 * @author vkr
 */
public class StandaloneExistInstance implements ExistInstance {

    private final String username;

    private final String password;

    private final String collectionRoot;

    public StandaloneExistInstance(String username, String password, String collectionRoot) {
        this.username = username;
        this.password = password;
        this.collectionRoot = collectionRoot;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getCollectionRoot() {
        return collectionRoot;
    }

    @Override
    public String getTemporaryCollection() {
        return "tmp";
    }

    @Override
    public String getExistUri() {
        //TODO: avoid hardcoded URI
        return "xmldb:exist://localhost:8080/exist/xmlrpc/db";
    }


}
