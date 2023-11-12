package org.efficiency.tester.program;

import org.efficiency.tester.cpn.threads.ObservableThreadData;

import java.util.List;

public record ProgramData(
        long totalTime,
        ProgramType programType,
        List<ObservableThreadData> records
) {
}
