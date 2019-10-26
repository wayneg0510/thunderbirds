package io.wayneg.thunderbirds.io.context;

import org.apache.commons.lang3.StringUtils;

public class RequestContext extends BaseContext {

    protected String requestId;

    protected String referId;

    public RequestContext(String requestId) {
        if(StringUtils.isNotEmpty(requestId)) {
            this.requestId = requestId;
        }
        this.referId = requestId;
    }
}
