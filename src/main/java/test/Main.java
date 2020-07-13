package test;

import org.apache.commons.collections.list.CursorableLinkedList;

import java.lang.ref.WeakReference;

public class Main {

    public static void main(String[] args) {
        CursorableLinkedList cursorableLinkedList = new CursorableLinkedList();


        WeakReference<Object> weakReference = new WeakReference<>(new Object());
        weakReference.enqueue();
    }

   
}
