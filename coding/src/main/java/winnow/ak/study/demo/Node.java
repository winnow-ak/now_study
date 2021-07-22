package winnow.ak.study.demo;

/**
 * @Author: Winyu
 * @Date: 2021/6/24
 */
public class Node {

    private Object data;
    private Node next;

    public Node(Object data,Node next){
        this.data = data;
        this.next = next;
    }

    public static void main(String[] args) {
        //头结点
        Node head = new Node(new Object(),null);
        //新头结点
       Node header =  new Node(new Object(),head);


    }
}
