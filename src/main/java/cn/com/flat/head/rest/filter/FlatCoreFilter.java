package cn.com.flat.head.rest.filter;




import cn.com.flat.head.rest.context.FlatContextManager;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class FlatCoreFilter implements ContainerRequestFilter, ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        FlatContextManager.createContext(containerRequestContext);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        FlatContextManager.clear();
    }
}
