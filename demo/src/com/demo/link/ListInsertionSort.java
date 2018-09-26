package com.demo.link;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/20.
 *
 * 有序链表和数组结合
 */
public class ListInsertionSort {


    /**
     * 链结点
     */
    public static class Link{
        public long dData;//数据
        public Link next;//下一个链结点

        /**
         * 构造
         */
        public Link(long d){
            this.dData = d;
        }

        public void displayLink(){
            System.out.println(dData);
        }
    }

    /**
     * 有序链表
     */
    public static class SortedList{

        /**
         * 头部链结点
         */
        private Link first;

        /**
         * 普通构造
         */
        public SortedList(){
            this.first = null;
        }

        /**
         * 构造
         * 结合数组
         * @param linkArr
         */
        public SortedList(Link[] linkArr){
            this.first = null;
            for (int i = 0; i <linkArr.length ; i++) {
                System.out.println("linkArr:"+linkArr[i].dData);
                insert(linkArr[i]); //数组和有序链表的结合
            }
        }

        /**
         * 有序链表插入
         */
        public void insert(Link k){
            //前一个链结点
            Link previous = null;
            //当前链结点
            Link current = first;

            /**
             * 排序
             */
            while(current != null && k.dData > current.dData){

                //前一个链结点指向当前链结点
                previous = current;
                //当前链结点指向当前链结点的下一个
                current = current.next;
            }
            //反之current为空,那么前一个链结点肯定为空
            if(previous == null){
                //空链的情况
                //将新创建的连接点指向头部链结点
                first = k;
            }else{
                //不是空链的情况
                /**
                 * 属于dData>current.dData的情况 新创建的链结点为2 已存在的链结点为1和3的情况
                 */
                //前一个链结点的下一个链结点指向新创建的链结点
                previous.next = k;
            }

            /**
             * 剩下的情况 新创建的链结点为1  已存在的链结点为3和2
             */
            k.next = current;//当前的链结点指向新创建的链结点的下一个
        }

        /**
         * 移除
         * 移除规则 从头部开始移除
         */
        public long remove(){
            SortedList sortedList = new SortedList();
            long temp = sortedList.first.dData;
            sortedList.first = sortedList.first.next;
            return temp;
        }

        /**
         * 显示
         * 规则 从头部开始 按照顺序一个一个读取
         */
        public void displayList(){
            Link current = first;
            while (current != null){
                current.displayLink();
                current = current.next;
            }
        }
    }

    /**
     * 输出
     * @param arg
     */
    public static void main (String[] arg){

        System.out.println("-------------有序链表结合数组------------");
        int size =10;
        ListInsertionSort.Link[] links = new ListInsertionSort.Link[size];
        for (int i = 0; i <size ; i++) {
            long n = (long) (Math.random()*99);
            ListInsertionSort.Link link=new ListInsertionSort.Link(n);
            System.out.println("link1:"+link.dData);
            links[i] = link;
        }
        ListInsertionSort.SortedList sortedList = new ListInsertionSort.SortedList(links);
        sortedList.displayList();
    }
}



