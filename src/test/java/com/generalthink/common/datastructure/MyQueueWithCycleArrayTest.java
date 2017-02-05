package com.generalthink.common.datastructure;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MyQueueWithCycleArrayTest {

    private MyQueueWithCycleArray<Integer> queue;

    @Before
    public void setUp() throws Exception {
        queue = new MyQueueWithCycleArray<Integer>();
    }

    @Test
    public void testOffer() {
        for (int i = 0; i < 5; i++) {
            queue.offer(i + 1);
        }
        assertTrue(queue.size() == 5);
        assertTrue("[1,2,3,4,5]".equals(queue.toString()));
    }

    @Test
    public void testPoll() {
        for (int i = 0; i < 5; i++) {
            queue.offer(i + 1);
        }
        for (int i = 0; i < 3; i++) {
            queue.poll();
        }
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);
        assertTrue(queue.size() == 5);
        assertTrue("[4,5,6,7,8]".equals(queue.toString()));
    }


}
