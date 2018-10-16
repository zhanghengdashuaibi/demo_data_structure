package com.demo.tree;

import java.nio.BufferUnderflowException;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/10/9.
 * 二叉树
 */
public class BinaryTree {



    /**
     * 二叉查找树
     */
    public static class BinarySearchTree<T extends Comparable<? super T>>{
        /**
         * 二叉树实现
         * 每个二叉树 最多只会有两个子节点
         */
        private static class BinaryNode<T>{
            T element;//当前结点数据
            BinaryNode<T> left;//代表从左边连接下一个节点
            BinaryNode<T> right;//代表从右边连接下一个节点

            /**
             * 构造
             */
             BinaryNode(T theElement){
                this(theElement,null,null);
            }

             BinaryNode(T theElement, BinaryNode<T> l,BinaryNode<T> r){
                this.element = theElement;
                this.left = l;
                this.right = r;
            }
        }

        /**
         * 根节点
         */
        private BinaryNode<T> root;

        /**
         * 构造
         */
        public BinarySearchTree(){
            this.root = null;
        }

        /**
         * 初始为空
         */
        public void makeEmpty(){
            root = null;
        }

        /**
         * 判空
         */
        public boolean isEmpty(){
            return (root == null);
        }


        public boolean contains(T x){
            return this.contains(x,root);
        }

        /**
         * 查找最小
         * @return
         */
        public T findMin(){
            if(isEmpty()){
                //root节点为空
                throw new BufferUnderflowException();
            }
            return this.findMin(root).element;
        }

        /**
         * 查找最大
         */
        public T findMax(){
            if(isEmpty()){
                throw new BufferUnderflowException();
            }
            return this.findMax(root).element;
        }

        /**
         * 新增
         */
        public void insert(T x){
            root = insert(x, root);
        }

        /**
         * 移除
         */
        public void remove(T x){
            root = remove(x,root);
        }

        /**
         * 遍历树
         */
        public void printTree(){
            if(isEmpty()){
               //为空
                System.out.print("空树!");
            }else{
                printTree(root);
            }
        }

        /**
         * tree中是否存在节点X
         * 存在返回 true 反之 false
         */
        private boolean contains(T x,BinaryNode<T> t){
            /**
             * 判断t节点 如果t节点为空  就没有左右子节点 返回false
             */
            if(t==null){
                return false;
            }
            //反之 如果x的值 正是t节点的值
            int compareResult = x.compareTo(t.element);

            if(compareResult < 0){
               //结果小于 二叉树的左边子节点比父节点小
                //递归往左边子节点找 一直找到相等则不再递归查找
                return this.contains(x,t.left);
            }else if(compareResult > 0){
                //结果大于 二叉树的右边子节点比父节点大
                return this.contains(x,t.right);
            }else{
                return true;
            }
        }

        /**
         * findMin
         * 查找最小值
         * 只找左边子节点 因为左边子节点存放的都是比父节点小的数据
         */
        private BinaryNode<T> findMin(BinaryNode<T> t){
            if(t == null){
                return null;
            }else if(t.left == null){
                return t;
            }
            return this.findMin(t.left);//递归找左边最小节点
        }

        /**
         * findMax
         * 查找最大值
         * 只找右边子节点 因为右边子节点存放的都是比父节点大的数据
         */
        private BinaryNode<T> findMax(BinaryNode<T> t){
//            if(t == null){
//                return null;
//            }else if(t.right == null){
//                return t;
//            }
//            return this.findMax(t.right);//递归找右边最大的节点
            /**
             * 非递归实现
             */
            if(t!=null){
                while (t.right!=null){
                    t = t.right;
                }
            }else{
                return null;
            }
            return t;
        }

        /**
         * insert方法
         */
        private BinaryNode<T> insert(T x,BinaryNode<T> t){
            if(t == null){
                //空树的情况下
                return new BinaryNode(x,null,null);
            }
            //反之 新插入的节点 与树种原有的节点比较
            int compareResult = x.compareTo(t.element);
            if(compareResult < 0){
                //往左边子节点插入
                t.left = this.insert(x, t.left);
            }else if(compareResult > 0){
                //往右边子节点插入
                t.right = this.insert(x,t.right);
            }else{
                //相等的情况不做任何处理
            }
            return t;
        }

        /**
         * remove方法
         */
        private BinaryNode<T> remove(T x,BinaryNode<T> t){
            if(t == null){
                //空树情况下不做任何处理
                return t;
            }
            //反之 要移除的节点 与树中原有的节点作比较
            int compareResult = x.compareTo(t.element);
            if(compareResult < 0){
                //左边的子节点
                //将当前节点引用给左边子节点
                t.left = this.remove(x,t.left);//递归查找后移除左边的子节点
            }else if(compareResult > 0){
                //右边的子节点
                //将当前节点引用给右边子节点
                t.right = this.remove(x,t.right);//递归查找后移除右边的子节点
            }else if(t.left != null && t.right != null){
                //左边 右边的子节点都不为空的情况下
                //删除的节点下有两个子节点的情况
                //找到删除节点下右边的子节点 此时右边的子节点大(所以忽略左边子节点)
                //如果右边子节点下也有左右子节点,则通过findMin()方法找到最小节点,然后替换要删除的节点
                t.element = findMin(t.right).element;
                //比较当前的删除的节点,然后将当前节点引用给右边子节点
                t.right = remove(t.element,t.right);//递归查找后移除右边的
            }else{
                if(t.left != null){
                    //要移除的节点只有左边的子节点
                    t = t.left;
                }else if(t.right != null){
                    //要移除的节点只有右边的子节点
                    t = t.right;
                }else if(t.left == null && t.right == null){
                    //要移除的节点下没有左右子节点
                    t.left = null;
                    t.right = null;
                }
            }
            return t;
        }

        /**
         * 遍历树
         * 中序遍历
         */
        private void printTree(BinaryNode<T> t){
            if(t != null){
                printTree(t.left);//遍历左树
                System.out.print(t.element);
                printTree(t.right);//遍历右树
            }

        }
    }

}
