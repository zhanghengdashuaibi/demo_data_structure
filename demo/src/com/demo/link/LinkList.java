package com.demo.link;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/18.
 *
 * 模拟链结点
 * 单向链表
 */
public class LinkList {

    /**
     * 头节点
     */
    private Link first;//最开始的链结点

    /**
     * 构造
     */
    private void LinkList(){
        this.first = null;//最开始链结点为null
    }

    /**
     * 是否为空
     */
    public boolean isEmpty(){
        return (first==null);
    }

    /**
     * 添加新的链结点
     */
    public void insertFirst(int id, double dd){
        Link newLink = new Link(id, dd);//创建新的链节点
        /**
         * 从最头部新增,所以新创建的链结点在 first的位置,first的位置指向到下一个链节点
         */
        newLink.next = first;//原来的first指向新创建的链结点的next
        first = newLink;//新创建的链结点指向原来first
        /**
         * 上述操作将新创建的链结点置顶,原来的first链结点排在新创建的后面
         */
    }

    /**
     * 删除头部链结点
     */
    public Link deleteFirst(){
        boolean empty = this.isEmpty();
        if(!empty){ //链表不为空的情况下
            Link temp = first; //临时存放头部链结点
            /**
             * 删除 将first的下一个链节点(first.next)指向first
             */
            first = first.next;
            return temp;
        }else{
            return  null;
        }
    }

    /**
     * 遍历链结点
     */
    public void diaplayList(){
        /**
         * 遍历规则 从first开始 一次按顺序向后找 next
         */
        Link current = first;//将最头部的链结点 指向current

        /**
         * 开始遍历
         * 然后从current开始遍历
         */
        while (current != null){
            current.displayLink();
            current = current.next;//循环的将下一个指向前一个 直到为null跳出循环
        }
    }


    /**
     * 查找指定的Link（链结点）
     */
    public  Link find(int key){
        /**
         * 从first开始查找 按顺序一直往下查找(next)
         */

        Link current = first;//将最头部的链结点指向current

        /**
         * 开始遍历
         * 在按照顺序遍历的过程中 去判断数据是否相等
         */
        while (current.iData!=key){
            //继续遍历流程
            if(current.next==null){
                return null;
            }else{
                current = current.next;
            }
        }
        //反之相等返回
        return current;
    }

    /**
     * 删除指定的链结点
     */
    public Link delete(int key){
        Link current = first;//当前链结点
        Link previous = first;//前一个链结点

        while(current.iData!=key){
            if(current.next==null){
                return null;
            }else{
                previous = current;//将当前的链结点指向前一个链结点
                current = current.next;//将下一个链结点指向当前链结点
            }
        }
         if(current == first){
             /**
              * 如果当前的链结点就是最头部的链结点
              * 就将头部的下一个链结点指向头部的链结点
              */
             first = first.next;
         }else{
             /**
              * 反之
              * 直接移除 将当前下一个的链结点指向前一个的下一个链结点
              */
             previous.next = current.next;
         }
         return current;
    }

    /**
     * 自引用  链结点(一个Link的自引用类 属于一个链结点,里面包含数据 和指向下一个链结点的连接)
     */
   public class Link {
        public int iData;//整型数据
        public double dData;//浮点数据
        public Link next;//指向下一个节点的连接

        /**
         * 构造
         */
        public Link(int id, double dd){
            this.iData = id;
            this.dData = dd;
        }

        /**
         * 显示Link数据
         */
        public void displayLink(){
            System.out.print("iData:"+iData);
            System.out.println("dData:"+dData);
        }
    }

}
