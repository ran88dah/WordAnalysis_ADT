public class LinkedList<T> implements List<T> {

    Node<T> head;
    Node<T> current;
    int size; 
    
    LinkedList()
    {
        head=current=null;
        size =0;
    }
    
    public boolean empty() {
        return head == null;
    }

    public boolean full() {
        return false;
    }

    public void findFirst() {
         current = head;
    }

    public void findNext() {
        current = current.next;
    }

    public boolean last() {
        return current.next == null;
    }

    public T retrieve() {
        if (current == null)
            return null;
        return current.data;
    }

    public void update(T e) {
        //update element increase the size of occurrences list and insert new location in the list 
        ((WordInformation)current.data).size++;
        ((WordInformation)current.data).occList.insert(((WordInformation)e).occList.retrieve());
    }

    public void insert(T e) 
    {
        //if list is empty then add the first node and increase size
        if(empty())
        {
            current = head = new Node<>(e);
            this.size++;
        }
        else
        {
            //if we add WordInformation element
            if(e instanceof WordInformation)
            {
                //word already exist in the list then updated the current element
                if(Search(((WordInformation)e).word)!=null)
                {
                    update(e);
                }
                else//new word added to the list
                {
                    current.next = new Node(e);
                    current = current.next;
                    this.size++;//increase size
                }
            }
            //if we add WordOccurrence element then just add the new occurrences at the end of the list
            else {
                current.next = new Node(e);
                current = current.next;
                this.size++;
            }

        }
    }
    
    public Node<T> Search(String word)
    {
        //temporary pointer
        Node<T>temp = head;
        //while not reach last node in the list
        while(temp!=null)
        {
            current = temp;//make current refer to this node
            if(((WordInformation)temp.data).word.equals(word))//if the word equals then return templ
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;//not found return null
    }
   

}
