/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
#pragma GCC optimize("O3", "unroll-loops")
class Solution {
public:
    ListNode* mergeInBetween(ListNode* list1, int a, int b, ListNode* list2) {
        ListNode* head=list1, *curr=list1;
        for(int i=1; i<a; i++)
            curr=curr->next;

        ListNode* rm_start = curr; // node before the section to be removed
        ListNode* rm_end = curr->next; // first node to be removed
        
        // Move rm_end to the last node of the section to be removed
        for (int i= a; i<=b; i++) {
            ListNode* tmp = rm_end;
            rm_end = rm_end->next;
            delete tmp; // Deallocate memory for each removed node
        }

        // Connect the remaining part of list1 to list2
        rm_start->next = list2;
        
        // Move curr to the end of list2
        for (; curr->next; curr=curr->next);
           
        
        // Connect the remaining part of list1 after the removed section
        curr->next = rm_end;
        
        return head;
    }
};


auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();