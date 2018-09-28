package com.demo;

import com.demo.iterator.InterIterator;
import com.demo.link.*;
import com.demo.list.MyArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/14.
 */
@SpringBootApplication
public class DemoApplication {

    public static void main (String[] arg){

        SpringApplication.run(DemoApplication.class, arg);


        String input = "(2.5+3.8)*0.6";
//        String input = "2.5+3.8*6";

        /**
         * 使用下标的方式 取出小数
         */
        int len = input.length();
        char c;
        //最后一位的下标
        int lastIndex=-1;
        //从字符串中去出double型数据
        double number;
        //倒循
//        for (int i = len-1; i >=0 ; --i) {
//           c = input.charAt(i);//取字符
//           //判断字符是否是数字
//            if(Character.isDigit(c)){
//                lastIndex = readDoubleReverse(input,i);
//                System.out.println("最后一位字符的下标:"+lastIndex);
//                number = Double.parseDouble(input.substring(lastIndex, i+1));
//                System.out.println("取出的double数:"+number);
//                i = lastIndex;
//            }
//        }

        //正序
        //不要用i++ 要用++i 先加1计算 对下标操作
//        for (int i = 0; i < len; ++i) {
//            c = input.charAt(i);//取字符
//            if(Character.isDigit(c)){
//                lastIndex = readDouble(input,i);
//                System.out.println("最后一位字符的下标:"+lastIndex);
//                number = Double.parseDouble(input.substring(i,lastIndex));
//                System.out.println("取出的double数:"+number);
//                i = lastIndex-1;
//            }
//        }

        /**
         * 调用正则的方式取出小数
         */
//        String patten = patten(input);
//        System.out.println("取出的double数:"+patten);

        /**
         * 测试链表
         */
        LinkList linkList = new LinkList();
        /**
         * 新增
         */
        linkList.insertFirst(1,1.0);
        linkList.insertFirst(2,2.0);
        linkList.insertFirst(3,3.0);
        linkList.insertFirst(4,4.0);
        linkList.insertFirst(5,5.0);

        linkList.diaplayList();
        /**
         * 删除
         */
        linkList.deleteFirst();
        System.out.println("---------------删除后-----------------");
        linkList.diaplayList();

        /**
         * 查找指定的数据
         */
        LinkList.Link link = linkList.find(1);
        System.out.println("-------------查找指定数据--------------");
        System.out.println(link.iData);

        System.out.println("-------------双端链表-------------");
        FirstLastList firstLastList = new FirstLastList();
        System.out.println("-------------链头新增新的链结点--------------");
        firstLastList.insertFirst(6);
        firstLastList.insertFirst(7);
        firstLastList.displayList();
        System.out.println("-------------链尾新增新的链结点--------------");
        firstLastList.insertLast(8);
        firstLastList.displayList();
        System.out.println("-------------链头删除----------");
        firstLastList.deleteFirst();
        firstLastList.displayList();
        System.out.println("-------------有序链表----------");
        SortedList sortedList = new SortedList();
        sortedList.insert(1);
        sortedList.insert(3);
        sortedList.insert(2);
        sortedList.displayList();
        System.out.println("-------------有序链表结合数组------------");
//        ListInsertionSort listInsertionSort = new ListInsertionSort();
        ListInsertionSort.Link[] links = new ListInsertionSort.Link[10];
        for (int i = 0; i <10 ; i++) {
            long n = (long) (Math.random()*99);
            ListInsertionSort.Link link1=new ListInsertionSort.Link(n);
            System.out.println("link1:"+link1.dData);
            links[i] = link1;
        }
        ListInsertionSort.SortedList sortedList1 = new ListInsertionSort.SortedList(links);
        sortedList1.displayList();
        System.out.println("-------------双向列表------------");
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();
        doublyLinkedList.insertFirst(3);
        doublyLinkedList.insertFirst(1);
        doublyLinkedList.insertFirst(2);
        doublyLinkedList.displayForward();
//        doublyLinkedList.displayBackward();

//        System.out.println("-----------从last端删除----------");
//        doublyLinkedList.deleteFirst();
//        doublyLinkedList.displayForward();
        System.out.println("-----------从指定位置添加链结点---------");
        doublyLinkedList.insertAfter(1,4);
        doublyLinkedList.displayForward();
        System.out.println("-----------链表迭代器-----------");
        InterIterator.LinkList linkList1 = new InterIterator.LinkList();
        InterIterator.ListIterator iterator = linkList1.getIterator();
        iterator.insertBefore(1);
        iterator.insertBefore(2);
        iterator.insertAfter(3);

        //当前链结点
        InterIterator.Link current = iterator.getCurrent();
        long value = current.getdData();
        System.out.println(value);
        /**
         * 遍历
         */
        iterator.reset();//从头部链结点开始
        while(!iterator.atEnd()){

            //当前链结点
            InterIterator.Link current1 = iterator.getCurrent();
            long value1 = current1.getdData();
            iterator.nextLink();
//            if(value1==3){
//                //删除
//                iterator.deleteCurrent();
//            }
            System.out.println(value1);
        }

        System.out.println("------------MyArrayList------");
        MyArrayList<Integer> arrayList = new MyArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.remove(1);
        Iterator<Integer> iterator1 = arrayList.iterator();
        while(iterator1.hasNext()){
            Integer next = iterator1.next();
            System.out.println(next);
        }

    }

