package org.efficiency.tester.cpn.solutions.four_conditions;

import org.efficiency.tester.cpn.store.AbstractStore;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FourConditionStore extends AbstractStore {

    private int buffer = 0;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition firstProducerWaitingCond = lock.newCondition();
    private final Condition restProducersWaitingCond = lock.newCondition();
    private final Condition firstConsumerWaitingCond = lock.newCondition();
    private final Condition restConsumersWaitingCond = lock.newCondition();

    private boolean isFirstProducerWaiting = false;
    private boolean isFirstConsumerWaiting = false;

    public FourConditionStore(int bufferSize) {
        super(bufferSize);
    }

    public void produce(int count) {
        lock.lock();
        try {

            while (isFirstProducerWaiting) {
                restProducersWaitingCond.await();
            }

            isFirstProducerWaiting = true;

            while (buffer + count > bufferSize) {
                firstProducerWaitingCond.await();
            }

            buffer += count;
            restProducersWaitingCond.signal();
            firstConsumerWaitingCond.signal();
            isFirstProducerWaiting = false;

        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
    }

    public void consume(int count) {
        lock.lock();
        try {
            while (isFirstConsumerWaiting) {
                restConsumersWaitingCond.await();
            }

            isFirstConsumerWaiting = true;

            while (buffer < count) {
                firstConsumerWaitingCond.await();
            }

            buffer -= count;
            restConsumersWaitingCond.signal();
            firstProducerWaitingCond.signal();
            isFirstConsumerWaiting = false;

        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
    }
}
