import java.util.*;

class sjfschedulingpractice{
    static Scanner Sc= new Scanner (System.in);
    public static void main(String [] args){

        System.out.println("-------SJF pre-emitive--------");

        System.out.print("enter number of process:");
        int n = Sc. nextInt();

        int process[]=new int[n];
        int AT[]=new int [n];
        int BT[]=new int [n];
        int CT[]=new int [n];
        int TAT[]=new int [n];
        int WT[]=new int [n];
        int RT[]=new int [n];
        int remBT[]=new int [n];
        int visit[]=new int [n];

        for(int i=0;i<n;i++){
            process[i]=(i+1);
            System.out.print("Enter Arrival time for Process p"+(i+1)+": ");
            AT[i]= Sc.nextInt();
      
            System.out.print("enter BT for process P"+(i+1)+":");
            BT[i]=Sc.nextInt();
           remBT[i]=BT[i];
           visit[i]=0;
        }

        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++){

                if(AT[i] < AT[j]){
                    int temp=AT[i];
                    AT[i]=AT[j];
                    AT[j]=temp;

                    temp=process[i];
                    process[i]=process[j];
                    process[j]=temp;

                    temp=BT[i];
                    BT[i]=BT[j];
                    BT[j]=temp;

                    temp=remBT[i];
                    remBT[i]=remBT[j];
                    remBT[j]=temp;
                }
            }
        }

        
    }
}