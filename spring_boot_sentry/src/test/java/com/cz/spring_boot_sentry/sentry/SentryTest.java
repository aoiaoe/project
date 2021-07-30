//package com.cz.spring_boot_sentry.sentry;
//
//import io.sentry.Sentry;
//import io.sentry.SentryClient;
//import io.sentry.SentryClientFactory;
//import io.sentry.context.Context;
//import io.sentry.event.BreadcrumbBuilder;
//import io.sentry.event.UserBuilder;
//
//public class SentryTest {
//
//    private static SentryClient sentry;
//
//    public static void main(String... args) {
//        /*
//         It is recommended that you use the DSN detection system, which
//         will check the environment variable "SENTRY_DSN", the Java
//         System Property "sentry.dsn", or the "sentry.properties" file
//         in your classpath. This makes it easier to provide and adjust
//         your DSN without needing to change your code. See the configuration
//         page for more information.
//         */
//        Sentry.init();
//
//        // You can also manually provide the DSN to the ``init`` method.
//        String dsn = "http://a95c235de37b480998b0345ea1eac621@106.15.197.59:9000/13";
//        Sentry.init(dsn);
//
//        /*
//         It is possible to go around the static ``Sentry`` API, which means
//         you are responsible for making the SentryClient instance available
//         to your code.
//         */
//        sentry = SentryClientFactory.sentryClient();
//
//        SentryTest myClass = new SentryTest();
////        myClass.logWithStaticAPI();
////        myClass.logWithInstanceAPI();
//        myClass.exc();
//    }
//
//    /**
//     * An example method that throws an exception.
//     */
//    void unsafeMethod() {
//        throw new UnsupportedOperationException("You shouldn't call this!");
//    }
//
//    void exc(){
//        try {
//            int x = 1/0;
//        }catch (Exception e){
//            Sentry.capture(e);
//        }
//    }
//    /**
//     * Examples using the (recommended) static API.
//     */
//    void logWithStaticAPI() {
//        // Note that all fields set on the context are optional. Context data is copied onto
//        // all future events in the current context (until the context is cleared).
//
//        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
//        Sentry.getContext().recordBreadcrumb(
//                new BreadcrumbBuilder().setMessage("User made an action").build()
//        );
//
//        // Set the user in the current context.
//        Sentry.getContext().setUser(
//                new UserBuilder().setEmail("caozhen@yiye.ai").build()
//        );
//
//        // Add extra data to future events in this context.
//        Sentry.getContext().addExtra("extra", "thing");
//
//        // Add an additional tag to future events in this context.
//        Sentry.getContext().addTag("tagName", "tagValue");
//
//        /*
//         This sends a simple event to Sentry using the statically stored instance
//         that was created in the ``main`` method.
//         */
//        Sentry.capture("This is a test");
//
//        try {
//            unsafeMethod();
//        } catch (Exception e) {
//            // This sends an exception event to Sentry using the statically stored instance
//            // that was created in the ``main`` method.
//            Sentry.capture(e);
//        }
//    }
//
//    /**
//     * Examples that use the SentryClient instance directly.
//     */
//    void logWithInstanceAPI() {
//        // Retrieve the current context.
//        Context context = sentry.getContext();
//
//        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
//        context.recordBreadcrumb(new BreadcrumbBuilder().setMessage("User made an action").build());
//
//        // Set the user in the current context.
//        context.setUser(new UserBuilder().setEmail("caozhen@yiye.ai").build());
//
//        // This sends a simple event to Sentry.
//        sentry.sendMessage("Man made");
//
//        try {
//            unsafeMethod();
//        } catch (Exception e) {
//            // This sends an exception event to Sentry.
//            sentry.sendException(e);
//        }
//    }
//
//}