    /**
     * read the next number (reverse)
     * @param input
     * @param start
     * @return
     * @throws IllegalArgumentException
     * 取出小数(double数)  使用下标位方式
     * 倒循
     */
    private static int readDoubleReverse(String input, int start)
            throws IllegalArgumentException {
        int dotIndex = -1;
        char c;
        for (int i=start; i>=0; --i) {
            c = input.charAt(i);
            if (c == '.') {//如果此字符为'.'则跳过此位字符的下标,继续下个循环
                if (dotIndex != -1)
                    throw new IllegalArgumentException(
                            "there have more than 1 dots in the number.");
                else
                    dotIndex = i;
            } else if (!Character.isDigit(c)) {
                return i + 1;
            } else if (i == 0) {
                return 0;
            }
        }
        throw new IllegalArgumentException("not a number.");
    }

    /**
     * 取出小数(double数)  使用下标位方式
     * 正循
     */
    private static int readDouble(String input, int start)
            throws IllegalArgumentException {
        int len = input.length();
        int dotIndex = -1;
        char c;
        for (int i=start; i<len; ++i) {
            c = input.charAt(i);
            if (c == '.') {
                if (dotIndex != -1)
                    throw new IllegalArgumentException(
                            "there have more than 1 dots in the number.");
                else if (i == len - 1)
                    throw new IllegalArgumentException(
                            "not a number, dot can't be the last part of a number.");
                else
                    dotIndex = i;
            } else if (!Character.isDigit(c)) {
                if (dotIndex == -1 || i - dotIndex > 1)
                    return i;
                else
                    throw new IllegalArgumentException(
                            "not a number, dot can't be the last part of a number.");
            } else if (i == len - 1) {
                return len;
            }
        }

        throw new IllegalArgumentException("not a number.");
    }

    /**
     * 使用正则方式
     */
    public static String patten(String str){
        //先判断有没有整数，如果没有整数那就肯定就没有小数
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(str);
        System.out.println("1m:"+m.toMatchResult());
        String result = "";
        if (m.find()) {
            Map<Integer, String> map = new TreeMap<>();
            Pattern p2 = Pattern.compile("(\\d+\\.\\d+)");
            m = p2.matcher(str);
            System.out.println("m:"+m);
            //遍历小数部分
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = str.indexOf(result);
                String s = str.substring(i, i + result.length());
                map.put(i, s);
                //排除小数的整数部分和另一个整数相同的情况下，寻找整数位置出现错误的可能，还有就是寻找重复的小数
                // 例子中是排除第二个345.56时第一个345.56产生干扰和寻找整数345的位置时，前面的小数345.56会干扰
                str = str.substring(0, i) + str.substring(i + result.length());
            }
            //遍历整数
            Pattern p3 = Pattern.compile("(\\d+)");
            m = p3.matcher(str);
            System.out.println("整数m:"+m);
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = str.indexOf(result);
                //排除jia567.23.23在第一轮过滤之后留下来的jia.23对整数23产生干扰
                if (String.valueOf(str.charAt(i - 1)).equals(".")) {
                    //将这个字符串删除
                    str = str.substring(0, i - 1) + str.substring(i + result.length());
                    continue;
                }
                String s = str.substring(i, i + result.length());
                map.put(i, s);
                str = str.substring(0, i) + str.substring(i + result.length());
            }
            result = "";
            for (Map.Entry<Integer, String> e : map.entrySet()) {
                result += e.getValue() + ",";
            }
            result = result.substring(0, result.length()-1);
        } else {
            result = "";
        }
        System.out.println(result);
        return result;
    }

}
