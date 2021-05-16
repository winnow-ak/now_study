package winnow.ak.study.datastructure;


/**
 * @Author: Winyu
 * @Date: 2020/11/29
 */
public class SingleLinkedList<T> {


    class Node {
        private  Object data;
        private Node next;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node firstNode = null;
    private int size;




    public void addFirst(T element){
        Node node = new Node();
        node.setData(element);
        Node currentFirst = firstNode;
        node.setData(currentFirst);
        firstNode = node;
        size ++ ;
    }


    public boolean remove(T element){
        if(size ==0){
            return false;
        }
        if (size == 1){
            firstNode = null;
            size --;
        }

        Node pre = firstNode;
        Node current = firstNode.getNext();
        while (current !=null){
            if ((current.getData() == null && element == null) || (current.getData().equals(element))){
                pre.setNext(current.getNext());
                size--;
                return true;
            }

            pre = current;
            current = current.getNext();
        }
        return false;
    }
}
