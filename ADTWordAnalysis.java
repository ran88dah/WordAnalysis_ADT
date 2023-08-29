import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ADTWordAnalysis {

    LinkedList<WordInformation>[] arrayOfDifferentLengths;
    WordInformation[] sortedArray;
    int n;
    int m;

    ADTWordAnalysis() {
    }

    //method to read the input file and initialize the arrays
    public void readFileAndAnalyse(int k, File input) throws FileNotFoundException {
        //init. first array
        arrayOfDifferentLengths = new LinkedList[k];
        for (int i = 0; i < k; i++) {
            arrayOfDifferentLengths[i] = new LinkedList();
        }
        n = m = 0;//init. n and m with 0
        //scanner to read from the input file
        Scanner scan = new Scanner(input);
        int line_num = 1;//current line number
        while (scan.hasNext())//while not reach end of the file
        {
            String line = scan.nextLine();//get line 
            String[] str = line.split("\\s+");//split by spaces
            for (int i = 0; i < str.length; i++)//to each word in the line
            {
                //replace all puncitation from the begin
                while( (String.valueOf(str[i].charAt(0)).matches("\\W"))) {
                    str[i] = str[i].substring(1);
                }
                //replace all puncitation from the end
                while( (String.valueOf(str[i].charAt(str[i].length()-1)).matches("\\W"))) {
                    str[i] = str[i].substring(0, str[i].length()-1);
                }
                //insert it in the array
                insert(str[i].toLowerCase(), line_num, i + 1);
            }
            line_num++;//increase the line number
        }
        //init. the sorted array with size (m)
        sortedArray = new WordInformation[m];
        //insert every word in the sortedArray
        int j = 0;
        for (int i = 0; i < k; i++) {
            //get list of word for length i and insert words in the sortedArray
            Node<WordInformation> temp = arrayOfDifferentLengths[i].head;
            while (temp != null) {
                sortedArray[j++] = temp.data;
                temp = temp.next;
            }
        }
        //Sort the sortedArray using merge sort algorithm
        mergeSort(sortedArray, m);
    }

    //merge sort algorithm to sort the element from the most frequent to the least
    private void mergeSort(WordInformation[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        WordInformation[] l = new WordInformation[mid];
        WordInformation[] r = new WordInformation[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    private void merge(WordInformation[] a, WordInformation[] l, WordInformation[] r, int left, int right)
    {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].size >= r[j].size) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    //insert method to insert word from line and pos in the array
    private void insert(String word, int line, int pos) {
        WordInformation e = new WordInformation(word, line, pos);//new object of WordInformation
        int length = word.length() - 1;//get length of the word in the array
        if (arrayOfDifferentLengths[length].Search(word) == null) //search for the word if not found increase number of unique words
        {
            m++;
        }
        //insert the element e in the array and increase number of total words
        arrayOfDifferentLengths[length].insert(e);
        n++;
    }

    //first operation return total number of words
    public int op1() {
        return n;
    }

    //operation 2 return number of unique words
    public int op2() {
        return m;
    }
    
    

    //operation 3 get total number of occurrences of a particular word
    public int op3(String word) {
        int length = word.length() - 1;//get the index of the word in the array
        Node<WordInformation> node = arrayOfDifferentLengths[length].Search(word);//search for this word in the specific index
        if (node == null) //if the word not found then return 0;
        {
            return 0;
        }
        return node.data.size;//aother eay return size of occurrences list of the word
    }
    
    
    

    //operation 4 total number of words with a particular lengths
    public int op4(int length) {
        //return the size of the list in the specific index
        return arrayOfDifferentLengths[length - 1].size;
    }

    //operation 5 unique words in the file sorted by the total occurrences of each word
    public void op5() {
        //print the words from the sorted array
        for (int i = 0; i < sortedArray.length; i++) {
            System.out.println("(" + sortedArray[i].word + "," + sortedArray[i].size + "), ");
        }
    }

    //operation 6 locations of the occurrences of a word starting from the top of the text file
    public void op6(String word) {
        int length = word.length() - 1;//get the index
        Node<WordInformation> node = arrayOfDifferentLengths[length].Search(word);//search for the word in the specific index
        if (node != null) //if found then print the occurrences list
        {
            System.out.println(node.data);
        }
    }

    //operation 7 examine if two words are occurring adjacent to each other in a text file
    public boolean op7(String w1, String w2) {
        int length1 = w1.length(), length2 = w2.length();//get the length of the words
        //get the words from specific indexs
        Node<WordInformation> node1 = arrayOfDifferentLengths[length1 - 1].Search(w1);
        Node<WordInformation> node2 = arrayOfDifferentLengths[length2 - 1].Search(w2);
        if (node1 != null && node2 != null) //if found the words
        {
            //get the list of occurrences of the first word
            Node<WordOccurrence> occ1 = node1.data.occList.head;
            while (occ1 != null) {
                //get the line and the position
                int line = occ1.data.lineNo;
                int pos = occ1.data.position;
                //get the list of occurrences of the second word
                Node<WordOccurrence> occ2 = node2.data.occList.head;
                while (occ2 != null) {
                    //check if line is same then check the position if they are occurring adjacent to each other then return true
                    if (occ2.data.lineNo == line) {
                        if (occ2.data.position == pos - 1 || occ2.data.position == pos + 1) {
                            return true;
                        }
                    }
                    //if we break the line number then break this loop
                    if (occ2.data.lineNo > line) {
                        break;
                    }
                    occ2 = occ2.next;
                }
                occ1 = occ1.next;
            }
        }
        return false;//not occurring adjacent to each other
    }


}
