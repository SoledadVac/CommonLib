package leetcode.ArrayAndString;

import org.apache.xmlbeans.impl.tool.XSTCTester;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* Created: liuhuichao
 * \* Date: 2019/2/19
 * \* Time: 5:41 PM
 * \* Description: 最长回文子串
 * <p>
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * <p>
 * 示例 1：
 * <p>
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 * <p>
 * 输入: "cbbd"
 * 输出: "bb"
 */
public class A5_LongestPalindromicSubstring {

    /**
     * 最长公共子串
     * ---------暴力破解方法（三次方的时间复杂度。。额。。）
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        if (s.length() == 1) {
            return s;
        }
        int length = s.length();
        String result = s.substring(0, 1);
        for (int i = 0; i < length; i++) {
            if (i == length - 1) {
                break;
            }
            for (int j = i + 1; j < length; j++) {
                if (ifStringIsBalanced(s, i, j)) {
                    if (j - i + 1 > result.length()) {
                        result = s.substring(i, j) + s.charAt(j);
                    }
                }

            }
        }
        return result;

    }

    /**
     * 包含起始位置和结束位置，判断是否对称
     *
     * @param s
     * @param begin 起始位置
     * @param end   结束位置
     * @return
     */
    private boolean ifStringIsBalanced(String s, int begin, int end) {
        if (begin == end) {
            return true;
        }
        String spiltString = s.substring(begin, end) + s.charAt(end);
        for (int i = 0; i < spiltString.length() - 1; i++) {
            if (spiltString.charAt(i) != spiltString.charAt(spiltString.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 最长公共子串 --- 动态规划法
     *
     * @param s
     * @return
     */
    public String longestPalindrome1(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        if (s.length() == 1) {
            return s;
        }
        int n = s.length();//总长度
        boolean[][] dpTable = new boolean[n][n];//表格
        int begin = 0;
        int end = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    // (i,j)代表这个区间内，并且需要j>=i
                    continue;
                }
                //初始化值
                if (i == j) {
                    dpTable[i][j] = true;
                    if ((j - i) > (end - begin)) {
                        begin = i;
                        end = j;
                    }
                    continue;
                }
                if (s.charAt(i) != s.charAt(j)) {
                    //头尾部不相等肯定不是回文
                    dpTable[i][j] = false;
                    continue;
                }
                if (i + 1 == j) {
                    //两个连续字母
                    dpTable[i][j] = true;
                    if ((j - i) > (end - begin)) {
                        begin = i;
                        end = j;
                    }
                    continue;
                }
                //头尾部相等，需要判断中间部分是不是
                if (dpTable[i + 1][j - 1]) {
                    dpTable[i][j] = true;
                    if ((j - i) > (end - begin)) {
                        begin = i;
                        end = j;
                    }
                    continue;
                }
                dpTable[i][j] = false;
            }
        }
        return s.substring(begin, end + 1);
    }

    /**
     * 中心扩散法
     *
     * @param s
     * @return
     */
    public String longestPalindrome2(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        if (s.length() == 1) {
            return s;
        }
        String result = s.substring(0, 1);
        //分别以每个值作为中心点往外扩散看能扩散多远
        for (int i = 0; i < s.length() - 1; i++) {
            String oddStr = getCenter(s, i, true);//以中心点为奇数长度时候
            String evenStr = getCenter(s, i, false);//以中新店为偶数时候
            String maxStr = oddStr.length() > evenStr.length() ? oddStr : evenStr;
            result = maxStr.length() > result.length() ? maxStr : result;
        }
        return result;
    }

    public String getCenter(String s, int centerIndex, boolean isOdd) {
        int left;
        int right;
        if (isOdd) {
            //奇数长度，中心点为centerIndex
            left = centerIndex;
            right = centerIndex;
        } else {
            //偶数长度
            left = centerIndex;
            right = centerIndex + 1;
        }
        while (left >= 0 && right <= s.length() - 1) {
            if (s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            } else {
                break;
            }

        }
        return s.substring(left + 1, right);
    }

    /**
     * Manacher 算法 n方
     *
     * @param s
     * @return
     */
    public String longestPalindrome3(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        if (s.length() == 1) {
            return s;
        }
        String newS = wrapStrings(s);
        int maxStep = 1;
        int start = 0;
        for (int i = 0; i < newS.length(); i++) {
            int step = getStep(newS, i);
            if (step > maxStep) {
                maxStep = step;
                start = (i - maxStep) / 2;
            }
        }
        return s.substring(start, start + maxStep);
    }

    /**
     * 将字符串包装为奇数个字符（n个 -> 2n+1个） ： aba -> #a#b#a#
     *
     * @param s
     * @return
     */
    public String wrapStrings(String s) {
        StringBuilder sb = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            sb.append(c);
            sb.append("#");
        }
        return sb.toString(); //奇数个字符
    }

