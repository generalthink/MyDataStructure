package com.generalthink.common.datastructure;


/**
 * 使用循环数组的方式实现队列
 * 
 * @author TKing
 */
public class MyQueueWithCycleArray<T> {

    // 保存数据的数组
    private transient Object[] elements;

    // 队列大小
    private int size;

    // 用于指向队列的第一个元素位置
    private int first;

    // 用于指向队列的最后一个元素位置
    private int last;

    public MyQueueWithCycleArray() {
        elements = new Object[10];
    }

    // 入队
    public boolean offer(T e) {
        int length = elements.length;
        // 判断是否需要扩容
        if (size == length) {
            ensureCapacity();
        }
        elements[last] = e;
        // 设置last位置
        last = (last + 1) % length;
        size++;
        return true;
    }

    /**
     * 扩容
     */
    private void ensureCapacity() {
        Object[] oldElements = elements;
        int length = elements.length;
        elements = new Object[length * 2];

        for (int i = 0; i < size; i++) {
            elements[i] = oldElements[((first + i) % length)];
        }

        // 重置first,last位置
        first = 0;
        last = size - 1;
    }

    // 出队
    @SuppressWarnings("unchecked")
    public T poll() {
        if (0 == size) {
            return null;
        }
        T element = (T) elements[first];
        // 重置first位置
        first = (first + 1) % elements.length;
        size--;
        return element;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[(first + i) % elements.length]);
            if (i != size - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public int size() {
        return size;
    }
}
