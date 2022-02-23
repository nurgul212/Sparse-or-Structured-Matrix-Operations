// ************************************************************************************************************
// 1. Generate random band matrix A with bandwidth BW in 1D array format, actually the generated array will be in Diag format
// 2. Store the CRS and JSA format
// Generate randon full vector x , size of n
// Method 1: Ax in CRS    
// Method 2: A'x in CRS  
// Method 3: Ax in JSA   
// Method 4: A'x in JSA  
// Method 5: Ax in Diagonal format  
// Method 6: A'x in Diagonal format 
//**************************************************************************************************************

import java.util.List;
import java.util.Arrays; 
import java.lang.Object;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;;
import java.text.DecimalFormat;
import java.util.*;
import java.util.ArrayList;
import java.lang.*;
import java.text.DecimalFormat;


public class RB_MV_diagModified{
  // generate random full vector size of n ****************************************************************************
    public static double [] RandomFullVector(int n) { 
          double [] vector= new double [n];
         for (int i=0; i<n;i++){  
            vector[i]=(double) (Math.round (Math.random()*1000))/1000;           
         }
         // System.out.println("x size=" + vector.length);
        return vector;
     }
//Method: generate the rabdom sparse band matrix with BW, and return nnz elements in 1D array--CRS formst  
public static Object[] RandomArray(int n, int[] diag){
   int ii=0;
    int nnz=0;
    for(int i=0; i<diag.length; i++){
      nnz+=n-Math.abs(diag[i]);
    } 
    double[] diagArray= new double [nnz];
    int [] colind=new int[nnz];
    int[] rowind=new int[nnz];
   
   //value, column, row index array
   for (int t=0; t<diag.length; t++){
      for(int k=0; k<n-Math.abs(diag[t]); k++){
       diagArray[ii]=(double)(Math.round(Math.random()*1000))/1000;
         if (diag[t]<0){        
            rowind[ii]=k+Math.abs(diag[t]);
            colind[ii]=k;
         }
         else{
           rowind[ii]=k;
           colind[ii]=k+Math.abs(diag[t]);
         }
         ii++;
      }
   }  
    //diag pointer
    int[] dpointer=new int[diag.length+1];
      dpointer[0]=0;
      for (int i=0; i<diag.length; i++){
         dpointer[i+1]=dpointer[i]+ n- Math.abs(diag[i]);
       
      }
        // System.out.println(Arrays.toString(dpointer));
        // System.out.println(Arrays.toString(diagArray));
        //  System.out.println(Arrays.toString(colind));
        //   System.out.println(Arrays.toString(rowind));
      Object[] objects=new Object[]{diagArray,dpointer,colind,rowind };
    return objects;
 } 

//Convert Diag array to CRS storage format, return value, rowpointer and colum index array
 public static Object[] ConvertDiagToCRS_JSA(int n, double[]diagArray, int[]colind, int[] diag ){
   int size=diagArray.length;
   double[] CRSarray=new double[size];
     int [] colind_CRS=new int[size];
      int[] rowptr=new int[n+1];
     
    double[][] JSAarray = new double[n][1];
    double[] tempJSA=new double[n];
    int[][] colind_JSA = new int[n][1];
    int[] tempindex=new int[n];

      int ii, jj=0;
      int kk=0;
      int x=0;
      int index=0;
      int[] newindex=new int[size];
      int count=0;
      rowptr[0]=0;

for (int r=0; r<n; r++){
  int cnt=0;
   for (int i=0; i<diag.length; i++){
      int p=diag[i]; 

      if(-r<=p && p<n-r){         
          if(p<0){
            x=p;
         }
         if (p>=0){
            x=0;
         }
         int alpha=x;
         int sum=0;
         for(int j=0; j<i;j++){
          sum+=n-Math.abs(diag[j]);
         } 
    ii=sum+r+alpha;
    CRSarray[jj]=diagArray[ii];
    colind_CRS[jj]=colind[ii];

    tempJSA[cnt]=diagArray[ii];
    tempindex[cnt]=colind[ii];
    jj++;
    cnt++;
    }    
  }
   count+=cnt;
   rowptr[kk+1]=count;
   kk++;
 double [] jsa=new double[cnt];   
 int[] jsaindex = new int[cnt];
 System.arraycopy(tempJSA, 0, jsa, 0, cnt);
 System.arraycopy(tempindex, 0, jsaindex, 0, cnt);
  JSAarray[r]=jsa;
  colind_JSA[r]=jsaindex;
}

    Object[] objects=new Object[]{CRSarray,rowptr,colind_CRS, JSAarray, colind_JSA};
  return objects;
 }

// Method 1: Ax in CRS  1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111

