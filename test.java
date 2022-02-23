import java.util.Arrays;
import java.util.Arrays; 
import java.lang.Object;
import java.io.FileNotFoundException;

import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class test{
static int n=4;
// generate random matrix-----------------------------------------------------------------
  // public static double [][] randomMatrixFunction(int n) { 
  //       int i, j;
  //       double [][] matrix= new double [n][n];
  //       for (i=0; i<matrix.length;i++){
  //         for ( j = 0; j < matrix.length; j++) {
  //           matrix[i][j]=(double) (Math.round (Math.random()*1000))/1000;
  //          }  
  //       }
  //     return matrix;
  // }
//Method 5: Ax in Diagonal format 555555555555555555555555555555555555555555555555555555555555555555555555555555555555555
//1D 
public static double multiply_diagAx1D(int n, double[] valA, int[] diag, double[] x, int U, int L ) { 
 double [] y5= new double[n];
   int j,i;
    double startTime5 = System.currentTimeMillis();
     // A's main and super_diagonals elements multiply with vector x
    for (int d=0; d<diag.length; d++){
        int k=diag[d];
        if (k>=0){         
            j=0;
            i=k; 
            // int inx_C = LC*(2*n-LC-1)/2 + k*(2*n-k-1)/2 + k ;  
            // int endinx_C = inx_C + n-k-1;      
            int startindex=L*(2*n-L-1)/2 + k*(2*n-k-1)/2 + k;
            int stopindex=startindex+n-k-1;
             for(; startindex<=stopindex; startindex++){
                y5[j]+=valA[startindex]*x[i];
                 j++;
                 i++;
             }
        }
        else{
            int abs_k=Math.abs(k);
            j=abs_k;
            i=0;
            // int inx_C = LC*(2*n-LC-1)/2 - k*(2*n-k-1)/2;;  
            // int endinx_C = inx_C + n-k-1;
            int startindex=L*(2*n-L-1)/2 - abs_k*(2*n-abs_k-1)/2;
            int stopindex=startindex + n-abs_k-1;
             for(; startindex<=stopindex; startindex++){
                y5[j]+=valA[startindex]*x[i];
                 j++;
                 i++;
             }
       }
   }
  System.out.println(Arrays.toString(diag));
   System.out.println("y5="+Arrays.toString(y5));
    double endTime5   = System.currentTimeMillis();
    double totalTime5= (endTime5 - startTime5);
    return totalTime5;  

}

public static double multiply_diagATx1D(int n, double[] valA, int[] diag, double[] x, int U, int L ) { 
 double [] y5= new double[n];
   int j,i;
    double startTime5 = System.currentTimeMillis();
     // A's main and super_diagonals elements multiply with vector x
    for (int d=0; d<diag.length; d++){
        int k=diag[d];
        if (k>=0){         
            // j=0;
            // i=k; 
            j=k;
            i=0;
            // int inx_C = LC*(2*n-LC-1)/2 + k*(2*n-k-1)/2 + k ;  
            // int endinx_C = inx_C + n-k-1;      
            int startindex=L*(2*n-L-1)/2 + k*(2*n-k-1)/2 + k;
            int stopindex=startindex+n-k-1;
             for(; startindex<=stopindex; startindex++){
                y5[j]+=valA[startindex]*x[i];
                 j++;
                 i++;
             }
        }
        else{
            int abs_k=Math.abs(k);
            // j=abs_k;
            // i=0;
            i=abs_k;
            j=0;
            // int inx_C = LC*(2*n-LC-1)/2 - k*(2*n-k-1)/2;;  
            // int endinx_C = inx_C + n-k-1;
            int startindex=L*(2*n-L-1)/2 - abs_k*(2*n-abs_k-1)/2;
            int stopindex=startindex + n-abs_k-1;
             for(; startindex<=stopindex; startindex++){
                y5[j]+=valA[startindex]*x[i];
                 j++;
                 i++;
             }
       }
   }
// System.out.println(Arrays.toString(diag));
   System.out.println("y5T="+Arrays.toString(y5));
    double endTime5   = System.currentTimeMillis();
    double totalTime5= (endTime5 - startTime5);
    return totalTime5;  

}


   public static void main(String[] args){ 
   double [] valA= {1,2,3, 1,2,3,4, 2,3,4 };
   double [] x= {1,2,3,4};
   int [] diag={-1, 0, 1};

   int U=1;
   int L=1;

    double time=multiply_diagAx1D(n,valA, diag, x , U, L);
     double timeT=multiply_diagATx1D(n,valA, diag, x , U, L);
   // System.out.println(Arrays.deepToString(A));
   }
 }