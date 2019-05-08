import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        //BTree Tree=new BTree();
    /*    BTree t=new BTree(); // A B-Tree with minium degree 3
        t.DisplayIndexFileContent("");

        t.insert(1,1);

        t.insert(9,9);
        t.insert(10,10);
        t.insert(1,11);
        t.insert(7,7);
        t.insert(8,8);
        t.insert(6,6);
        t.insert(8,8);
        t.insert(9,9);
        t.insert(10,10);
        System.out.println(t.search(9));
        System.out.println(t.search(10));
        System.out.println(t.search(1));
        System.out.println(t.search(3));
        System.out.println(t.search(79));
*/
        //  t.insert(4);
        BTree x = new BTree();
        Scanner a = new Scanner(System.in);
        String cont = "Y";
        Boolean added = false;
        while (!cont.equals("N")) {
            System.out.println("1) insert record");
            System.out.println("2) Search");
            System.out.println("3) Display all");

            cont = a.next();
            if (cont.equals("N")) {
                break;
            } else if (cont.equals("1")) {
                added = true;
                System.out.println("Enter your key: ");
                int key = a.nextInt();

                System.out.println("Enter your offset: ");
                int offset = a.nextInt();
                x.InsertNewRecordAtIndex(key, offset);
            } else if (cont.equals("2")) {
                System.out.println("Enter your key: ");
                int key = a.nextInt();
                x.SearchRecordInIndex(key);
            } else if (cont.equals("3")) {
                x.DisplayIndexFileContent("");
            }


        }

    }
}
