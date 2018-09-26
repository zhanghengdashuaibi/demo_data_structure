package com.demo.link;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/20.
 *
 * 双向列表
 */
public class DoublyLinkedList {


    /**
     * 头尾链结点
     */
    private Link first;
    private Link last;

    /**
     * 构造
     */
    public DoublyLinkedList(){
        this.first = null;
        this.last = null;
    }


    /**
     * 判断是否为空
     */
    public boolean isEmpty(){
        return (this.first == null);
    }


    /**
     * 向链头插入
     */
    public void insertFirst(long key){

        Link newLink = new Link(key);
        /**
         * 空链的情况
         */
      if(isEmpty()){
          /**
           * 双向链表的 last必须要引用  所以空链的清空 链尾必须要引用
           */
          last = newLink;
      }else{
          /**
           * 老的first指向
           */
          first.previous = newLink;
          newLink.next = first;
      }
        /**
         * 新的first指向
         */
        first = newLink;
    }

    /**
     * 向链尾插入
     */
    public void insertLast(long key){

        //新创建链结点
        Link newLink = new Link(key);

        /**
         * 空链情况
         */
         if(isEmpty()){
             first = newLink;
         }else{
             /**
              * 老的last指向
              */
             last.previous = newLink;
             newLink.next = last;
         }

        /**
         * 新的last指向
         */
        last = newLink;
    }

    /**
     * 在指定的数据后面插入新的链结点
     */
    public boolean insertAfter(long key,long dd){
        /**
         * 从first端开始
         */
        Link current = first;
        //遍历查找
        while(current.dData!=key){
          //没有找到遍历
            current = current.next;
            if(current==null){
               //没有找到返回false
                return false;
            }
        }
        //找到当前的key
        //新创建链结点
        Link newLink = new Link(dd);
        /**
         * 只有一个链结点的情况（一个链结点 刚好是找到的key的情况）
         */
        if(current==last){
            //新创建的链结点就是最后一个
            newLink.next = null;
            last = newLink;

        }else{
            /**
             * 不止一个链结点
             */
              newLink.next = current.next;
              current.next.previous = newLink;

        }

        newLink.previous = current;
        current.next = newLink;
        return true;
    }

    /**
     * 移除first的key(从first端开始移除)
     */
    public Link deleteFirst(){
        Link temp = first;
        if(!isEmpty()){
            if(first.next==null){
                //只有一个链结点(first结点)
                //所以当first的next结点为空时,last结点就是空
                //first的下一个是空 那么指向last的是空
                last = null;
            }else{
                //反之不止一个链结点
                //指向last不是空的情况
                //first的下一个的前一个 指向first
                first.next.previous = null;
            }
            first = first.next;
            return temp;
        }else{
            return null;
        }
    }

    /**
     * 从last端移除
     */
    public Link deleteLast(){
        Link temp = last;
        if(last.previous==null){
            //从last端移除
            //只有一个链结点的情况
            first = null;
        }else{
            //指向last
            last.previous.next = null;
        }

        last = last.previous;

        return temp;
    }

    /**
     * 删除指定的key
     */
    public Link deleteKey(long key){
        Link current = first;
        /**
         * 从first开始遍历
         */
        while(current.dData!=key){

            current = current.next;
            if(current==null){
               //为空就结束
                return null;
            }
        }

        /**
         * 找到指定的key
         */
        //指定key是first头部链结点
        if(current==first){
            first = current.next;//指向新的first
        }else{
            //中间位置
            current.previous.next = current.next;
        }

        /**
         * 指定key是last尾部链结点
         */
        if(current==last){
            last = current.previous;
        }else{
            current.next.previous = current.previous;
        }
       return current;
    }

    /**
     * 显示(first---->last)
     */
    public void displayForward(){
        /**
         * 从first--->last的遍历 头部在first
         */
        Link current = first;
        System.out.println("first--->last的遍历");
        while(current != null){
            current.displayLink();
            current = current.next;
        }
    }

    /**
     * 显示(last---->first)
     */
    public void displayBackward(){
        /**
         * 从last--->first的遍历 头部在last
         */
        Link current = last;
        System.out.println("last--->first的遍历");
        while (current != null){
            current.displayLink();
            current = current.previous;
        }
    }

    /**
     * 自引用 链结点
     */
    public class Link{

        private long dData;//数据
        private Link previous;//指向前一个链结点的引用
        private Link next;//指向下一个链结点的引用

        /**
         * 构造
         */
        public Link(long d){
            this.dData = d;
        }

        /**
         * 显示
         */
        public void displayLink(){
            System.out.println(dData);
        }

    }

}
