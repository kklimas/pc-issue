package org.efficiency.tester.cpn.solutions.embedded_lock;

import org.efficiency.tester.cpn.store.AbstractStore;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EmbeddedLockStore extends AbstractStore {
    private final Lock lock = new ReentrantLock();
    private final Lock consumerLock = new ReentrantLock();
    private final Lock producerLock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    public EmbeddedLockStore(int bufferSize) {
        super(bufferSize);
    }

    public void produce(int portion) {
        producerLock.lock();
        try {
            lock.lock();
            while (buffer + portion > bufferSize) {
                condition.await();
            }
            buffer += portion;
            condition.signal();
            lock.unlock();
        } catch (InterruptedException ignored) {
        } finally {
            producerLock.unlock();
        }
    }

    public void consume(int portion) {
        consumerLock.lock();
        try {
            lock.lock();
            while (buffer < portion) {
                condition.await();
            }
            buffer -= portion;
            condition.signal();
            lock.unlock();
        } catch (InterruptedException ignored) {
        } finally {
            consumerLock.unlock();
        }
    }
}
