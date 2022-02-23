//***********************************************************************************************
// Generate random matrix size of n x n , and random vector size of n.
// Store the matrix in 1D array by row-wise order for standard matrix vector multilication (for comparision, matvec multiply in 2D is included)
// Store the matrix in 1D array by diagonals order for matrix-vector multiplication by diagonals, ie, main-superdiagonal-subdiagonal.(for comparision, matvec multiply in 2D is included)
// Method1: Standard matrix-vector multilication
// Method2: Standard matrix_trasnpose- vector multiplication
// Method3: Matrix-vector multiply by diagonals
// Methid4: Matrix trasnpose- vector multiply by diagonals
//***********************************************************************************************
import java.util.Arrays;
import java.util.Arrays; 
import java.lang.Object;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class matvec1D2D_diagModified{
   static int  n;
 // generate random matrix ----------------------------------------------------------
     public static double [][] randomMatrixFunction( int n) { 
        int i, j;
        double [][] matrix= new double [n][n];
         for (i=0; i<matrix.length;i++){
          for ( j = 0; j < matrix[0].length; j++) {
            matrix[i][j]=(double) (Math.round (Math.random()*1000))/1000;
           }  
         }
        return matrix;
    }

  // generate random vector-------------------------------------------------------------- 
     public static double [] randomVectorFunction(int n) { 
        double [] vector= new double [n];
         for (int i=0; i<n;i++){  
            vector[i]=(double) (Math.round (Math.random()*1000))/1000;           
         }
        return vector;
    }

// Store the matrix in 1D array by row-wose order---------------------------------------
    public static double [] getArray(double[][] A, int n) { 
	   double[] array= new double[n*n];
	   int index=0;
	   for(int i=0; i<n; i++){
	    	for(int j=0; j<n; j++){
           array[index]=A[i][j];
           index++;
	     	}
	    }
       return array;  
    } 

// Method1: Standard matrix-vector multilication 111111111111111111111111111111111111111111111111111111111111111
 //1D ---- Matrix A is stored in 1D arrray   
   public static double multiply_Ax1D(int n, double[] arrayA , double[] x) {
     double[] y1= new double[n];
      double startTime1 = System.currentTimeMillis();
         for (int i=0; i<n ; i++){
            for (int j=0; j<n; j++){
                y1[i]+=arrayA[i*n+j]*x[j];
            }
         }
     // System.out.println(Arrays.toString(y1)) ;                   
    double endTime1   = System.currentTimeMillis();
    double totalTime1 = (endTime1 - startTime1);
    return totalTime1; 
  }

 //2D ----Matrix A is in 2D format
  public static double multiply_Ax2D(int n, double[][] A, double[] x) {
     double[] y2= new double[n];
     double startTime2 = System.currentTimeMillis();
         for (int i=0; i<n ; i++){
            for (int j=0; j<n; j++){
                y2[i]+=A[i][j]*x[j];
            }
         }
     // System.out.println(Arrays.toString(y2)) ;                   
    double endTime2   = System.currentTimeMillis();
    double totalTime2 = (endTime2 - startTime2);
    return totalTime2; 
  } 
 
// Method2: Standard matrix_trasnpose- vector multiplipcation 22222222222222222222222222222222222222222222222222
 //1D  
 public static double multiply_ATx1D(int n, double[] arrayA, double[] x) {
     double[] y3= new double[n]; 
      double startTime3 = System.currentTimeMillis();           
        for (int i=0; i<n ; i++){
            for (int j=0; j<n; j++){
                y3[i]+=arrayA[j*n+i]*x[j];
            }
         } 
         // System.out.println("ATx_1D="+Arrays.toString(y3)) ;        
    double endTime3   = System.currentTimeMillis();
    double totalTime3 = (endTime3 - startTime3);
    return totalTime3; 
  }
//2D
  public static double multiply_ATx2D(int n, double[][] A, double[] x) {
     double[] y4= new double[n];      
      double startTime4 = System.currentTimeMillis();           
        for (int i=0; i<n ; i++){
            for (int j=0; j<n; j++){
                y4[i]+=A[j][i]*x[j];
            }
         }
     // System.out.println(Arrays.toString(y4)) ;      
    double endTime4   = System.currentTimeMillis();
    double totalTime4 = (endTime4 - startTime4);
    return totalTime4; 
    } 

// Store elements of matrix in 1D array by diagonal order------------------------      
 public static Object[] diagStoreFunction(double[][] A, int n) { 
      double[] array=new double[n*n];
        int [] diag=new int[2*n-1];
      int index=0; 
      int count=0 ;
    //Print A's main and super_diagonals elements  
      for (int d=0; d<n; d++){
           int j=d; 
           int u=n-d-1;
           int t=0;
          for (int i=0; i<=u;i++){  
              array[index]=A[i][j];
                index++;
                j++;    
            }
            diag[d]=d;
            count+=1;  
        }
       
   //Print A's sub_diagonals elements
      for (int d=-1; d>=-n+1; d--){
           int i=-d; 
           int u=n-1-i;  
          for (int j=0; j<=u;j++){
              array[index]=A[i][j];
              index++; 
              i++;  
          }
          diag[count-d-1]=d; 
        }
        // System.out.println(Arrays.toString(diag));
        Object[] arraycolindex=new Object[]{array, diag};
        return arraycolindex;
      }
 // Method3: Matrix-vector multiply by diagonals 3333333333333333333333333333333333333333333333333333333333333333
//1D 
public static double multiply_diagAx1D(int n, double[] valA, int[] diag, double[] x ) { 
 double [] y5= new double[n];
   int j,i;
    double startTime5 = System.currentTimeMillis();
     // A's main and super_diagonals elements multiply with vector x
    for (int d=0; d<diag.length; d++){
        int k=diag[d];
        if (k>=0){         
            j=0;
            i=k;        
            int startindex=k*n-k*(k-1)/2;
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
            int startindex=n*(n+1)/2 + (abs_k-1)*n - abs_k*(abs_k-1)/2 ;
            int stopindex=startindex + n-abs_k-1;
             for(; startindex<=stopindex; startindex++){
                y5[j]+=valA[startindex]*x[i];
                 j++;
                 i++;
             }
       }
   }
// System.out.println(Arrays.toString(diag));
   // System.out.println("y5="+Arrays.toString(y5));
    double endTime5   = System.currentTimeMillis();
    double totalTime5= (endTime5 - startTime5);
    return totalTime5;  

}

   
//2D
 public static double multiply_diagAx2D(int n, double[][] A, double[] x ) { 
       double[] y6= new double[n];     
       double startTime6 = System.currentTimeMillis();       
         // A's sub_diagonals elements multiply with vector x
      for (int d=-1; d>-n; d--){  
          for (int i=0; i<n+d;i++){
              y6[i-d]+=A[i-d][i]* x[i];
             } 
        }  
         // A's main and super_diagonals elements multiply with vector x
       for (int d=0; d<n; d++){ 
          for (int i=0; i<n-d;i++){  
              y6[i]+=A[i][i+d]*x[i+d];            
            }  
        }
        // System.out.println(Arrays.toString(y6)) ;       
        double endTime6  = System.currentTimeMillis();
      double totalTime6 = (endTime6 - startTime6);
     return totalTime6; 
  }

// Methid4: Matrix trasnpose- vector multiply by diagonals  4444444444444444444444444444444444444444444444444444
//1D  

public static double multiply_diagATx1D(int n, double[] valA, int[] diag, double[] x ) { 
 double [] y7= new double[n];
   int j,i;
    double startTime7 = System.currentTimeMillis();
     // A's main and super_diagonals elements multiply with vector x
    for (int d=0; d<diag.length; d++){
        int k=diag[d];
        if (k>=0){         
            j=k;
            i=0;        
            int startindex=k*n-k*(k-1)/2;
            int stopindex=startindex+n-k-1;

             for(; startindex<=stopindex; startindex++){
                y7[j]+=valA[startindex]*x[i];
                 j++;
                 i++;
             }
        }
        else{
            int abs_k=Math.abs(k);
            j=0;
            i=abs_k;
            int startindex=n*(n+1)/2 + (abs_k-1)*n - abs_k*(abs_k-1)/2 ;
            int stopindex=startindex + n-abs_k-1;
             for(; startindex<=stopindex; startindex++){
                y7[j]+=valA[startindex]*x[i];
                 j++;
                 i++;
             }
       }
   }
// System.out.println(Arrays.toString(diag));
// System.out.println("y7="+Arrays.toString(y7));
    double endTime7   = System.currentTimeMillis();
    double totalTime7= (endTime7 - startTime7);
    return totalTime7;  

}

//2D
public static double multiply_diagATx2D(int n, double[][] A, double[] x ) { 
    double[] y8= new double[n];      
    double startTime8 = System.currentTimeMillis();     
     //A_transpose's sub_diagonals elements multiply with vector x
      for (int d=-1; d>=-n+1; d--){
           int u=n-1+d;  
          for (int i=0; i<=u;i++){
              y8[i-d]+=A[i][i-d]* x[i];
             } 
        } 
         for (int d=0; d<n; d++){ 
           int u=n-d-1;
          for (int i=0; i<=u;i++){  
              y8[i]+=A[i+d][i]*x[i+d];            
            }  
        }
         // System.out.println(Arrays.toString(y8)) ; 
      double endTime8  = System.currentTimeMillis();
      double totalTime8 = (endTime8 - startTime8);
     return totalTime8; 
}


//Main function -------------------------------------------------------------------------------------
 // public static void main(String[] args){ 
 public static void main(String[] args) throws Throwable {
         PrintStream out = new PrintStream(new FileOutputStream("denseMatvec4.xlsx"));
         System.setOut(out);
         System.out.println("N"  + ","+ "Ax_1D"+ ","+ "Ax_diag1D" + "," +"ATx_1D" +"," + "ATx_diag1D" + "," +
                            "N"  + ","+ "Ax_2D"+ ","+ "Ax_diag2D" + "," +"ATx_2D" +"," + "ATx_diag2D");
   for ( int n = 8000 ; n <= 13000; n+=200 ) {


//matvec - standard
     double[][] A = randomMatrixFunction (n);
     double [] arrayA=getArray (A, n);
  	 double [] x=randomVectorFunction(n);
     double t1_Ax=multiply_Ax1D(n, arrayA, x); 
     double t2_Ax=multiply_Ax2D(n, A, x); 
     double t1_ATx=multiply_ATx1D(n, arrayA, x); 
     double t2_ATx=multiply_ATx2D(n, A, x); 

//matvec --diag    
     Object[] diagArraydiag=diagStoreFunction(A,n);
     double[] diagarrayA= (double[])diagArraydiag[0];
     int []diag=(int [])diagArraydiag[1];
     // int []colindex=(int [])diagArrayColindex[1];
     // int[] rowindex=(int[]) diagArrayColindex[2];
     double t1_diagAx=multiply_diagAx1D(n, diagarrayA, diag, x);
      double t2_diagAx=multiply_diagAx2D(n, A,x);
     double t1_diagATx=multiply_diagATx1D(n, diagarrayA, diag, x);
     double t2_diagATx=multiply_diagATx2D(n, A,x);

  System.out.println(n + ","+ t1_Ax + "," + t1_diagAx + ","+ t1_ATx+ ","+ t1_diagATx+ "," + 
                   n + ","+ t2_Ax + "," + t2_diagAx + ","+ t2_ATx+ ","+ t2_diagATx);
  //  System.out.print("\n");

  }
 }
}