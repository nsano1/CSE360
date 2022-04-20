package com.yummy.project.state;

import java.io.Serializable;

public abstract class Manager implements Serializable {
    protected transient Listener stateListener;

    public Manager() {}

    public void setStateListener(Listener listener) {
        stateListener = listener;
    }
}