    /**
     * 获取从中心点向两边扩散的的最大步长
     *
     * @param s
     * @param centerIndex
     * @return
     */
    public int getStep(String s, int centerIndex) {
        int left = centerIndex - 1;
        int right = centerIndex + 1;
        int step = 0;
        while (left >= 0 && right <= s.length() - 1) {
            if (s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                step++;
            } else {
                break;
            }
        }
        return step;
    }


    /**
     * Manacher -- n复杂度算法
     *
     * @param s
     * @return
     */
    public String longestPalindrome4(String s) {
        // 特判
        int len = s.length();
        if (len < 2) {
            return s;
        }
        String T = processS(s);
        int n = T.length();
        int[] P = new int[n];
        int C = 0, R = 0;
        for (int i = 1; i < n - 1; i++) {
            int i_mirror = 2 * C - i;
            if (R > i) {
                P[i] = Math.min(R - i, P[i_mirror]);// 防止超出 R
            } else {
                P[i] = 0;// 等于 R 的情况
            }

            // 碰到之前讲的三种情况时候，需要利用中心扩展法
            while (T.charAt(i + 1 + P[i]) == T.charAt(i - 1 - P[i])) {
                P[i]++;
            }

            // 判断是否需要更新 R
            if (i + P[i] > R) {
                C = i;
                R = i + P[i];
            }

        }
        // 找出 P 的最大值
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
        }
        int start = (centerIndex - maxLen) / 2; //最开始讲的求原字符串下标
        return s.substring(start, start + maxLen);
    }


    /**
     * 处理字符串
     *
     * @param s
     * @return
     */
    public String processS(String s) {
        StringBuilder sb = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            sb.append(c);
            sb.append("#");
        }
        return sb.toString(); //奇数个字符
    }


    public String longestPalindrome5(String s) {
        // 特判
        int len = s.length();
        if (len < 2) {
            return s;
        }
        String T = processS(s);
        int n = T.length();
        int[] p = new int[n];
        int centerIndex = 0, maxRightIndex = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || i == n - 1) {
                p[i] = 0;
                continue;
            }
            int iMirror = 2 * centerIndex - i;
            if (i > maxRightIndex) {
                //中心扩散吧
                int left = i - 1;
                int right = i + 1;
                while (left >= 0 && right < n) {
                    if (T.charAt(left) == T.charAt(right)) {
                        left--;
                        right++;
                        p[i]++;
                    } else {
                        break;
                    }
                }
                centerIndex = i;
                maxRightIndex = p[i];
                continue;
            }
            // i < maxRightIndex
            //1， ` p[iMirror] < maxRightIndex - i` 时候,p[i]=p[iMirror]
            if (p[iMirror] < maxRightIndex - i) {
                p[i] = p[iMirror];
                continue;
            }
            //2， ` p[iMirror] = maxRightIndex - i` 时候,p[i]至少为maxRightIndex - i，此时需要以maxRightIndex进行中心扩散法计算。
            if (p[iMirror] == maxRightIndex - i) {
                p[i] = p[iMirror];
                int left = i - p[i];
                int right = i + p[i];
                while (left >= 0 && right < n) {
                    if (T.charAt(left) == T.charAt(right)) {
                        left--;
                        right++;
                        p[i]++;
                    } else {
                        break;
                    }
                }
                centerIndex = i;
                maxRightIndex = i + p[i];
                continue;
            }
            //3， ` p[iMirror] > maxRightIndex - i` 时候,根据centerIndex的对称性，p[i]的最大值，只能是`maxRightIndex - i`,不能再多。
            if (p[iMirror] > maxRightIndex - i) {
                p[i] = maxRightIndex - i;
            }
        }
        int center = 0;
        int step = 0;
        for (int i = 0; i < p.length; i++) {
            if (p[i] > step) {
                center = i;
                step = p[i];
            }
        }
        int start = (center - step) / 2;
        return s.substring(start, start + step);
    }

    @Test
    public void test() {
        //babad
        //bb
        //babad

        String s0 = "bab"; // bab
        // String s0 = "ccc"; // bab
        System.out.println(longestPalindrome5(s0));

        /*  String s0 = "aledadi"; // idadela
        //           0123456   // 0123456

        System.out.println(longestPalindrome1(s0));*/

    }


}
