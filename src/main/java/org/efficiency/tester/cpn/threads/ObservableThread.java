package org.efficiency.tester.cpn.threads;

public interface ObservableThread {
    ObservableThreadData getThreadData();

    void setShouldExit(boolean shouldExit);
}
