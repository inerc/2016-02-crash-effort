package org.glassfish.jersey.test;

        import java.net.URI;
        import java.security.AccessController;

        import javax.ws.rs.GET;
        import javax.ws.rs.Path;
        import javax.ws.rs.core.Application;

        import org.glassfish.jersey.client.ClientConfig;
        import org.glassfish.jersey.internal.util.PropertiesHelper;
        import org.glassfish.jersey.server.ResourceConfig;
        import org.glassfish.jersey.test.spi.TestContainer;
        import org.glassfish.jersey.test.spi.TestContainerException;
        import org.glassfish.jersey.test.spi.TestContainerFactory;

        import org.jetbrains.annotations.NotNull;
        import org.junit.Assert;
        import org.junit.Ignore;
        import org.junit.Test;
        import static org.junit.Assert.assertEquals;

/**
 * Created by inerc on 05.05.16.
 */
public class AnnotationTest extends Assert {

    @Path("/")
    public static class MyResource {

        @GET
        public String get() {
            return "xxx";
        }
    }

    public static class MyTestContainerFactory implements TestContainerFactory {

        @Override
        public TestContainer create(final URI baseUri, final DeploymentContext context) throws IllegalArgumentException {
            return new TestContainer() {

                @Override
                public ClientConfig getClientConfig() {
                    return null;
                }

                @Override
                public URI getBaseUri() {
                    return baseUri;
                }

                @Override
                public void start() {
                }

                @Override
                public void stop() {
                }
            };
        }
    }

    private static class MyJerseyTest extends JerseyTest {
        @Override
        protected Application configure() {
            return new ResourceConfig(MyResource.class);
        }

        @Override
        protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
            return new MyTestContainerFactory();
        }
    }

    @Test
    public void testCustomTestContainerFactory() {
        MyJerseyTest myJerseyTest = new MyJerseyTest();

        assertEquals(myJerseyTest.getTestContainerFactory().getClass(), MyTestContainerFactory.class);
    }

    @Test
    public void testOverridePortNumber() {
        final int newPort = TestProperties.DEFAULT_CONTAINER_PORT + 1;
        MyJerseyTest myJerseyTest = new MyJerseyTest() {
            @Override
            protected Application configure() {
                forceSet(TestProperties.CONTAINER_PORT, Integer.toString(newPort));
                return super.configure();
            }
        };

        assertEquals(newPort, myJerseyTest.getPort());
    }

    @Test
    public void testThatDefaultContainerPortIsUsed() {
        MyJerseyTest myJerseyTest = new MyJerseyTest();

        String portValue = AccessController.doPrivileged(PropertiesHelper.getSystemProperty(TestProperties.CONTAINER_PORT,
                String.valueOf(TestProperties.DEFAULT_CONTAINER_PORT)));

        assertEquals(Integer.valueOf(portValue).intValue(), myJerseyTest.getPort());
    }


    @Test
    public void Setup(){
        @NotNull Integer Ui= 12;
        if (Ui == null){
            fail("NotNull not working");
        }else
            assertTrue(true);
    }

    @Test
    public void someTest(){
        assertTrue(true);
    }

    @Ignore("Not work")
    @Test
    public void some2Test(){

        System.out.print("getAmount");
        fail("@Ignore annotation not run");


    }

}