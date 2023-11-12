package org.efficiency.tester.program;

import org.efficiency.tester.cpn.store.IStore;
import org.efficiency.tester.cpn.threads.ObservableThreadData;
import org.efficiency.tester.cpn.threads.Producer;
import org.efficiency.tester.cpn.threads.Consumer;
import org.efficiency.tester.cpn.threads.ObservableThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class ProgramWrapper implements Program {
    private final int threadCount;
    private final long executionTimeMillis;
    private final IStore store;
    private final Supplier<IntStream> randomStreamSupplier;
    private final long programStartTime;
    private final ExecutorService executorService;

    private long programEndTime;
    private List<ObservableThread> programThreads;
    private final ProgramType programType;


    public ProgramWrapper(int threadCount, long executionTimeMillis, IStore store, ProgramType programType, Supplier<IntStream> randomStreamSupplier) {
        this.threadCount = threadCount;
        this.executionTimeMillis = executionTimeMillis;
        this.store = store;
        this.programType = programType;
        this.randomStreamSupplier = randomStreamSupplier;
        this.programStartTime = System.nanoTime();
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public Optional<ProgramData> getData() {
        return execute() ? Optional.of(getProgramData()): Optional.empty();
    }

    private boolean execute() {
        programThreads = createThreads();
        var castedThreads = castToThreads(programThreads);
        startThreads(castedThreads);
        joinThreads(programThreads);
        programEndTime = System.nanoTime();
        return true;
    }

    private List<ObservableThread> createThreads() {
        var observableThreads = new ArrayList<ObservableThread>();
        for (int i = 0; i < threadCount; i++) {
            var producer = new Producer(store, randomStreamSupplier.get());
            var consumer = new Consumer(store, randomStreamSupplier.get());
            observableThreads.add(producer);
            observableThreads.add(consumer);
        }
        return observableThreads;
    }

    private List<Thread> castToThreads(List<ObservableThread> observableThreads) {
        return observableThreads.stream().map(t -> (Thread) t).toList();
    }

    private void startThreads(List<Thread> threads) {
        threads.forEach(executorService::submit);
    }

    private void joinThreads(List<ObservableThread> threads) {
        try {
            Thread.sleep(executionTimeMillis);
        } catch (InterruptedException ignored) {}
        threads.forEach(t -> t.setShouldExit(true));
        executorService.shutdownNow();
    }

    private ProgramData getProgramData() {
        return new ProgramData(
                programEndTime - programStartTime,
                programType,
                getDataRecords()
        );
    }

    private List<ObservableThreadData> getDataRecords() {
        return programThreads.stream()
                .map(ObservableThread::getThreadData)
                .toList();
    }

}
