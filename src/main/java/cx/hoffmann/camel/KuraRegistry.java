package cx.hoffmann.camel;

import org.apache.felix.connect.launch.ClasspathScanner;
import org.apache.felix.connect.launch.PojoServiceRegistry;
import org.apache.felix.connect.launch.PojoServiceRegistryFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Author: Ingo Weiss <ingo@redhat.com>
 */

public class KuraRegistry {
    private static ServiceLoader<PojoServiceRegistryFactory> loader = ServiceLoader.load(PojoServiceRegistryFactory.class);
    private static PojoServiceRegistry registry;
    private static BundleContext bundleContext;
    private static KuraRegistry instance = new KuraRegistry();

    private KuraRegistry() {
    }

    public static void init() {
        // Tries to initialize the registry
        if (registry == null) {
            Map config = new HashMap();
            try {
                // Exclude the org.eclipse.osgi bundle from being initialized
                String filter = "(!(" + Constants.BUNDLE_SYMBOLICNAME + "=org.eclipse.osgi*))";
                // Scan the classpath for bundles and add them to the registry
                config.put(PojoServiceRegistryFactory.BUNDLE_DESCRIPTORS, new ClasspathScanner().scanForBundles(filter));
                registry = loader.iterator().next().newPojoServiceRegistry(config);
                bundleContext = registry.getBundleContext();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static KuraRegistry getInstance() {
        if (registry == null) {
            init();
        }

        return instance;
    }

    public BundleContext getBundleContext() {
        return instance.bundleContext;
    }

    public PojoServiceRegistry getRegistry() {
        return instance.registry;
    }
}
