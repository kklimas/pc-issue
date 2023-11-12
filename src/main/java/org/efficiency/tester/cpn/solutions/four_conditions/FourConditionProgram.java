package org.efficiency.tester.cpn.solutions.four_conditions;

import org.efficiency.tester.program.ProgramType;
import org.efficiency.tester.program.ProgramWrapper;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class FourConditionProgram extends ProgramWrapper {
    public FourConditionProgram(int threadCount, long executionTimeMillis, int bufferSize, Supplier<IntStream> randomStreamSupplier) {
        super(threadCount, executionTimeMillis, new FourConditionStore(bufferSize), ProgramType.FOUR_COND, randomStreamSupplier);
    }
}
