package io.wayneg.thunderbirds.io.context;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
public abstract class BaseContext extends HashMap implements Serializable {

    @Getter
    String processId = UUID.randomUUID().toString();
}
