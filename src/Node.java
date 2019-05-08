import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.Vector;

public class Node {
    static int limit;
    int indx,counter=0;
    int recsiz=32,F,n;
    static RandomAccessFile datafile;
    Vector<Integer> keys=new Vector();
    Vector<Integer> offset=new Vector<>();
    Vector<Integer> Children=new Vector();

    Node(int leaf1)
    {
        Children.add(-1);
        Children.add(-1);
        Children.add(-1);
        keys.add(-1);
        keys.add(-1);
        offset.add(-1);
        offset.add(-1);
        // Copy the given minimum degree and leaf property
        F = leaf1;
        // Initialize the number of keys as 0
        n = 0;
    }
    Node(int a, int b, int c, int d, int e, int f, int g, int h){
       /* value=v;
        offset=o;
        left=l;
        right=r;
   */
       F=a;
       Children.clear();
       Children.add(b);
       Children.add(e);
       Children.add(h);
       keys.clear();
       keys.add(c);
       keys.add(f);
       offset.clear();
       offset.add(d);
       offset.add(g);
    }
    public Boolean LeafOrNot(){
        if (F==0){return  true;}
        else return false;
    }
    public Vector<Integer> getoffset(){
        if (offset.isEmpty()){return  null;}
        try {
            offset.clear();
            datafile.seek((indx)*recsiz+3*4);
            offset.add(datafile.readInt());
            datafile.seek((indx)*recsiz+6*4);
            offset.add(datafile.readInt());
            return offset;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  offset;

//        return null;
    }
    public Vector<Integer> getkeys(){
        if (keys.isEmpty()){return  null;}
        try {
            keys.clear();
            datafile.seek((indx)*recsiz+2*4);
            keys.add(datafile.readInt());
            datafile.seek((indx)*recsiz+5*4);
            keys.add(datafile.readInt());
            return keys;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  keys;

//        return null;
    }
    public Vector<Integer> getChildren(){
        if (Children.isEmpty()){return  null;}
        try {
            Children.clear();
            datafile.seek((indx)*recsiz+1*4);
            Children.add(datafile.readInt());
            datafile.seek((indx)*recsiz+4*4);
            Children.add(datafile.readInt());
            datafile.seek((indx)*recsiz+7*4);
            Children.add(datafile.readInt());
            return Children;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  Children;
    }
    static Node readNode(){
        try {
            return new Node(datafile.readInt(),datafile.readInt(),datafile.readInt(),datafile.readInt(),datafile.readInt(),datafile.readInt(),datafile.readInt(),datafile.readInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    static void writeRec(Node a){
        try {
            datafile.writeInt(a.F); datafile.writeInt(a.Children.get(0)); datafile.writeInt(a.keys.get(0)); datafile.writeInt(a.offset.get(0)); datafile.writeInt(a.Children.get(1)); datafile.writeInt(a.keys.get(1)); datafile.writeInt(a.offset.get(1)); datafile.writeInt(a.Children.get(2));
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    static void printRec(int indx){
        Node a=readNode();
        if (a!=null) {
            System.out.format("%10s %10s %10s %10s %10s %10s %10s %10s ",a.F,a.Children.get(0),a.keys.get(0),a.offset.get(0),a.Children.get(1),a.keys.get(1),a.offset.get(1),a.Children.get(2));
            System.out.println();
        }
    }
    static void printRec(Node a){
        if (a!=null) {
            System.out.format("%10s %10s %10s %10s %10s %10s %10s %10s ",a.F,a.Children.get(0),a.keys.get(0),a.offset.get(0),a.Children.get(1),a.keys.get(1),a.offset.get(1),a.Children.get(2));
            System.out.println();
        }
    }
    //void remove
    public static void removeDuplicates(Vector v)

    {

        for(int i=0;i<v.size();i++)

        {

            for(int j=0;j<v.size();j++)

            {

                if(i!=j)

                {

                    if(v.elementAt(i).equals(v.elementAt(j)))

                    {

                        v.removeElementAt(j);

                    }

                }

            }

        }
    }

    void orderchildren() {
        removeDuplicates(Children);
        Vector<Node> children = new Vector<>();
        for (int i : Children) {
            //System.out.println("inside : "+i);
            if (i < 0) {
                continue;
            }
            Node a = General.getChild(i);
            if (a != null)
                children.add(a);
        }
        int c, d;
        Node swap;
        int n = children.size();

        if (n > 1)
        {       for (c = 0; c < (n - 1); c++) {
                for (d = 0; d < n - c - 1; d++) {
                    if (children.get(d).keys.get(0) > children.get(d + 1).keys.get(0)) {
                        swap = children.get(d);
                        children.set(d, children.get(d + 1));
                        children.set(d + 1, swap);
                    }
                }

            }
        Children.clear();
        for (Node a : children) {
            Children.add(a.indx);
        }
        int diff=3-Children.size();
        for (int o=0;o<diff;o++){
            Children.add(-1);
        }
    }

    }
    Node getparent(int indx){
        try {
            datafile.seek(recsiz);
            for (int i=1;i<limit;i++){
                Node result=readNode();
                result.indx=i;

                if (result.Children.indexOf(indx)!=-1){return result;}

            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    void sorter(Vector<Integer> v,Vector<Integer> offset){
        int c,d,swap;
        int n=v.size();
        for (c = 0; c < (n - 1); c++) {
            for (d = 0; d < n - c - 1; d++) {
                if (v.get(d) > v.get(d + 1)) {
                    swap = v.get(d);
                    v.set(d, v.get(d + 1));
                    v.set(d + 1, swap);
                    swap = offset.get(d);
                    offset.set(d, offset.get(d + 1));
                    offset.set(d + 1, swap);
                }
            }
        }
        if (v.get(0)==-1&&v.size()==3){v.remove(0);
        offset.remove(0);}

    }
    void updateLeaf(){
        try {
            datafile.seek(indx*recsiz);
      //      System.out.println(Children);
            if (Children.get(0)!=-1){
                F=1;
                //datafile.writeInt(1);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
///len is number of current records/nodes inserted___ increment it
    void splitChild(int i,int newoffset, Node y) {  ///////zabaty el indexes
        //another function to know which  child to split
        getChildren();
        getkeys();
        getoffset();
        /// i is the bigger one,0 is small,1 is mid,
        y.keys.add(i);
        y.offset.add(newoffset);
        sorter(y.keys,y.offset);
        i=y.keys.get(2);
        newoffset=y.offset.get(2);
        y.keys.remove(2);
        y.offset.remove(2);
       // System.out.println("LOL");
        Node parent=getparent(y.indx);
     //   System.out.println("parent:");
        if (parent!=null) {
       //     printRec(parent);
         /*  if (len+1>=limit){
               return false;
           }*/
            if (parent.keys.indexOf(-1) == -1) { //full parent  - to handle
               // System.out.println("full parent");
         /*       Node smaller = new Node(y.F);
                smaller.n = 1;
                smaller.indx = y.indx + 1;
                smaller.keys.add(0, y.keys.get(0));
                smaller.offset.add(0,y.offset.get(0));
                smaller.Children.add(0, y.Children.get(0));
                Node bigger = new Node(y.F);
                bigger.n = 1;
                bigger.indx = y.indx + 2;
                bigger.keys.add(0, i); //insertion = the newly added third value
                bigger.offset.add(0,newoffset);
                bigger.Children.add(0, y.Children.get(2));
            */
                Node smaller = new Node(y.F);
                smaller.n = 1;
                smaller.indx = y.indx;
                smaller.keys.add(0, y.keys.get(0));
                smaller.offset.add(0,y.offset.get(0));
                smaller.Children.add(0, y.Children.get(0));
                Node bigger = new Node(y.F);
                bigger.n = 1;
                bigger.indx = BTree.isempty(datafile);
             //   System.out.println("bigger index : "+bigger.indx);
                bigger.keys.add(0, i); //insertion = the newly added third value
                bigger.offset.add(0,newoffset);
                bigger.Children.add(0, y.Children.get(2));
           //     int mid=y.keys.get(1);

                try {
             //       DisplayIndexFileContent("");

                    datafile.seek((smaller.indx) * recsiz);
                    writeRec(smaller);
                    datafile.seek((bigger.indx ) * recsiz);
                    writeRec(bigger);

                    //            return true;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                parent.Children.add(bigger.indx);
        parent.orderchildren();
                parent.updateLeaf();

                splitChild(y.keys.get(1),y.offset.get(1),parent);

                //      return splitChild(y.keys.get(1),parent);
            } else {

                Node smaller = new Node(y.F);
                smaller.n = 1;
                smaller.indx = y.indx;
                smaller.keys.add(0, y.keys.get(0));
                smaller.offset.add(0,y.offset.get(0));
                smaller.Children.add(0, y.Children.get(0));
                Node bigger = new Node(y.F);
                bigger.n = 1;
                bigger.indx = BTree.isempty(datafile);
                bigger.keys.add(0, i); //insertion = the newly added third value
                bigger.offset.add(0,newoffset);
                bigger.Children.add(0, y.Children.get(2));
                int mid=y.keys.get(1);
                if (mid>parent.keys.get(0)){
                    parent.keys.add(1,mid);
                    parent.offset.add(1,y.offset.get(1));
                    parent.Children.add(1,smaller.indx);
                    parent.Children.add(2,bigger.indx);
                }
                else {
                    parent.keys.add(1,parent.keys.get(0));
                    parent.offset.add(1,parent.offset.get(0));
                    parent.keys.add(0,mid);
                    parent.offset.add(0,y.offset.get(1));
                    parent.Children.add(smaller.indx);
                    parent.Children.add(bigger.indx);


                }
                try {

                    datafile.seek((smaller.indx) * recsiz);
                    writeRec(smaller);
                    datafile.seek((bigger.indx ) * recsiz);
                    writeRec(bigger);
                    parent.orderchildren();
                    parent.updateLeaf();
                    datafile.seek((parent.indx ) * recsiz);
                    writeRec(parent);
                    //    return true;
              //      System.out.println("after spacy parent");
            //        DisplayIndexFileContent("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ///if we'll add to the parent then we pass it too a split again if it's full, else add normally
        }
        else {
          //  System.out.println("root");
        /*    if (len+2>=limit){
                return false;
            }*/
            Node smaller = new Node(y.F);
            smaller.n = 1;
            smaller.indx = BTree.isempty(datafile);
            smaller.keys.add(0, y.keys.get(0));
            smaller.offset.add(0,y.offset.get(0));
            smaller.Children.add(0, y.Children.get(0));
            smaller.Children.add(1,y.Children.get(1));
            Node bigger = new Node(y.F);
            bigger.n = 1;
            bigger.indx = smaller.indx + 1;
            bigger.keys.add(0, i); //insertion = the newly added third value
            bigger.offset.add(0,newoffset);
            bigger.Children.add(0, y.Children.get(2));
            if (y.Children.size()==4){bigger.Children.add(1,y.Children.get(3));}
            int mid = y.Children.get(1);
            y.Children.clear();
            y.Children.add(smaller.indx);
            y.Children.add(bigger.indx);
            //y.Children.add(-1);

            int midkey = y.keys.get(1),midoffset=y.offset.get(1);
            y.keys.clear();
            y.keys.add(midkey);
            y.keys.add(-1);
            y.offset.clear();
            y.offset.add(midoffset);
            y.offset.add(-1);
            try {

                datafile.seek((smaller.indx) * recsiz);
                writeRec(smaller);
                datafile.seek((bigger.indx) * recsiz);
                writeRec(bigger);
                y.orderchildren();
                y.updateLeaf();


                datafile.seek((y.indx) * recsiz);
                writeRec(y);
             //   return true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      //  return false;
    }
    void insertNonFull(int k,int newoffset)
    {
        getChildren();
        getkeys();
        keys.add(1,k);
        offset.add(1,newoffset);
        sorter(keys,offset);
        try {
            datafile.seek(indx*recsiz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeRec(this);
        /*// Initialize index as index of rightmost element
        int i = n-1;

        // If this is a leaf node
        if (F == 0)
        {
            System.out.println("-----------------------------------");
            // The following loop does two things
            // a) Finds the location of new key to be inserted
            // b) Moves all greater keys to one place ahead
            while (i >= 0 && keys.get(i) > k)
            {
                General.KeyinsertAt(indx,i+1,keys.get(i),offset.get(i));
              //  DisplayIndexFileContent("");
                i--;
            }

            // Insert the new key at found location
            General.KeyinsertAt(indx,i+1,k,newoffset);
            n = n+1;
            System.out.println("-----------------------------------");

        }
       /* else // If this node is not leaf
        {
            // Find the child which is going to have the new key
            while (i >= 0 && keys.get(i) > k)
                i--;

            // See if the found child is full
            if (General.getChild(Children.get(i+1)).keys.indexOf(-1)== -1)
            {
                // If the child is full, then split it
                splitChild(i+1,, General.getChild(Children.get(i+1)));

                // After split, the middle key of C[i] goes up and
                // C[i] is splitted into two. See which of the two
                // is going to have the new key
                if (keys.get(i+1) < k)
                    i++;
            }
            General.getChild(Children.get(i+1)).insertNonFull(k);
        }*/
    }
    Node search(int k)
    {
        getkeys();
        //getoffset();
        getChildren();


        int i = 0;
        while (i < 2 && k > keys.get(i)) {
            if (keys.get(i) == k)
                return this;
            i++;
        }
       // System.out.println("rn : ");
     //   printRec(this);
        // If key is not found here and this is a leaf node
        if (Children.get(0) == -1) {
               return this;
        }
        // Go to the appropriate child

        if (k<=keys.get(0)){
          //  System.out.println("or here");
            return General.getChild(Children.get(0)).search(k);
        }
        else if (keys.get(1)!=-1&&k>keys.get(1)&&Children.get(2)!=-1) {
            //System.out.println("or is it? ");
            return General.getChild(Children.get(2)).search(k);
        }
        else {
          // System.out.println("maybe here");
            return General.getChild(Children.get(1)).search(k);
        }

    }
    int SearchRecordInIndex(int k)
    {
        getkeys();
        getChildren();


        int i = 0;
        while (i < 2 && k >= keys.get(i)) {
         //   System.out.println(k+" vssss "+keys.get(i));
            if (keys.get(i) == k)
                return offset.get(i);
            i++;
        }
      //  System.out.println("rn : ");
    //    printRec(this);
        //System.out.println(Children.get(0));
        // If key is not found here and this is a leaf node
        if (Children.get(0) == -1) {

            return -1;
        }
        // Go to the appropriate child

        if (k<keys.get(0)){
            //System.out.println("or here");
            return General.getChild(Children.get(0)).SearchRecordInIndex(k);
        }
        else if (keys.get(1)!=-1&&k>keys.get(1)&&Children.get(2)!=-1) {
            //System.out.println("or is it? ");
            return General.getChild(Children.get(2)).SearchRecordInIndex(k);
        }
        else {
            //System.out.println("maybe here");
            return General.getChild(Children.get(1)).SearchRecordInIndex(k);
        }


    }
}
//y.Children.clear();

///rewrite y keys with the middle as the only key and pointing to smaller and bigger
/*
        getChildren();
        getkeys();
        // Create a new node which is going to store (t-1) keys
        // of y
        Node z = new Node(y.F);
        z.n = 1;  ///minimum degree = t
        // Copy the last (t-1) keys of y to z
        z.keys = z.getkeys();
        z.Children=z.getChildren();
        General.KeyinsertAt(z.indx,0,y.keys.lastElement());
        if (y.F == 1) {
            General.ChildinsertAt(z.indx,0,y.Children.lastElement());
        }
        y.n --;
        // Since this node is going to have a new child,
        // create space of new child
        for (int j = n; j >= i + 1; j--) {
            General.ChildinsertAt(indx,j+1,y.Children.get(j));
        }
        // Link the new child to this node
        General.ChildinsertAt(indx,i+1,z.indx);
        // A key of y will move to this node. Find location of
        // new key and move all greater keys one space ahead
        for (int j = n - 1; j >= i; j--){
            General.KeyinsertAt(indx,j+1,y.keys.get(j));
        }
        // Copy the middle key of y to this node
        General.ChildinsertAt(indx,i,y.keys.get(1));
        // Increment count of keys in this node
        n = n + 1;*/