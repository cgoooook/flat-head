package cn.com.flat.head.rest.context;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.container.ContainerRequestContext;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FlatContext {

    private ContainerRequestContext containerRequestContext;

    private Locale locale;

    FlatContext(ContainerRequestContext containerRequestContext) {
        this.containerRequestContext = containerRequestContext;
    }


    public Locale getLanguage() {
        if (this.locale == null) {
            String forwardedLange = this.containerRequestContext.getHeaderString("x-forwarded-lange");
            if (StringUtils.isNotBlank(forwardedLange)) {
                this.locale = LocaleUtils.toLocale(forwardedLange);
            } else {
                this.locale = this.containerRequestContext.getLanguage();
            }
        }

        return this.locale;
    }

    public boolean isSync() {
        return "SYNC".equalsIgnoreCase(this.containerRequestContext.getHeaderString("REQUEST-TYPE"));
    }

    public String HeaderString(String name) {
        return this.containerRequestContext.getHeaderString(name);
    }

    public Map<String, List<String>> getHeaders() {
        return this.containerRequestContext.getHeaders();
    }

    public String getRequestURL() {
        return this.containerRequestContext.getUriInfo().getBaseUri().toString() + " " +  this.containerRequestContext.getUriInfo().getPath();
    }

//    public String getRemoteAddress() {
//        String address = this.containerRequestContext.getHeaderString("x-forwarded-for");
//        if (StringUtils.isNotBlank(address)) {
//            return address;
//        } else {
//            ChannelHandlerContext ctx = ResteasyContext.getContextData(ChannelHandlerContext.class);
//            return ctx.getChannel().getRemoteAddress().toString();
//        }
//    }

}
