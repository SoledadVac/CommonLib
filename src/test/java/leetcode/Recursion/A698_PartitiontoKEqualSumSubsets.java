package leetcode.Recursion;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * \* Created: liuhuichao
 * \* Date: 2019/9/26
 * \* Time: 30:36 PM
 * \* Description: 划分为k个相等的子集
 * \
 * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
 * <p>
 * 示例 1：
 * <p>
 * 输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * 输出： True
 * 说明： 有可能将其分成 4 个子集（5），（1,4），（2,3），（2,3）等于总和。
 *  
 * <p>
 * 注意:
 * <p>
 * 1 <= k <= len(nums) <= 16
 * 0 < nums[i] < 10000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-to-k-equal-sum-subsets
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class A698_PartitiontoKEqualSumSubsets {

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        int avg;
        for (int n : nums) {
            sum += n;
        }
        if (sum % k != 0) {
            return false;
        }
        avg = sum / k;



        return true;
    }

    @Test
    public void test() {
      /*  int[] nums = {4, 3, 2, 3, 5, 2, 1};
        int k = 4;*/
        int[] nums = {10, 10, 10, 7, 7, 7, 7, 7, 7, 6, 6, 6};
        int k = 3;
        System.out.println(canPartitionKSubsets(nums, k));
    }

}
