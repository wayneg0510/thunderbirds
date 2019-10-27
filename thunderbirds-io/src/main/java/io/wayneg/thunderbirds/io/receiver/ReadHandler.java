package io.wayneg.thunderbirds.io.receiver;

import io.wayneg.thunderbirds.io.context.ContextContainer;
import io.wayneg.thunderbirds.io.context.ReadContext;

import java.util.Optional;

public abstract class ReadHandler {

    protected ReadContext getContext() {
        Optional<ReadContext> opt = ContextContainer.getRequestContext();
        return opt.orElse(null);
    }

    protected ReadContext buildContext(String requestId) {
        return new ReadContext(requestId);
    }
}