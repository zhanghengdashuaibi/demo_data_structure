package com.demo.tree;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/10/22.
 *
 *  在这里定义编码的格式如下：
 *
 *      (这里，约定使用int的31位，最高位为0)
 *      (最高位为1，即符号标志位为1的，约定为一些敏感字段的头和尾)
 *
 *     [int:-1] 文件头（包含一些文件信息，例如后缀等等）
 *              [int:-10] 文件后缀(一个int)
 *                  [int:一个int]
 *                          unknown:0（没约定）
 *                          txt : 1
 *                          jpg : 2
 *                          ...（需要约定）
 *     [int:-1]
 *
 *     [int:-2] 字符字典：
 *              [int:] （这个为字符！），为16进制！应该解析为字符。
 *              [int:]...（字符的频率/权重：）
 *              ...
 *     [int:-2]
 *
 *     [int:-3] 编码区
 *              [int:] n个int,为正数，最高位为0。
 *              ...
 *              ...
 *              ...
 *              [int:-301] 说明下一个是最后一个int的开始。
 *              [int:] 说明剩下的bit数
 *                  [int:]...数据
 *     [int:-3]
 *
 *
 */
public class HuffManTree {

//    public static class HuffManSearchTree<T extends Comparable<? super T>>{

    private HuffManNode root;

    public HuffManNode getRoot() {
        return root;
    }

    public void setRoot(HuffManNode root) {
        this.root = root;
    }

    /**
         * huffman Tree
         */
         public static class HuffManNode<T> implements Comparable<HuffManNode>{
             private T element;
             private int weight;//权重
             private HuffManNode<T> left;//左子树
             private HuffManNode<T> right;//右子树

            /**
             * 构造
             */
            public HuffManNode(T element,HuffManNode<T> l,HuffManNode<T> r,int weight){
               this.element = element;
               this.left = l;
               this.right = r;
               this.weight = weight;
            }

            /**
             * 重写比较方法 使用权重做对比
             * @param o
             * @return
             */
            @Override
            public int compareTo(HuffManNode o) {
                if(o.weight > this.weight){
                    return 1;
                }else if(o.weight < this.weight){
                    return -1;
                }
                return 0;
            }

            public T getElement() {
                return element;
            }

            public void setElement(T element) {
                this.element = element;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public HuffManNode<T> getLeft() {
                return left;
            }

            public void setLeft(HuffManNode<T> left) {
                this.left = left;
            }

            public HuffManNode<T> getRight() {
                return right;
            }

            public void setRight(HuffManNode<T> right) {
                this.right = right;
            }

        }

        /**
         * 统计字符出现的频率
         */
        public static List<HuffManNode> countChart(char[] charArray){
            List<Character> characters = new ArrayList<>();//字典
            List<HuffManNode> huffManNodes = new ArrayList<>();//huffman结点返回

            Map<Character,Integer> map = new HashMap<>();
            for(char c : charArray){
                Character character = new Character(c);
                if(map.containsKey(character)){
                    //包含就自加一
                    //重复字符出现的位置
                    int index = characters.indexOf(character);
                    //通过下标位设置权重(频率)
                    int weight = huffManNodes.get(index).getWeight();
                    huffManNodes.get(index).setWeight(++weight);//权重自加1
                    map.put(character,map.get(character)+1);
                }else{
                    //反之置为1
                    characters.add(character);
                    HuffManNode<Character> huffManNode = new HuffManNode<>(character,null,null,1);
                    map.put(character,1);
                    huffManNodes.add(huffManNode);
                }
            }
            return huffManNodes;
        }

