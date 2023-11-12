package org.efficiency.tester;

import org.efficiency.tester.util.TestCasesGenerator;

import java.util.Arrays;

public class Main {
    /* program arguments
    * THREAD_COUNT (amount of producers and consumers)
    * BUFFER_SIZE (min is 20 because of RANDOM_TOP_BOUND field)
    * RANDOM type (0 - Random, 1 - ThreadLocalRandom)
    * */
    public static void main(String[] args) {
        var intArgs = Arrays.stream(args).map(Integer::valueOf).toList();
        if (intArgs.size() != 3) {
            System.out.print(intArgs);
            System.out.print("Wrong number of parameters!!!");
            return;
        }
        var casesGenerator = new TestCasesGenerator(intArgs.get(0), intArgs.get(1), intArgs.get(2));
        casesGenerator.generate();
    }
}
