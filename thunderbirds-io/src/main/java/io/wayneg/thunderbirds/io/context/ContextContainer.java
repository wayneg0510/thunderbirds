package io.wayneg.thunderbirds.io.context;

import java.util.Optional;

public class ContextContainer {

    private static final ThreadLocal _TL = new ThreadLocal();

    public static <C extends RequestContext> Optional<C> getRequestContext() {
        return Optional.ofNullable((C)_TL.get());
    }

    public static <C extends RequestContext> void setRequestContext(C context) {
        if(context != null) {
            _TL.set(context);
        }
    }

    public static void clearRequestContext() { _TL.remove(); }
}
