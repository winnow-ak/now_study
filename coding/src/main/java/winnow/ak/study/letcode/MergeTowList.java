package winnow.ak.study.letcode;


/**
 * @Author: Winyu
 * @Date: 2021/6/26
 */
public class MergeTowList {

    static class Node {
        Node next;
        Integer data;

        public Node(Integer data) {
            this.data = data;
        }
    }


    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node3;
        node3.next = node5;
        node2.next = node4;
        Node merge = MergeTowList.merge(node1, node2);
        while (merge !=null){
            Integer data = merge.data;
            merge = merge.next;
            System.out.print(data);
        }
    }

    //两个有序链表合并
    public static Node merge(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return null;
        }
        if (node1 == null) {
            return node2;
        }
        if (node2 == null) {
            return node1;
        }
        Node head = null;
        //当前节点数大于 第二个节点数
        if (node1.data > node2.data) {
            //小数放在头结点
            head = node2;
            head.next = merge(node1, node2.next);
        } else {
            head = node1;
            head.next = merge(node1.next, node2);
        }
        return head;
    }
}
