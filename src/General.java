import java.io.IOException;
import java.io.RandomAccessFile;

public class General {
    static int recsize = 32;
    static public RandomAccessFile datafile;

    static void KeyinsertAt(int current, int indx, int value, int offset) {
        try {
            // System.out.println("KEYS before inserting "+value+" at "+current+" with pos "+indx);
            //    DisplayIndexFileContent("");
            if (indx == 0) {
                datafile.seek(recsize * (current) + (4 * 2));
            } else if (indx == 1) {
                datafile.seek(recsize * (current) + (4 * 5));
            }
            datafile.writeInt(value);
            datafile.writeInt(offset);
            //   DisplayIndexFileContent("");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void Childi
    nsertAt(int current, int indx, int value) {
        try {
            //System.out.println("children before inserting "+value+" at "+current+" with pos "+indx);
            //   DisplayIndexFileContent("");
            if (indx == 0) {
                datafile.seek(recsize * (current) + (4 * 1));
            } else if (indx == 1) {
                datafile.seek(recsize * (current) + (4 * 4));
            } else if (indx == 2) {
                datafile.seek(recsize * (current) + (4 * 7));
            }
            datafile.writeInt(value);
            //     DisplayIndexFileContent("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void DisplayIndexFileContent(String filename) {
        try {

            datafile.seek(0);
            System.out.println("-----------------------------------------------------------------------------");
            System.out.printf("%10s %10s %10s %10s %10s %10s %10s %10s", "F", "P", "K", "O", "P", "K", "O", "P");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------");

            for (int o = 0; o < 4; o++) {
                Node.printRec(o);
            }
            System.out.println("-----------------------------------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static void write(int indx, Node r) {
        try {
            datafile.seek(indx * recsize);
            Node.writeRec(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Node getChild(int indx) {
        try {
            datafile.seek(recsize * indx);
            Node a = Node.readNode();
            a.indx = indx;
            //      Node.printRec(a);
            return a;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
