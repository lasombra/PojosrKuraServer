package cx.hoffmann.camel;

import org.apache.camel.builder.SimpleBuilder;
import org.apache.camel.component.kura.KuraRouter;

/**
 * Author: Ingo Weiss <ingo@redhat.com>
 */

public class MyKuraRouter extends KuraRouter {
    @Override
    public void configure() throws Exception {
        from("timer://foo?fixedRate=true&period=10000").setBody(new SimpleBuilder("Body!!!")).to("log:INFO");
    }
}
