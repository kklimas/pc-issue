package org.efficiency.tester.cpn.threads;

import org.efficiency.tester.cpn.store.IStore;

import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

public class Producer extends Thread implements ObservableThread {

    private final IStore store;
    private final PrimitiveIterator.OfInt randomPortions;
    private final long startTimeNano;

    private long operationCounter = 0;
    private boolean shouldExit;

    public Producer(IStore store, IntStream randomPortionStream) {
        this.store = store;
        this.randomPortions = randomPortionStream.iterator();
        this.startTimeNano = System.nanoTime();
    }

    @Override
    public void run() {
        while (!shouldExit) {
            var portion = randomPortions.next();
            store.produce(portion);
            operationCounter += 1;
        }
    }

    @Override
    public ObservableThreadData getThreadData() {
        return new ObservableThreadData(ThreadType.PRODUCER, operationCounter, System.nanoTime() - startTimeNano);
    }

    @Override
    public void setShouldExit(boolean shouldExit) {
        this.shouldExit = shouldExit;
    }
}