 public static double AxinCRS( double[] val, int[] col_ind, int [] row_ptr, double [] x, int n){
     double [] y1= new double[n];

       double startTime1 = System.currentTimeMillis();
        for (int i=0; i< n; i++) {
            for( int j=row_ptr[i]; j<=row_ptr[i+1]-1; j++){
              y1[i]= y1[i]+ val[j]* x[col_ind[j]];
            }
         }
        
         // System.out.println("AxCRS"+Arrays.toString(y1));
      double endTime1   = System.currentTimeMillis();
      double totalTime1= (endTime1 - startTime1);
      return totalTime1;     // System.out.println(Arrays.toString(y));  
    }

// Method 2: A'x in CRS 22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
public static double ATxinCRS( double[] val, int[] col_ind, int [] row_ptr, double [] x, int n){
    double [] y2= new double[n];
    
    double startTime2 = System.currentTimeMillis();   
    for (int i=0; i< n; i++) {
           for( int j=row_ptr[i]; j<=row_ptr[i+1]-1; j++){
             y2[col_ind[j]]+= val[j]* x[i];
            }
     }
     
     // System.out.println("ATxCRS"+Arrays.toString(y2));
    double endTime2   = System.currentTimeMillis();
    double totalTime2= (endTime2 - startTime2);
    return totalTime2;  
 }
// Method 3: Ax in JSA 333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333
// a) Give the input directly 
 public static double AxinJSA( double[][] val, int[][] index, double [] x, int n){
    double [] y3= new double[n];
    double startTime3 = System.currentTimeMillis();
      for (int i=0; i< n; i++) {
           double[] subarray=val[i];
           int []   subindex=index[i];       
          for (int ii=0; ii<subarray.length; ii++){
              y3[i]+=subarray[ii]*x[subindex[ii]];
           }
       }
       
       // System.out.println("AxJSA"+Arrays.toString(y3));
     double endTime3   = System.currentTimeMillis();
     double totalTime3= (endTime3 - startTime3);
     return totalTime3; 
} 
// Method 4: A'x in JSA  444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444   
 public static double ATxinJSA( double[][] val, int[][] index, double [] x, int n){
   double [] y4= new double[n];

    double startTime4 = System.currentTimeMillis();
          for (int j=0; j<n; j++){
              double[] subarray=val[j]; 
              int []   subindex=index[j];     
              for (int i=0; i<subarray.length; i++){
                y4[subindex[i]]+=subarray[i]*x[j];                           
              }
          }
        
       // System.out.println("ATXJSA"+Arrays.toString(y4));
      double endTime4= System.currentTimeMillis();
      double totalTime4= (endTime4 - startTime4);
      return totalTime4; 
 }
//Method 5: Ax in Diagonal format 555555555555555555555555555555555555555555555555555555555555555555555555555555555555555
public static double AxinDiag(double[] val,  int[] diag, double [] x, int n){
  double [] y5= new double[n];
   int cnt=0;
   int i, j;
   double startTime5 = System.currentTimeMillis();
   for (int d=0; d<diag.length; d++){
     int start=cnt;
     int stop= start+ n- Math.abs(diag[d])-1;      
          if (diag[d]>0){
              j=0;
              i=diag[d];
            for(; start<=stop; start++){
              y5[j]+=val[start]* x[i]; 
              j++;
              i++;      
            }
          }
          else {
            j=Math.abs(diag[d]);
             i=0;
             for(; start<=stop; start++){
                 y5[j]+=val[start]* x[i];
                 j++;
                 i++; 
              }
          }
        cnt+= n- Math.abs(diag[d]);      
    }
      // System.out.println("AXdiag"+Arrays.toString(y5));
    double endTime5   = System.currentTimeMillis();
    double totalTime5= (endTime5 - startTime5);
    return totalTime5;        
  }
  // Method 6: A'x in Diagonal format 6666666666666666666666666666666666666666666666666666666666666666666666666666666666666666 
public static double ATxinDiag(double[] val,  int[] diag, double [] x, int n){
  double [] y6= new double[n];
  int cnt=0;
  int i,j;
  double startTime6 = System.currentTimeMillis();
   for (int d=0; d<diag.length; d++){
     int start=cnt;
     int stop= start+ n- Math.abs(diag[d])-1;      
          if (diag[d]<0){
            j=0;
            i=Math.abs(diag[d]);
            for(; start<=stop; start++){
              y6[j]+=val[start]* x[i]; 
              j++;
              i++;      
            }
          }
          else {
            j=diag[d];
             i=0;
             for(; start<=stop; start++){
                 y6[j]+=val[start]* x[i];
                 j++;
                 i++; 
              }
          }
        cnt+= n- Math.abs(diag[d]);      
    }
      // System.out.println("ATXdiag"+Arrays.toString(y6));
    double endTime6   = System.currentTimeMillis();
    double totalTime6= (endTime6 - startTime6);
    return totalTime6;        
  }

