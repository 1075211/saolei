import java.util.*;
public class list{
    public static void main(String[]args){
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("输入两个整型数组的大小");
            int a=sc.nextInt();
            int b=sc.nextInt();
            System.out.println("分别输入数组中每个元素的值");
            int []list1=new int[a];
            int list2[]=new int [b];
            System.out.println("Enter list1:");
            for(int i=0;i<a;i++){
                list1[i]=sc.nextInt();
            }
            System.out.println("Enter list2:");
            for(int j=0;j<b;j++){
                list2[j]=sc.nextInt();
            }
            boolean decide = equal(list1, list2);
            String result;
            if (decide == true) 
                result = "Two lists are identical"; 
            else 
                result = "Two lists are not identical";
            System.out.println(result);
        }
    }
    public static boolean equal(int [] list1, int [] list2) {
        boolean decide = false;
        if (list1.length != list2.length) {
            decide = false;
        } 
        else {
            Arrays.sort(list1);
            Arrays.sort(list2);
            if (Arrays.equals(list1,list2)){
                decide=true;
            }
        }
        return decide;
   }
    public static int calculate(int number, int num) {
        return 0;
    }
}