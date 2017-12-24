package utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static final Reader pr;

    static {
        pr = new Reader();
    }

    public static String read(final String propFileName, final String propName) {
        String fName = propFileName + ".properties";
        return pr.getPropertyValue(fName, propName);
    }

    private static class Reader {
        private static final Logger LOG = Logger.getLogger(Reader.class);

        String getPropertyValue(final String propFileName, final String propName) {
            Properties properties = new Properties();
            LOG.debug("Trying to find the value for the '" + propName + "' in file '" + propFileName + ".");
            String value = null;
            try {
                properties.load(
                        getClass().getClassLoader().getResourceAsStream(propFileName)
                );
                value = properties.getProperty(propName);
                LOG.debug("For property '" + propName + "' was found value '" + value + "'.");
            } catch (IOException e) {
                LOG.error(e);
            }
            return value;
        }
    }
}