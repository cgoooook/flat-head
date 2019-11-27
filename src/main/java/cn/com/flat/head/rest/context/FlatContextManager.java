package cn.com.flat.head.rest.context;

import javax.ws.rs.container.ContainerRequestContext;

public class FlatContextManager {

    private static final ThreadLocal<FlatContext> contextLocal = new ThreadLocal<>();

    public FlatContextManager() {
    }

    public static FlatContext getCurrentContext() {
        return contextLocal.get();
    }

    public static void createContext(ContainerRequestContext containerRequestContext) {
        contextLocal.set(buildContext(containerRequestContext));
    }

    public static void setCurrentContext(FlatContext cubeContext) {
        contextLocal.set(cubeContext);
    }

    public static void clear() {
        contextLocal.remove();
    }

    private static FlatContext buildContext(ContainerRequestContext containerRequestContext) {
        return new FlatContext(containerRequestContext);
    }

}
