package org.efficiency.tester.util;

import org.efficiency.tester.cpn.solutions.embedded_lock.EmbeddedLockProgram;
import org.efficiency.tester.cpn.solutions.four_conditions.FourConditionProgram;
import org.efficiency.tester.program.Program;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class TestCasesGenerator {

    private static final long EXECUTION_TIME_MILLIS = 30000L;
    private static final int RANDOM_TOP_BOUND = 10;

    private final int threadCount;
    private final int bufferSize;
    private final Supplier<IntStream> randomStreamSupplier;

    public TestCasesGenerator(int threadCount, int bufferSize, int randomType) {
        this.bufferSize = bufferSize < RANDOM_TOP_BOUND * 2 ? 20 : bufferSize;
        this.threadCount = threadCount;
        this.randomStreamSupplier = randomType == 0
                ? () -> new Random().ints(0, RANDOM_TOP_BOUND)
                : () -> ThreadLocalRandom.current().ints(0, RANDOM_TOP_BOUND);
    }

    public void generate() {
        var embeddedProgram = new EmbeddedLockProgram(threadCount, EXECUTION_TIME_MILLIS, bufferSize, randomStreamSupplier);
        var fourProgram = new FourConditionProgram(threadCount, EXECUTION_TIME_MILLIS, bufferSize, randomStreamSupplier);
        var t1 = new Thread(() -> generate(embeddedProgram));
        var t2 = new Thread(() -> generate(fourProgram));
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {
        }
    }

    private void generate(Program program) {
        var optProgramData = program.getData();
        if (optProgramData.isPresent()) {
            var data = optProgramData.get();
            System.out.println(data);
        }
    }
}
