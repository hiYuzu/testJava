package offer;

import java.util.*;

/**
 * @author hiYuzu
 * @version V1.0
 * @date 2020/4/15 14:25
 */
public class Solution {
    /**
     * 时间限制：2秒 空间限制：64M
     * 本题知识点： 查找 数组
     * 题目描述
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     * @param target 整数
     * @param array 二维数组
     * @return boolean
     */
    public boolean find(int target, int [][] array) {
        if(array == null || array.length == 0 || array[0].length == 0){
            return false;
        }
        int j = array[0].length - 1;
        int i = 0;
        while(i < array.length && j > -1){
            if(target == array[i][j]) {
                return true;
            } else if(target < array[i][j]) {
                j--;
            } else if(target > array[i][j]) {
                i++;
            }
        }
        return false;
    }

    /**
     * 时间限制：2秒 空间限制：64M
     * 本题知识点： 字符串
     * 题目描述
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     * @param str 字符串
     * @return String
     */
    public String replaceSpace(StringBuffer str) {
        int a = str.indexOf(" ");
        while (a != -1) {
            str = str.replace(a, a + 1, "%20");
            a = str.indexOf(" ", a + 1);
        }
        return str.toString();
    }

    /**
     * 时间限制：2秒 空间限制：64M
     * 本题知识点： 链表
     * 题目描述
     * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
     * @param listNode 链表
     * @return ArrayList
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        ArrayList<Integer> array = new ArrayList<>();
        while (!stack.empty()) {
            array.add(stack.pop());
        }
        return array;
    }

    /**
     * 时间限制：2秒 空间限制：64M
     * 本题知识点： 树
     * 题目描述
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     * @param pre 前序遍历
     * @param in 中序遍历
     * @return 原二叉树
     */
    private Map<Integer, Integer> indexIn = new HashMap<>();
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre == null || in == null) {
            return null;
        }
        for (int i = 0; i < in.length; i++) {
            indexIn.put(in[i], i);
        }
        return reConstructBinaryTree(pre, 0, pre.length - 1, 0);
    }
    private TreeNode reConstructBinaryTree(int[] pre, int preL, int preR, int inL) {
        if (preL > preR) {
            return null;
        }
        TreeNode treeNode = new TreeNode(pre[preL]);
        int index = indexIn.get(pre[preL]);
        int leftSize = index - inL;
        treeNode.left = reConstructBinaryTree(pre, preL + 1, preL + leftSize, inL);
        treeNode.right = reConstructBinaryTree(pre, preL + leftSize + 1, preR, inL + leftSize + 1);
        return treeNode;
    }

    /**
     * 时间限制：2秒 空间限制：64M
     * 本题知识点： 队列 栈
     * 题目描述
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();
    public void push(int node) {
        stack1.push(node);
    }
    public int pop() {
        if (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    /**
     * 时间限制：6秒 空间限制：64M
     * 本题知识点： 查找
     * 题目描述
     * 一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     * @param array 旋转后的数组
     * @return 最小元素
     */
    public int minNumberInRotateArray(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            int mid = (start + end) >>> 1;
            if (array[start] == array[mid] && array[mid] == array[end]) {
                //数组中有重复数字
                for (int i = start; i < end; i++) {
                    if (array[i] > array[i + 1]) {
                        return array[i + 1];
                    }
                }
                return array[start];
            }
            else if (array[mid] > array[end]) {
                start = mid + 1;
            }
            else {
                end = mid;
            }
        }
        return array[start];
    }

    /**
     * 时间限制：2秒 空间限制：64M
     * 本题知识点： 递归
     * 题目描述
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
     * n<=39n
     * @param n 自然数
     * @return 斐波那契数列第n项
     */
    public int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        /*
        int[] fib = new int[40];
        fib[1] = 1;
        for (int i = 2; i <= n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[n];
        */
        int fib = 0, bef = 0, aft = 1;
        for (int i = 2; i <= n; i++) {
            fib = bef + aft;
            bef = aft;
            aft = fib;
        }
        return fib;
    }

    /**
     * 时间限制：2秒 空间限制：64M
     * 本题知识点： 递归
     * 题目描述
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
     * n<=39n
     * @param target 台阶数
     * @return 跳法数量
     */
    public int jumpFloor(int target) {
        //n = 1 => 1
        //n = 2 => 2
        //n = 3 => 3 = 2 + 1
        //n = 4 => 5 = 3 + 2
        //=> 斐波那契数列
        if (target <= 2) {
            return target;
        }
        int fib = 0, bef = 1, aft = 2;
        for (int i = 3; i <= target; i++) {
            fib = bef + aft;
            bef = aft;
            aft = fib;
        }
        return fib;
    }

    /**
     * 时间限制：2秒 空间限制：64M
     * 本题知识点： 贪心
     * 题目描述
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     * n<=39n
     * @param target 台阶数
     * @return 跳法数量
     */
    public int jumpFloorII(int target) {
        //f(1) = 1
        //f(2) = f(1) + 1
        //f(3) = f(2) + f(1) + 1
        //f(4) = f(3) + f(2) + f(1) + 1
        //f(n) = f(n - 1) + f(n - 2) + ... + f(1) + 1 = f(n - 1) + f(n - 1) = 2 * f(n - 1)
        //TODO..
        return 0;
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
    }
}