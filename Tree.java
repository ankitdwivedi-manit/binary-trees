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

    /*--------------------------REPLACE THE CURRENT NODE WITH THE SUM OF ITS CHILD FOR EVERY NODE EXEPT LEAF------------------------------------------ */
    static int replaceSum(Node root){
        if(root.left==null && root.right==null){
            return root.data;
        }
        int left=replaceSum(root.left);
        int right=replaceSum(root.right);
        int toReturn=left+right+root.data;
        root.data=left+right;
        return toReturn;
    }


    /*--------------------------CHECK IF HEIGHT BALANCED NINARY TREE------------------------------------- */
    //time complexity of given bottom up approach is O(n).
    //if we do the problem by top down, then time complexity will be O(n^2). same as calcuated in diameter.
    static class HeightBalancedNode{
        int height;
        boolean balance;
        HeightBalancedNode(int height,boolean balance){
            this.height=height;
            this.balance=balance;
        }
    }
    static HeightBalancedNode isHeightBalanced(Node root){
        if(root==null){
            return new HeightBalancedNode(0, true);
        }
        HeightBalancedNode left=isHeightBalanced(root.left);
        HeightBalancedNode right=isHeightBalanced(root.right);
        int bal=Math.abs(left.height-right.height);
        if(bal<=1 && left.balance && right.balance) {
            return new HeightBalancedNode(Math.max(left.height,right.height)+1, true);
        }
        return new HeightBalancedNode(Math.max(left.height,right.height)+1, false);
    }


    /*--------------------------------BUILD HEIGHT BALANCED BINARY TREE FROM GIVEN ARRAY------------------------------------------------------- */
    static Node createHeightBalancedTree(int[] a,int l,int r){
        if(l>r){
            return null;
        }
        if(l==r){
            return new Node(a[l]);
        }
        int m=l+(r-l)/2;
        Node temp=new Node(a[m]);
        temp.left=createHeightBalancedTree(a, l, m-1);
        temp.right=createHeightBalancedTree(a, m+1, r);
        return temp;
    }


    // worst case time complexty of make tree from (inorder & preorder) and (inorder & postorder) is O(n^2) (...ie for skewed trees). approaches given is of O(n*n).
    //we can improve the time complexity by using hashmap to store the indices using hashmaps the time complexity would be O(n).
    /*--------------------------------MAKE TREE FROM INORDER AND PREORDER------------------------------------------------------------- */
    static int i=0;
    static Node makeTreeFromInAndPreorder(int[] in,int[] pre,int s,int e){
        if(s>e){
            return null;
        }
        Node root=new Node(pre[i]);
        int index=-1;
        for(int j=s;j<=e;j++){
            if(in[j]==pre[i]){
                index=j;
                break;
            }
        }
        i++;
        root.left=makeTreeFromInAndPreorder(in, pre, s, index-1);
        root.right=makeTreeFromInAndPreorder(in, pre, index+1, e);
        return root;
    }
    /*--------------------------MAKE TREE FROM INORDER AND POSTORDER--------------------------------------------------------- */
    static int j=0;
    static Node makeTreeFromInorderAndPostorder(int[] in,int[] post,int s,int e){
        if(s>e){
            return null;
        }
        Node root=new Node(post[j]);
        int index=-1;
        for(int i=s;i<=e;i++){
            if(in[i]==post[j]){
                index=i;
                break;
            }
        }
        j--;
        root.right=makeTreeFromInorderAndPostorder(in, post, index+1, e);
        root.left=makeTreeFromInorderAndPostorder(in, post, s, index-1);
        return root;
    }




    public static void main(String[] args){
        // Node head=buildTree();
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
        
        // replaceSum(head);
        // printTree(head);
        
        // HeightBalancedNode hb=isHeightBalanced(head);
        // System.out.println(hb.balance);

        // int[] arr={1, 2, 3, 4, 5, 6, 7, 9, 0};
        // Node heightBalancedNode=createHeightBalancedTree(arr, 0, 8);
        // printTree(heightBalancedNode);

        
        // int[] preorder={1, 2, 3, 4, 8, 5, 6, 7};
        // int[] inorder={3, 2, 8, 4, 1, 6, 7, 5};
        // Node head=makeTreeFromInAndPreorder(inorder, preorder, 0, inorder.length-1);
        // printTree(head);

        // int[] inorder={3, 2, 8, 4, 1, 6, 7, 5};
        // int[] postorder={3, 8, 4, 2, 7, 6, 5, 1};
        // j=inorder.length-1;
        // Node head=makeTreeFromInorderAndPostorder(inorder, postorder, 0, inorder.length-1);
        // printTree(head);

        sc.close();
    }
}
// 1 2 4 -1 -1 5 -1 -1 3 6 -1 -1 7 -1 -1