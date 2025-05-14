class Solution:
    def minSteps(self, s: str, t: str) -> int:
        freq=[0]*26
        for c in s:
            freq[ord(c)-ord('a')]+=1
        for c in t:
            freq[ord(c)-ord('a')]-=1
        ans=0
        for i in range(26):
            if freq[i]>0:
                ans+=freq[i]
        return ans