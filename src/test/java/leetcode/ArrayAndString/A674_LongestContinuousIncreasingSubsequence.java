package leetcode.ArrayAndString;

import org.junit.Test;

/**
 * \* Created: liuhuichao
 * \* Date: 2020/5/29
 * \* Time: 28:42 下午
 * \* Description: 最长连续递增序列
 * \
 * 给定一个未经排序的整数数组，找到最长且连续的的递增序列。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,3,5,4,7]
 * 输出: 3
 * 解释: 最长连续递增序列是 [1,3,5], 长度为3。
 * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为5和7在原数组里被4隔开。
 * 示例 2:
 * <p>
 * 输入: [2,2,2,2,2]
 * 输出: 1
 * 解释: 最长连续递增序列是 [2], 长度为1。
 * 注意：数组长度不会超过10000。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-continuous-increasing-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class A674_LongestContinuousIncreasingSubsequence {
    public int findLengthOfLCIS0(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int maxLength = 1;
        for (int i = 0; i < nums.length; i++) {
            if (i == nums.length - 1) {
                break;
            }
            int tempLong = 1;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i + 1] > nums[i] && nums[j] > nums[j - 1]) {
                    tempLong = j - i + 1;
                } else {
                    break;
                }
            }
            maxLength = Math.max(tempLong, maxLength);
        }
        return maxLength;
    }

    public int findLengthOfLCIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int begin = 0;
        int end = 1;
        int maxLength = 1;
        for (int i = 0; i < nums.length; i++) {
            if (end > nums.length - 1) {
                break;
            }
            if (nums[end] > nums[end - 1]) {
                maxLength = Math.max(end - begin + 1, maxLength);
                end++;
            } else {
                begin = i;
                end = begin + 1;
            }
        }
        return maxLength;
    }

    @Test
    public void test() {
        //int[] nums = {1, 3, 5, 4, 2, 3, 4, 5}; //4
        //int[] nums = {1, 3, 5, 4, 7};
        //int[] nums = {2,2,2,2,2};
        int[] nums = {1, 3, 5, 7};
        System.out.println(findLengthOfLCIS(nums));
    }
}
