package cz.semanta.coc.exist;

/**
 * eXist-db instance connection parameters.
 *
 * @author vkr
 */
public interface ExistInstance {

    String getUsername();

    String getPassword();

    String getCollectionRoot();

    String getTemporaryCollection();

    String getExistUri();

}
