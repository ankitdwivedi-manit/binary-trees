import java.util.*;

class Node{
    int data;
    Node left;
    Node right;
    Node(int data){
        this.data=data;
        this.left=null;
        this.right=null;
    }
}

class Pair{
    Node node;
    int level;
    Pair(Node node,int level){
        this.node=node;
        this.level=level;
    }
}

class Tree{
    static Scanner sc=new Scanner(System.in);
    
    static Node buildTree(){
        int x=sc.nextInt();
        if(x==-1){
            return null;
        }
        Node root=new Node(x);
        root.left=buildTree();
        root.right=buildTree();
        return root;
    }
    static void printTree(Node root){
        if(root==null)
            return;
        System.out.print(root.data+" ");
        printTree(root.left);
        printTree(root.right);
    }


    /*----------------------------------------------------RECURSIVE TRAVERSALS-------------------------------------------------------------------------- */
    //recursive traversals(inorder,preorder and postorder)
    static void preorder(Node root){
        if(root==null)
            return;
        System.out.print(root.data+" ");
        preorder(root.left);
        preorder(root.right);
    }
    static void inorder(Node root){
        if(root==null)
        return;
        inorder(root.left);
        System.out.print(root.data+" ");
        inorder(root.right);
    }
    static void postorder(Node root){
        if(root==null)
        return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.data+" ");
    }
    // height of tree
    static int height(Node root){
        if(root==null)
            return 0;
        int hl=height(root.left);
        int hr=height(root.right);
        return 1+Math.max(hl,hr);
    }
    //print kth level elements recursively.
    static void printKthLevel(Node root,int level){
        if(root==null){
            return;
        }
        if(level==1){
            System.out.print(root.data+" ");
        }
        printKthLevel(root.left, level-1);
        printKthLevel(root.right, level-1);
    }



    /*--------------------------------------------ITERATIVE LEVEL ORDER (BFS)------------------------------------------------------------------------- */
    //levelorder iterative approach(BFS). complexity O(n). far better than recursive approach.
    static void levelorderIterative(Node root){
        if(root==null)
            return;
        Queue<Node> q=new ArrayDeque<>();
        q.add(root);
        while(!q.isEmpty()){
            Node t=q.poll();
            System.out.print(t.data+" ");
            if(t.left!=null)
                q.add(t.left);
            if(t.right!=null)
                q.add(t.right);
        }
    }


    /*------------------------------------------LEVEL ORDER -2  -------------------------------------------------------------*/
    //modified iterative levelorder in which each level is seperated.
    //with the help of pair in which we are also keeping track of levels.
    static void modifiedlevelorder(Node root){
        if(root==null)
            return;
        Queue<Pair> q=new ArrayDeque<>();
        q.offer(new Pair(root,0));
        int temp=0;
        while(!q.isEmpty()){
            Pair p=q.poll();
            Node n=p.node;
            int l=p.level;
            if(l>temp){
                temp=Math.max(l,temp);
                System.out.println();
            }
            System.out.print(n.data+" ");
            if(n.left!=null)
                q.offer(new Pair(n.left,l+1));
            if(n.right!=null)
                q.offer(new Pair(n.right,l+1));
        }
    } 
    //modifing the above approach
    //inserting null after each level.
    //note java arraydequeue does not allow null insertion. for null insertion we should implement queue with linked list
    static void modifiedlevelorder1(Node root){
        if(root==null){
            return;
        }
        Queue<Node> q=new LinkedList<>();
        q.offer(root);
        q.offer(null);
        while(!q.isEmpty()){
            Node t=q.poll();
            if(t==null){
                System.out.println();
                if(!q.isEmpty())
                    q.offer(null);
                continue;
            }
            System.out.print(t.data+" ");
            if(t.left!=null)
                q.offer(t.left);
            if(t.right!=null)
                q.offer(t.right);
        }
    }




    static int countNodes(Node root){
        if(root==null){
            return 0;
        }
        int ln=countNodes(root.left);
        int rn=countNodes(root.right);
        return 1+ln+rn;
    }
    static int sumOfNodes(Node root){
        if(root==null){
            return 0;
        }
        int lns=sumOfNodes(root.left);
        int rns=sumOfNodes(root.right);
        return root.data+lns+rns;
    }




    /*---------------------------------------------DIAMETER OF BINARY TREE--------------------------------------------------------------------*/
    //diametre of tree:-
    //worst case time complexity of this approach is O(n^2).
    //since at each node we are calculating height and height itself take O(n) to compute.
    //improved time complexity in next function fastDiameter().
    static int diameter(Node root){
        if(root==null){
            return 0;
        }
        int headDiameter=height(root.left)+height(root.right);
        int ld=diameter(root.left);
        int rd=diameter(root.right);
        return Math.max(headDiameter,Math.max(ld,rd));
    }
    //in fastDiameter used Pairs class to return two values(height and diameter).
    //time complexity of this bottom up approach is O(n). as we are calculating height only once and using it ireespective of calculating it again and again.
    static class Pairs{
        int height;
        int diameter;
        Pairs(int height,int diameter){
            this.height=height;
            this.diameter=diameter;
        }
    }
    static Pairs fastDiameter(Node root){
        Pairs p=null;
        if(root==null){
            p=new Pairs(0,0);
            return p;
        }
        Pairs left=fastDiameter(root.left);
        Pairs right=fastDiameter(root.right);
        int p_height=Math.max(left.height,right.height)+1;
        int p_diameter=Math.max(left.height+right.height,Math.max(left.diameter,right.diameter));
        p=new Pairs(p_height,p_diameter);
        return p;
    }


    public static void main(String[] args){
        Node head=buildTree();
        // printTree(head);
        // System.out.println();
        // preorder(head);
        // System.out.println();
        // inorder(head);
        // System.out.println();
        // postorder(head);
        // System.out.print("\n"+height(head)+"\n");

        //printing the tree levelorder recursively. with the help of height and KthLevelorder.
        //worst case complexity of this approach is O(n^2) in case of skew tree(having height n).
        // int h=height(head);
        // for(int level=1;level<=h;level++){
        //     printKthLevel(head,level);
        // }

        // levelorderIterative(head);
        // modifiedlevelorder1(head);
        // System.out.println(countNodes(head)+"  --> "+sumOfNodes(head));


        // System.out.println("diameter = "+diameter(head));
        //Pairs p=fastDiameter(head);
        //System.out.println("diameter = "+p.diameter);
        sc.close();
    }
}

// 1 2 4 -1 -1 5 -1 -1 3 6 -1 -1 7 -1 -1