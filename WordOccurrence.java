
public class WordOccurrence {

    int lineNo;
    int position;

    WordOccurrence(int row, int pos) {
        this.lineNo=row;
        this.position = pos;
    }
    
    @Override
    public String toString()
    {
        return "("+lineNo+", "+position+")";
    }
    
}
