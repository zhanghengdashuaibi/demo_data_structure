package com.demo.tree;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/10/12.
 * AVL树 二叉平衡树
 */
public class AVLTree {


    public static class AVLSearchTree<T extends Comparable<? super T>>{

        /**
         * 定义AVL树节点
         */
        private static class AVLNode<T>{
            private T element;//当前节点数据
            private AVLNode<T> left;//左树
            private AVLNode<T> right;//右树
            private int height;//左右两树相差高

            /**
             * 构造
             */
            public AVLNode(T element){
                this(element,null,null);
            }

            public AVLNode(T element,AVLNode<T> l,AVLNode<T> r){
                this.element = element;
                this.left = l;
                this.right = r;
                this.height = 0;
            }

        }

        /**
         * 计算AVL节点高度
         */
        private int height(AVLNode<T> t){
            if(t==null){
                return -1;
            }else{
                return t.height;
            }
        }

        /**
         * 添加
         */
        private AVLNode<T> insert(T x,AVLNode<T> t){
            if(t == null){
                return t;
            }
            //不是空树的情况
            //比对大小
            int compareResult = x.compareTo(t.element);
            if(compareResult < 0){
                //结果小于0
                //要添加的节点比原树节点小 （左子树）
               t.left = insert(x,t.left);
            }else if(compareResult > 0){
                //右子树
               t.right = insert(x,t.right);
            }else{
                //相等不做任何处理
            }
            //调用平衡树的算法
            return balance(t);
        }

        //允许左右两树高度差（不平衡时）
        private static final int ALLOWED_IMBALANCE = 1;
        /**
         * 平衡树的算法
         */
        private AVLNode<T> balance(AVLNode<T> t){
            if(t == null){
              return t;
            }
            //反之不是空树的情况
            //左树高于右树的不平衡情况
            if(height(t.left) - height(t.right) > ALLOWED_IMBALANCE){
                //只通过左旋转就能平衡的情况
                if(height(t.left.left) >= height(t.left.right)){
                    t = this.rotateLeft(t);
                }else{
                    //需要通过左-右双旋转才能平衡的情况
                    t =this.leftRightRotate(t);
                }
            }else if(height(t.right) - height(t.left) > ALLOWED_IMBALANCE){
                //右树高于左树的不平衡情况
                //只通过右旋转就能平衡的情况
                if(height(t.right.right) >= height(t.right.left)){
                     //需要通过一次右旋转才能平衡
                    t = this.rotateRight(t);
                }else{
                    //需要通过右-左双旋转才能平衡
                    t = this.rightLeftRotate(t);
                }
            }
           //计算平衡的高度
            // t的高度=t的左子树高度+t的右子树高度+1
            t.height = Math.max(height(t.left),height(t.right))+1;
            //反之直接返回(不需要平衡)
            return t;
        }

        /**
         * 在节点上进行左旋转
         * 将k2的左子节点k1变成根节点
         * k2变成k1的右子节点
         * k1的右子树变成k2的左子树
         */
         private AVLNode<T> rotateLeft(AVLNode<T> k2){
             AVLNode<T> k1 = k2.left;
             k2.left = k1.right;
             k1.right = k2;
             //k1的高度 = k1的左子节点的高度+k2的高度 +1
             k1.height = Math.max(height(k1.left),k2.height)+1;
             //k2的高度 = k2的左子节点的高度+k2的右子节点的高度 +1
             k2.height = Math.max(height(k2.left),height(k2.right))+1;
             return k1;
         }
        /**
         * 在节点上进行右旋转
         * k1的右子节点k2变成根节点
         * k2的左子节点变成k1的右子节点
         * k1变成k2的左子节点
         */
         private AVLNode<T> rotateRight(AVLNode<T> k1){
             AVLNode<T> k2 = k1.right;
             k1.right = k2.left;
             k2.left = k1;
             //经过右旋转后
             //k1的高度 = k1的左子节点的高度+k1的右子节点的高度 + 1
             k1.height = Math.max(height(k1.left),height(k1.right))+1;
             //k2的高度 = k2的右子节点的高度+k1的高度 + 1
             k2.height = Math.max(height(k2.right),k1.height)+1;
             return k2;
         }

        /**
         * 左-右旋转(双旋转)
         * 先对k1进行右旋转得到k2
         * 再对k3进行左旋转
         */
        private AVLNode<T> leftRightRotate(AVLNode<T> k3){
            //先对k1进行右旋转得到k2
            AVLNode<T> k2 = this.rotateRight(k3.left);
            //此时k3的左子节点是k2
            k3.left = k2;
            //再对k3进行左旋转
            return this.rotateLeft(k3);
        }

        /**
         * 右-左旋转
         * 先对k3进行左旋转得到k2
         * 再对k1进行右旋转
         */
        private AVLNode<T> rightLeftRotate(AVLNode<T> k1){
           //先对k3进行左旋转得到k2
            AVLNode<T> k2 = this.rotateLeft(k1.right);
            //此时k1的右边节点是k2
            k1.right = k2;
            //再对k1进行右旋转
            return this.rotateRight(k1);
        }
    }



}