        /**
         * 统计文件中出现的字符频率
         */
        public static List<HuffManNode> getHuffManNodes(String filePath){
            List<Character> characters = null;//字典
            List<HuffManNode> huffManNodes = null;//huffman结点返回

            try (FileReader fileReader = new FileReader(filePath)) {
                characters = new ArrayList<>();//字典
                huffManNodes = new ArrayList<>();//huffman结点返回
                int ch;
                //读取文件
                while ((ch = fileReader.read()) != -1){
                   char temp = (char)ch;
                   //开始统计字符出现的频率
                   if(!characters.contains(temp)){
                       //不包含 则是第一次出现 置为1
                       characters.add(temp);
                       HuffManNode<Character> huffManNode = new HuffManNode<>(temp,null,null,1);
                       huffManNodes.add(huffManNode);
                   }else{
                       //包含出现重复 频率加一
                       //重复字符出现的位置
                       int index = characters.indexOf(temp);
                       //重复字符出现的权重(频率)
                       int weight = huffManNodes.get(index).getWeight();
                       //自加以后重新设置权重
                       huffManNodes.get(index).setWeight(++weight);
                   }

                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
                System.out.print("文件找不到异常:"+e.getMessage());
            }catch (IOException e){
                e.printStackTrace();
                System.out.print("IOException异常:"+e.getMessage());
            }
            return huffManNodes;
        }

        /**
         * 封装得到编码字符字典的方法并返回字符编码结果
         */
        public static Map<Character,String> getLetterCode(HuffManNode root){
            Map<Character,String> letterCode = new HashMap<>();
            getLetterCode(root,"",letterCode);
            return letterCode;
        }


        /**
         * 得到编码字符字典
         * 约定 左树为0 右树为1
         * 先序遍历 获得huffman的所有字符编码对
         * root:huffman树的根节点
         * suffix:编码前缀---->左树记为0 右树记为1
         * letterCode:用于保存字符编码的结果
         */
        private static void getLetterCode(HuffManNode root,String suffix,Map<Character,String> letterCode){

            if(root != null){
                if(root.getLeft() == null && root.getRight() == null){
                    //只有根节点
                    Character character = (Character)root.getElement();
                    //存放字符编码
                    letterCode.put(character,suffix);//根节点前缀放入空串
                }
                //左树记0
                getLetterCode(root.left,suffix+"0",letterCode);
                //右树记1
                getLetterCode(root.right,suffix+"1",letterCode);
            }
        }

        /**
         * 根据字符取得letterCode中的对应字符编码
         */
        public static String getEncodeFromOneChar(Map<Character,String> letterCode,char c){
            Set<Character> characters = letterCode.keySet();
            if(characters.contains(c)){
               return letterCode.get(c);
            }
            return "";
        }

        /**
         * 编码
         */
        public static String encode(String originalStr,List<HuffManNode> huffManNodes){
            if (originalStr == null || originalStr.equals("")) {
                return "";
            }
            char[] charArray = originalStr.toCharArray();
//            List<HuffManNode> leafNodes = new ArrayList<HuffManNode>();
            //创建huffman树
            HuffManNode<Object> leafNodes = buildHuffManTree(huffManNodes);
            Map<Character, String> letterCode = getLetterCode(leafNodes);
            StringBuffer buffer = new StringBuffer();
            for (char c : charArray) {
                Character character = new Character(c);
                buffer.append(letterCode.get(character));
            }

            return buffer.toString();
        }

    /**
     * 解码
     */
    public static String decode(List<HuffManNode> huffManNodes,String encode
                                ) {
        //创建huffman树
        HuffManNode<Object> huffManNode = buildHuffManTree(huffManNodes);
        Map<Character, String> letterCode = getLetterCode(huffManNode);
        Map<String, Character> decoder = getDecoder(letterCode);
        // 解码器键集合
        Set<String> keys = decoder.keySet();
        // 解码得到的字符串
        StringBuffer decodeStr = new StringBuffer();
        // 从最短的开始匹配之所以能够成功，是因为哈夫曼编码的唯一前缀性质
        // 临时的可能的键值
        String temp = "";
        // 改变temp值大小的游标
        int i = 1;
        while (encode.length() > 0) {
           temp = encode.substring(0, i);
            if (keys.contains(temp)) {
                Character character = decoder.get(temp);
                decodeStr.append(character);
                encode = encode.substring(i);
                i = 1;
            } else {
                i++;
            }
        }
        return decodeStr.toString();
    }

        /**
          * 获得解码器，也就是通过字母/编码对得到编码/字符对。
          *
          * @param letterCode
          * @return
          */
     private static Map<String, Character> getDecoder(Map<Character, String> letterCode) {
        Map<String, Character> decodeMap = new HashMap<String, Character>();
        Set<Character> keys = letterCode.keySet();
        for (Character key : keys) {
        String value = letterCode.get(key);
        decodeMap.put(value, key);
        }
        return decodeMap;
     }

    /**
     * 构建huffman树
         * 使用优先队列
         */
        public static <T> HuffManNode<T> buildHuffManTree(List<HuffManNode> huffManNodes){

            //创建优先队列
            PriorityQueue<HuffManNode> priorityQueue = new PriorityQueue<>();
            priorityQueue.addAll(huffManNodes);

            /**
             * 创建huffman树
             */
            HuffManNode huffManNode;
            /**
             * huffman树的约定 弹出的两个左右子树的权相加等于父节点的权
             */
            for (int i = 0; i <huffManNodes.size() - 1 ; i++) {
                //分别从队列中弹出两个数
                HuffManNode pollA = priorityQueue.poll();
                HuffManNode pollB = priorityQueue.poll();
                huffManNode = new HuffManNode(null,pollA,pollB,pollA.getWeight()+pollB.getWeight());
                if(priorityQueue.isEmpty()){
                    //优先队列为空就返回最后的新树
                    return huffManNode;
                }
                //反之将组合成的新树放入优先队列中
                priorityQueue.add(huffManNode);
            }
            return null;
        }

//    }

}
