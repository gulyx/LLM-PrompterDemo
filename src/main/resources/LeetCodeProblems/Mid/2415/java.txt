/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode reverseOddLevels(TreeNode root) {
        if(root == null)
            return root;
        
        Queue<TreeNode> queue = new LinkedList<>();
        int level = 0;

        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;

            TreeNode[] arr = new TreeNode[size];

            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                arr[i] = cur;
                
                if(cur.left != null){
                    queue.add(cur.left);
                    queue.add(cur.right);
                }
            }

            if (level % 2 == 0) {
                int left = 0;
                int right = size - 1;

                while (left < right) {
                    int leftVal = arr[left].val;
                    arr[left].val = arr[right].val;
                    arr[right].val = leftVal;

                    left++;
                    right--;
                }
            }
        }

        return root;
    }
}