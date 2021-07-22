package winnow.ak.study.demo;

/**
 * @Author: Winyu
 * @Date: 2021/5/26
 */
public class User {
    private Integer userId;
    protected Integer id;
    public String name;
    String addr;

    public static void main(String[] args) {
        String a = "Aa";
        String b = "BB";
        int i = a.hashCode();
        int i2 = b.hashCode();
        boolean equals = a.equals(b);
        System.out.println(i);
        System.out.println(i2);
        System.out.println(i == i2);
        System.out.println(equals);
        System.out.println(a==b);

        // hashCode 相等，equals 不一定相等
        //
    }

    public static void add(){
        System.out.println("add");
    }

    private static void update(){
        System.out.println("update");
    }

    protected static void delete(){
        System.out.println("delete");
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
