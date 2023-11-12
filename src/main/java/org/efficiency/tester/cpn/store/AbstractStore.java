package org.efficiency.tester.cpn.store;

public abstract class AbstractStore implements IStore {
    protected final int bufferSize;
    protected int buffer = 0;

    public AbstractStore(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
