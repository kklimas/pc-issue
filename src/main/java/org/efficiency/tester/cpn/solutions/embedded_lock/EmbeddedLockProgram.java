package org.efficiency.tester.cpn.solutions.embedded_lock;

import org.efficiency.tester.program.ProgramType;
import org.efficiency.tester.program.ProgramWrapper;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class EmbeddedLockProgram extends ProgramWrapper {
    public EmbeddedLockProgram(int threadCount, long executionTimeMillis, int bufferSize, Supplier<IntStream> randomStreamSupplier) {
        super(threadCount, executionTimeMillis, new EmbeddedLockStore(bufferSize), ProgramType.EMBEDDED_LOCK, randomStreamSupplier);
    }
}
