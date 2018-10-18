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
         * 查找最小节点
         */
        private AVLNode<T> findMin(AVLNode<T> t){
            if(t == null){
                return null;
            }else if(t.left == null){
                return t;
            }
            return findMin(t.left);
        }

        /**
         * 查找最大节点
         */
        private AVLNode<T> findMax(AVLNode<T> t){
            if(t == null){
                return null;
            }else if(t.right == null){
                return t;
            }
            return findMax(t.right);
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

        /**
         * 移除
         */
        public AVLNode<T> remove(T x,AVLNode<T> t){
            if(t == null){
                //空树情况下不做任何处理
                return t;
            }
            //反之 要移除的节点 与树中原有的节点作比较
            int compareResult = x.compareTo(t.element);
            if(compareResult < 0){
                //从左子树中查找要删除的节点
                t.left = this.remove(x,t.left);//递归查找后移除左边的子节点
            }else if(compareResult > 0){
                //从右子树中查找要删除的节点
                t.right = this.remove(x,t.right);//递归查找后移除右边的子节点
            }else if(t.left != null && t.right != null){
                //删除的节点下有两个子节点的情况
                //找到删除节点下右边的子节点 此时右边的子节点大(所以忽略左边子节点)
                //如果右边子节点下也有左右子节点,则通过findMin()方法找到最小节点,然后替换要删除的节点
                //寻找替换节点
                t.element = findMin(t.right).element;
                //比较当前的删除的节点,然后将当前节点引用给右边子节点
                t.right = remove(t.element,t.right);//递归查找后移除右边的
            }else{
               //只有一个子节点或者是根节点的情况
                t = (t.left != null) ? t.left : t.right;
            }
            return balance(t);//平衡
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
                //只通过LL单旋转就能平衡的情况
                if(height(t.left.left) >= height(t.left.right)){
                    t = this.leftLeftrotate(t);//调用旋转左树的方法
                }else{
                    //需要通过左-右双旋转才能平衡的情况
                    t =this.leftRightRotate(t);
                }
            }else if(height(t.right) - height(t.left) > ALLOWED_IMBALANCE){
                //右树高于左树的不平衡情况
                //只通过RR单旋转就能平衡的情况
                if(height(t.right.right) >= height(t.right.left)){
                     //需要通过一次RR单旋转才能平衡
                    t = this.rightRightrotate(t);
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
         * LL单旋转---往左儿子的左子树插入数据失去平衡(将左子树节点上移,将右子树节点下移)
         * 左子树高于右子树 并且达到不平衡点 高度相差大于1
         */
         private AVLNode<T> leftLeftrotate(AVLNode<T> k2){
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
         * RR单旋转----往右儿子的右子树插入数据失去平衡(将右子树节点上移,将左子树节点下移)
         * 右子树高于左子树 并且达到不平衡点 高度差大于1
         */
         private AVLNode<T> rightRightrotate(AVLNode<T> k1){
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
         * LR双旋转--->左-右旋转(双旋转)
         * 往左儿子的右子树插入数据失去平衡(先进行RR单旋转,再进行LL单旋转)
         */
        private AVLNode<T> leftRightRotate(AVLNode<T> k3){
            //先对k1进行RR单旋转得到k2
            AVLNode<T> k2 = this.rightRightrotate(k3.left);
            //此时k3的左子节点是k2
            k3.left = k2;
            //再对k3进行左旋转
            return this.leftLeftrotate(k3);
        }

        /**
         * RL双旋转--->右-左旋转
         * 往右儿子的左子树插入数据失去平衡(先进行LL单旋转,再进行RR单旋转)
         */
        private AVLNode<T> rightLeftRotate(AVLNode<T> k1){
           //先对k3进行LL单旋转得到k2
            AVLNode<T> k2 = this.leftLeftrotate(k1.right);
            //此时k1的右边节点是k2
            k1.right = k2;
            //再对k1进行右旋转
            return this.leftLeftrotate(k1);
        }
    }



}
