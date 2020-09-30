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
        modifiedlevelorder1(head);
        sc.close();
    }
}

// 1 2 4 -1 -1 5 -1 -1 3 6 -1 -1 7 -1 -1