  private static DecimalFormat df2= new DecimalFormat(".##");
//Main function **************************************************************************8	
 // public static void main(String[] args){
   
   public static void main(String[] args) throws Throwable {
     PrintStream out = new PrintStream(new FileOutputStream("RBMV2.xlsx"));
     System.setOut(out);

    System.out.println("n" + "," + "#diags"+ "," + "%of nnz" + "," + "Ax_CRS" + ","+ "Ax_JSA" +","+ "Ax_Diag"+ "," +"A'x_CRS" +","+ "A'x_JSA"+ ","+"A'x_Diag"); 
    //50000 is upper bound              
  for ( int n = 120000; n <=143000; n+=1000) {
  
    int t=(2*n-1)/500;
        if(t/2==0){
          t=t/2;
        }
        else{
         t=(t-1)/2;
        }
        // int inc=0;

        int [] diag= new int[t];
         diag[(t-1)/2]=0;
          int jump=100;
        // System.out.println(t);
         for (int i=(t-1)/2 +1; i<t; i++){
           // if(i<= (t-1)/2){
            diag[i]=i+jump;
            jump+=200;
           }
           int v=t-1;
    for (int j=0; j<(t-1)/2; j++){
            diag[j]=-diag[v];
            v--;                    
        }
  
     // System.out.println("diag"+Arrays.toString(diag));
    //find number of nonzero elements
     int totalnnz=0;
    int [] diagnnz=new int [diag.length];
   for (int i=0; i<diag.length; i++){
     diagnnz[i]=n- Math.abs(diag[i]);
     totalnnz+=n- Math.abs(diag[i]);
   }
    
    double a=totalnnz;

    int BW=diag.length;
     // System.out.println("nnz="+a);
     // System.out.println(diag.length);
    double b=(long)n*(long)n;
    double percent=(a/b)*100;
    // System.out.println("nn=" + b);
      // System.out.println("%=" + percent);
    
    
    
     double[] x=RandomFullVector(n);
   
   //Output in diag
   Object[] diagObjects=RandomArray(n,diag);
   double[] val_Diag=(double[]) diagObjects[0];
    // System.out.println(Arrays.toString(diagArray));
      int[] pointer_Diag=(int[]) diagObjects[1];
      int[] colind_Diag=(int[]) diagObjects[2];
      int[] rowind_Diag=(int[]) diagObjects[3];

   // output in CRS  
   Object[] CRSObjects=ConvertDiagToCRS_JSA(n, val_Diag, colind_Diag, diag);
   double[] val_CRS=(double[]) CRSObjects[0];
      int[] rowptr_CRS=(int[]) CRSObjects[1];
      int[] colind_CRS=(int[]) CRSObjects[2];
  //output in JSA 
  double[][] val_JSA=(double[][]) CRSObjects[3];
     int[][] colind_JSA=(int[][]) CRSObjects[4];

   //Time of Ax in CRS, JSA, Diag
      double t1_CRS=AxinCRS(val_CRS, colind_CRS, rowptr_CRS, x, n );
      double t1_JSA= AxinJSA(val_JSA, colind_JSA, x, n );
      double t1_Diag= AxinDiag( val_Diag, diag, x, n);

  
   //time of A'x in CRS, JSA, Diag
     double t2_CRS=ATxinCRS(val_CRS, colind_CRS, rowptr_CRS, x, n );
      double t2_JSA= ATxinJSA(val_JSA, colind_JSA, x, n );
      double t2_Diag= ATxinDiag( val_Diag, diag, x, n);
 

    // System.out.println(Arrays.toString(CRSArray));
 //  System.out.println("from maim");
 // System.out.println(Arrays.toString(colind_CRS));
 // System.out.println(Arrays.toString(rowptr));
  // Output in JSA
 System.out.println(n +"," + BW + "," + df2.format(percent) + "," + t1_CRS + ","+ t1_JSA +","+ t1_Diag+ "," + t2_CRS +","+t2_JSA+ ","+t2_Diag );
     }
 }
}

// 