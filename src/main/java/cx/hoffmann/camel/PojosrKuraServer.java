package cx.hoffmann.camel;

import org.apache.camel.component.kura.KuraRouter;
import org.apache.felix.connect.launch.PojoServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Ingo Weiss <ingo@redhat.com>
 */

public class PojosrKuraServer {
    private static Logger log = LoggerFactory.getLogger(PojosrKuraServer.class);
    private PojoServiceRegistry registry;

    public PojosrKuraServer() {
    }

    public <T extends KuraRouter> T start(Class<T> kuraRouter) {
        registry = KuraRegistry.getInstance().getRegistry();
        T router = null;
        try {
            log.debug("Starting router for class {}...", kuraRouter.getName());
            router = kuraRouter.newInstance();
            router.start(registry.getBundleContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return router;
    }

    public static void init() {
        PojosrKuraServer pojosrKuraServer = new PojosrKuraServer();
        MyKuraRouter myKuraRouter = pojosrKuraServer.start(MyKuraRouter.class);
    }

    public static void main(String[] args) {
        init();
    }
}
