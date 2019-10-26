package io.wayneg.thunderbirds.io.context;

import java.util.Optional;

public class ContextContainer {

    private static final ThreadLocal _TL = new ThreadLocal();

    public static <C extends RequestContext> Optional<C> getRequestContext() {
        return Optional.ofNullable((C)_TL.get());
    }
}
