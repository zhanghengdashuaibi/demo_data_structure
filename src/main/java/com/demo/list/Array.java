package com.demo.list;

import com.demo.iterator.InterIterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/27.
 * 对array list 分析
 */
public class Array {

    /**
     * 结合java.util包下的源码
     */

//    Collection
//    Iterator
//    Iterable
//    ListIterator
    /**
     * 所以使用Collection的 都能使用for增强遍历
     */
    public static <T> void print(Collection<T> coll){

        /**
         * 因为Collection扩展了Iterable接口 所以他可以使用iterator()方法
         */
        //iterator迭代器
        Iterator<T> iterator = coll.iterator();
        //hasNext()是否存在下一个数据
        while(iterator.hasNext()){
            //next()方法获得下一个
            T next = iterator.next();
            System.out.println(next);
        }
    }

    /**
     * list remove  list的衍生 ArrayList LinkedList
     * LinkedList对get()和remove()效率并不高
     */
    public static <T> void removeE1(List<Integer> list){
          int i = 0;
          while(i<list.size()){

              if(list.get(i)%2==0){
                  //移除
                  list.remove(i);
              }else
                  i++;
          }
    }

    /**
     * 使用for增强遍历remove
     * @param <T>
     */
    public static <T> void removeE2(List<Integer> list){
        for (Integer i :list) {
            if(i%2==0){
                //这个地方会抛出ConcurrentModificationException
                //要避免 需要换成iterator中的remove()方法
                list.remove(i);
            }
        }

    }

    /**
     * 使用迭代器的remove
     * 在多线程的情况下还是会抛出ConcurrentModificationException 使用同步锁机制 或者CopyOnWriteArrayList
     * 如果传入LinkedList则是线性的 数量和时间成倍增长
     */
    public static <T> void  removeE3(List<Integer> list){
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer next = iterator.next();
            if(next%2==0){
                synchronized (iterator){
                    //使用迭代器的remove
                    iterator.remove();
                }
            }
        }
    }

}
