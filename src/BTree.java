import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class BTree {
    Node root;
    int limit;
    RandomAccessFile datafile;

    // The main function that inserts a new key in this B-Tree
    BTree() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your nmber of records: ");
        limit = in.nextInt();
        System.out.println("Enter file name: ");
        String Filename = in.next();
        Node.limit = limit;
        CreateRecordsFile(Filename, limit);
    }

    void DisplayIndexFileContent(String filename) {
        try {

            datafile.seek(0);
            System.out.println("-----------------------------------------------------------------------------");
            System.out.printf("%10s %10s %10s %10s %10s %10s %10s %10s", "F", "P", "K", "O", "P", "K", "O", "P");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------");

            for (int o = 0; o < limit; o++) {
                Node.printRec(o);
            }
            System.out.println("-----------------------------------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void CreateRecordsFile(String filename, int numberOfRecords) {
        try {
            datafile = new RandomAccessFile(filename + ".bin", "rw");
            Node.datafile = datafile;
            General.datafile = datafile;
            for (int counter = 1; counter < numberOfRecords; counter++) {
                datafile.writeInt(-1);
                datafile.writeInt(-1);
                datafile.writeInt(counter);
                datafile.writeInt(-1);
                datafile.writeInt(-1);
                datafile.writeInt(-1);
                datafile.writeInt(-1);
                datafile.writeInt(-1);
            }
            datafile.writeInt(-1);
            datafile.writeInt(-1);
            datafile.writeInt(-1);
            datafile.writeInt(-1);
            datafile.writeInt(-1);
            datafile.writeInt(-1);
            datafile.writeInt(-1);
            datafile.writeInt(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int isempty(RandomAccessFile datafile) {
        updateempty(datafile);
        int next = -1;
        try {
            datafile.seek(8);
            next = datafile.readInt();
            return next;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return next;
        /// go to the first record and check  -1 is empty else return record to insert in
    }


    void InsertNewRecordAtIndex(int k, int offset) {

        int empty = isempty(datafile);
        // If tree is full
        // if (empty==-1){
        //       System.out.println("FULL");return;}
        if (empty == 1 && root == null) //empty
        { /// write f awl records bas ya amar
            // Allocate memory for root
            root = new Node(0);
            root.keys.add(0, k); // Insert key
            root.offset.add(0, offset);
            root.n = 1; // Update number of keys in root
            root.indx = 1;
            General.write(isempty(datafile), root);

        } else // If tree is not empty
        {
            root.getChildren();
            root.getkeys();
            root.getoffset();
            // If root is full, then tree grows in height
            Node pos = root.search(k);

            if (pos.getkeys().indexOf(-1) == -1 && empty != -1) {  // Node.printRec(pos);
                //     System.out.println("dakhl hna");
                pos.splitChild(k, offset, pos);
            } else if (pos.getkeys().indexOf(-1) != -1) {// If root is not full, call insertNonFull for root
                pos.insertNonFull(k, offset);
            } else {
                System.out.println("full, bai bai");
            }
            updateempty();
        }
        System.out.println("insertion is done");

        //  DisplayIndexFileContent("");
        DisplayIndexFileContent("");
    }

    void updateempty() {
        try {
            datafile.seek(General.recsize);
            for (int i = 1; i < limit; i++) {
                Node a = Node.readNode();
                if (a.offset.get(0) == -1) {
                    datafile.seek(8);
                    datafile.writeInt(i);
                    return;
                }

            }
            datafile.seek(8);
            datafile.writeInt(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void updateempty(RandomAccessFile datafile) {
        try {
            datafile.seek(General.recsize);
            for (int i = 1; i < Node.limit; i++) {
                Node a = Node.readNode();
                if (a.offset.get(0) == -1) {
                    datafile.seek(8);
                    datafile.writeInt(i);
                    return;
                }

            }
            datafile.seek(8);
            datafile.writeInt(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void SearchRecordInIndex(int k) {
        System.out.println(root.SearchRecordInIndex(k));
    }

}
