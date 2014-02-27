package cz.semanta.coc.exist;

/**
 * @author vkr
 */
public class StandaloneExistInstance implements ExistInstance {

    private final String username;

    private final String password;

    private final String collectionRoot;

    private final String host;

    private final String port;

    public StandaloneExistInstance(String username, String password, String collectionRoot, String host, String port) {
        this.username = username;
        this.password = password;
        this.collectionRoot = collectionRoot;
        this.host = host;
        this.port = port;
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
        return "xmldb:exist://"+ host +":"+ port +"/exist/xmlrpc/db";
    }

}