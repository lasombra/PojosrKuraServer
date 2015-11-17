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

    public KuraRouter start(Class<? extends KuraRouter> kuraRouter) {
        registry = KuraRegistry.getInstance().getRegistry();
        KuraRouter router = null;
        try {
            log.info("Starting router...");
            router = kuraRouter.newInstance();
            router.start(registry.getBundleContext());
        } catch (Exception e) {
            log.error("Failing to start router....");
            log.error(e.getMessage());
        }

        return router;
    }

    public static void init() {
        PojosrKuraServer pojosrKuraServer = new PojosrKuraServer();
        MyKuraRouter myKuraRouter = (MyKuraRouter) pojosrKuraServer.start(MyKuraRouter.class);
    }

    public static void main(String[] args) {
        init();
    }
}
