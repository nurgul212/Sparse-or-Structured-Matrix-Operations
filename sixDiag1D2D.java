//***********************************************************************************************
//this can run till N=?
// Code for general matrix multiplication in 6 versions and diagonals
// Generate random matrix A, B size of n*n
// Store the elements of matrices in 1D array in a row-wise order for generating result martrix C in 1D , to get C in 2D, just use the generated ranom matrix in 2D way
// Method 1: Apply 6-version of matrix multiplicaiton C= C+A*B in 1D and 2D 
// Method 2: Apply 6-version of matrix trasnpose multiplicaiton  C= C+A_transpose*B in 1D and 2D 
// Method 3: Matrix multiplication by diagonals for C= C+A*B
// Method 4: Matrix multiplication by diagonals for C= C+A_transpose*B
// ***********************************************************************************************

import java.util.Arrays;
import java.util.Arrays; 
import java.lang.Object;
import java.io.FileNotFoundException;

import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class sixDiag1D2D{
static int n;
// generate random matrix-----------------------------------------------------------------
  public static double [][] randomMatrixFunction(int n) { 
        int i, j;
        double [][] matrix= new double [n][n];
        for (i=0; i<matrix.length;i++){
          for ( j = 0; j < matrix.length; j++) {
            matrix[i][j]=(double) (Math.round (Math.random()*1000))/1000;
           }  
        }
      return matrix;
  }

// Store the matrix in 1D by row-wise order--------------------------------------------------
public static double [] getArray(double[][] matrix, int n) { 
	   double[]array= new double[n*n];
	   int index=0;
	   for(int i=0; i<n; i++){
	    	for(int j=0; j<n; j++){
           array[index]=matrix[i][j];
           index++;
	     	}
	    }
    return array;  
}
// 111111111111*Method1:  General matrix multiplicaton by six versions C=C+A* B  1111111111111111111111111111111111111
 //Version1_IJK***********************************************************************************
//1D
    public static double Version1_IJK1(int n, double[] arrayA1, double[] arrayB1){
          double[] arrayC1= new double [n*n];
          double startTime1 = System.currentTimeMillis();
           for(int i=0;i<n;i++){
               for(int j=0;j<n;j++){
                  for (int k=0;k<n;k++){
                  	arrayC1[i*n+j]+=arrayA1[i*n+k]*arrayB1[k*n+j];                                      
                   }
                }
            }
          // System.out.println(Arrays.toString(arrayC1)) ;
         double endTime1   = System.currentTimeMillis();
         double totalTime1 = (endTime1 - startTime1);
        return totalTime1;
     }

//2D
    public static double Version1_IJK2(int n, double[][] A1, double[][] B1){
          double[][] C1= new double [n][n];
          double startTime1 = System.currentTimeMillis();
           for(int i=0;i<n;i++){
               for(int j=0;j<n;j++){
                  for (int k=0;k<n;k++){
                     C1[i][j]+=A1[i][k]*B1[k][j];                                          
                   }
                }
            }
          // System.out.println(Arrays.deepToString(C1)) ;
         double endTime1   = System.currentTimeMillis();
         double totalTime1 = (endTime1 - startTime1);
        return totalTime1;
       }


 //Version2_JIK***********************************************************************************
 //1D      
    public static double Version2_JIK1(int n, double[] arrayA2, double[] arrayB2){
          double[] arrayC2= new double [n*n];
          double startTime2 = System.currentTimeMillis();
           for(int j=0;j<n;j++){
               for(int i=0;i<n;i++){
                  for (int k=0;k<n;k++){
                     arrayC2[i*n+j]+=arrayA2[i*n+k]*arrayB2[k*n+j];
                   }
                }
            } 
           // System.out.println(Arrays.toString(arrayC2)) ;
         double endTime2   = System.currentTimeMillis();
         double totalTime2 = (endTime2 - startTime2);
        return totalTime2;
       }

//2D
   public static double Version2_JIK2(int n, double[][] A2,   double[][] B2){
          double[][] C2= new double [n][n];
          double startTime2 = System.currentTimeMillis();
           for(int j=0;j<n;j++){
               for(int i=0;i<n;i++){
                  for (int k=0;k<n;k++){
                     C2[i][j]+=A2[i][k]*B2[k][j];
                   }
                }
            }
         // System.out.println(Arrays.deepToString(C2)) ;
         double endTime2   = System.currentTimeMillis();
         double totalTime2 = (endTime2 - startTime2);
        return totalTime2;
       }
 //Version3_JKI***********************************************************************************
 //1D      
    public static double Version3_JKI1(int n, double[] arrayA3, double[] arrayB3){
          double[] arrayC3= new double [n*n];
          double startTime3 = System.currentTimeMillis();
           for(int j=0;j<n;j++){
               for(int k=0;k<n;k++){
                double r= arrayB3[k*n+j];
                  for (int i=0;i<n;i++){
                      arrayC3[i*n+j] +=arrayA3[i*n+k]*r;
                  }
                }
            }
           // System.out.println(Arrays.toString(arrayC3)) ; 
         double endTime3  = System.currentTimeMillis();
         double totalTime3 = (endTime3 - startTime3);
        return totalTime3;
       }

//2D
   public static double Version3_JKI2(int n, double[][] A3,   double[][] B3){
          double[][] C3= new double [n][n];
          double startTime3 = System.currentTimeMillis();
           for(int j=0;j<n;j++){
               for(int k=0;k<n;k++){
                double r= B3[k][j];
                  for (int i=0;i<n;i++){
                      C3[i][j] +=A3[i][k]*r;
                  }
                }
            }
            // System.out.println(Arrays.deepToString(C3)) ; 
         double endTime3  = System.currentTimeMillis();
         double totalTime3 = (endTime3 - startTime3);
        return totalTime3;
       }

  //Version4_KJI***********************************************************************************
//1D
    public static double Version4_KJI1(int n, double[] arrayA4,   double[] arrayB4){
          double[] arrayC4= new double [n*n];
          double startTime4 = System.currentTimeMillis();
           for(int k=0;k<n;k++){
               for(int j=0;j<n;j++){
                double r= arrayB4[k*n+j];
                  for (int i=0;i<n;i++){
                      arrayC4[i*n+j] +=arrayA4[i*n+k]*r;
                  }
                }
            } 
           // System.out.println(Arrays.toString(arrayC4)) ; 
         double endTime4  = System.currentTimeMillis();
         double totalTime4 = (endTime4 - startTime4);
        return totalTime4;
       }

//2D
   public static double Version4_KJI2(int n, double[][] A4,   double[][] B4){
          double[][] C4= new double [n][n];
          double startTime4 = System.currentTimeMillis();
           for(int k=0;k<n;k++){
               for(int j=0;j<n;j++){
                double r= B4[k][j];
                  for (int i=0;i<n;i++){
                      C4[i][j] +=A4[i][k]*r;
                  }
                }
            }
            // System.out.println(Arrays.deepToString(C4)) ;
         double endTime4  = System.currentTimeMillis();
         double totalTime4 = (endTime4 - startTime4);
        return totalTime4;
    }

  //Version5_KIJ***********************************************************************************
//1D
    public static double Version5_KIJ1(int n, double[] arrayA5,   double[] arrayB5){
          double[] arrayC5= new double [n*n];
          double startTime5 = System.currentTimeMillis();
           for(int k=0;k<n;k++){
               for(int i=0;i<n;i++){
                double r= arrayA5[i*n+k];
                  for (int j=0;j<n;j++){
                      arrayC5[i*n+j] +=arrayB5[k*n+j]*r;
                  }
                }
            }
           // System.out.println(Arrays.toString(arrayC5)) ;  
         double endTime5 = System.currentTimeMillis();
         double totalTime5 = (endTime5 - startTime5);
        return totalTime5;
       }

//2D
     public static double Version5_KIJ2(int n, double[][] A5,   double[][] B5){
          double[][] C5= new double [n][n];
          double startTime5 = System.currentTimeMillis();
           for(int k=0;k<n;k++){
               for(int i=0;i<n;i++){
                double r= A5[i][k];
                  for (int j=0;j<n;j++){
                      C5[i][j] +=r*B5[k][j];
                  }
                }
            }
            // System.out.println(Arrays.deepToString(C5)) ; 
         double endTime5 = System.currentTimeMillis();
         double totalTime5 = (endTime5 - startTime5);
        return totalTime5;
       }

  //Version6_IKJ***********************************************************************************
//1D
    public static double Version6_IKJ1(int n, double[] arrayA6,   double[] arrayB6){
          double[] arrayC6= new double [n*n];
          double startTime6 = System.currentTimeMillis();
           for(int i=0;i<n;i++){
               for(int k=0;k<n;k++){
                double r= arrayA6[i*n+k];
                  for (int j=0;j<n;j++){
                      arrayC6[i*n+j] +=arrayB6[k*n+j]*r;
                  }
                }
            }
           // System.out.println(Arrays.toString(arrayC6)) ;     
         double endTime6 = System.currentTimeMillis();
         double totalTime6 = (endTime6 - startTime6);
        return totalTime6;
       }
//2D
   public static double Version6_IKJ2(int n, double[][] A6,   double[][] B6){
          double[][] C6= new double [n][n];
          double startTime6 = System.currentTimeMillis();
           for(int i=0;i<n;i++){
               for(int k=0;k<n;k++){
                double r= A6[i][k];
                  for (int j=0;j<n;j++){
                      C6[i][j] +=r*B6[k][j];
                  }
                }
            } 
            // System.out.println(Arrays.deepToString(C6)) ;
         double endTime6 = System.currentTimeMillis();
         double totalTime6 = (endTime6 - startTime6);
        return totalTime6;
       }
 // 22222222222222222   Method2:  General matrix multiplicaton by six versions C=A_transpose * B 222222222222222222222222222222222
 //Version1T_IJK-----------------------------------------------------------------------------------------------------------------
//1D    
    public static double Version1T_IJK1(int n, double[] arrayA1, double[] arrayB1){    
          double[] arrayC1T= new double [n*n];
          double startTime1 = System.currentTimeMillis();
           for(int i=0;i<n;i++){
               for(int j=0;j<n;j++){
                  for (int k=0; k<n; k++){
                    arrayC1T[i*n+j] +=arrayA1[k*n+i]*arrayB1[k*n+j];                     
                   }
                }
            } 
            // System.out.println(Arrays.toString(arrayC1T)) ;  
         double endTime1   = System.currentTimeMillis();
         double totalTime1 = (endTime1 - startTime1);
         return totalTime1;          
       }
//2D
     public static double Version1T_IJK2(int n, double[][] A1, double[][] B1){
    
          double[][] C1= new double [n][n];
          double startTime1 = System.currentTimeMillis();
           for(int i=0;i<n;i++){
               for(int j=0;j<n;j++){
                  for (int k=0; k<n; k++){
                    C1[i][j] +=A1[k][i]*B1[k][j];                     
                   }
                }
            } 
          // System.out.println("C1_V1=");
           // System.out.println(Arrays.deepToString(C1));
         double endTime1   = System.currentTimeMillis();
         double totalTime1 = (endTime1 - startTime1);
         return totalTime1;
       }

//Version2T_JIK-----------------------------------------------------------------------------------------------------------------
//1D      
    public static double Version2T_JIK1(int n, double[] arrayA2,   double[] arrayB2){
    	
          double[] arrayC2T= new double [n*n];
          double startTime2 = System.currentTimeMillis();
           for(int j=0;j<n;j++){
               for(int i=0;i<n;i++){
                   arrayC2T[i*n+j]=0;
                 for (int k=0;k<n;k++){
                   arrayC2T[i*n+j] +=arrayA2[k*n+i]*arrayB2[k*n+j];
                  }
                }
            }
            // System.out.println(Arrays.toString(arrayC2T)) ;   
         double endTime2   = System.currentTimeMillis();
         double totalTime2 = (endTime2 - startTime2);
        return totalTime2;
       }

//2D
 public static double Version2T_JIK2(int n, double[][] A2,   double[][] B2){     
          double[][] C2= new double [n][n];
          double startTime2 = System.currentTimeMillis();
           for(int j=0;j<n;j++){
               for(int i=0;i<n;i++){
                   C2[i][j]=0;
                 for (int k=0;k<n;k++){
                   C2[i][j] +=A2[k][i]*B2[k][j];
                  }
                }
            } 
             // System.out.println("C2_V2=");
             // System.out.println(Arrays.deepToString(C2));
         double endTime2   = System.currentTimeMillis();
         double totalTime2 = (endTime2 - startTime2);
        return totalTime2;
       }

 //Version3T_JKI-------------------------------------------------------------------------------------------------------------
//1D       
    public static double Version3T_JKI1(int n, double[] arrayA3, double[] arrayB3){
          double[] arrayC3T= new double [n*n];
          double startTime3 = System.currentTimeMillis();
           for(int j=0;j<n;j++){
               for(int k=0;k<n;k++){
                double r= arrayB3[k*n+j];
                  for (int i=0;i<n;i++){
                      arrayC3T[i*n+j] +=arrayA3[k*n+i]*r;
                  }
                }
            }
            // System.out.println(Arrays.toString(arrayC3T)) ;   
         double endTime3  = System.currentTimeMillis();
         double totalTime3 = (endTime3 - startTime3);
        return totalTime3;
       }

//2D 
public static double Version3T_JKI2(int n, double[][] A3,   double[][] B3){
          double[][] C3= new double [n][n];
          double startTime3 = System.currentTimeMillis();
           for(int j=0;j<n;j++){
               for(int k=0;k<n;k++){
                double r= B3[k][j];
                  for (int i=0;i<n;i++){
                      C3[i][j] +=A3[k][i]*r;
                  }
                }
            } 
            //  System.out.println("C3_V3=");
            // System.out.println(Arrays.deepToString(C3));
         double endTime3  = System.currentTimeMillis();
         double totalTime3 = (endTime3 - startTime3);
        return totalTime3;
       }
 //Version4T_KJI----------------------------------------------------------------------------------------------
//1D 
    public static double Version4T_KJI1(int n, double[] arrayA4,   double[] arrayB4){
          double[] arrayC4T= new double [n*n];
          double startTime4 = System.currentTimeMillis();
           for(int k=0;k<n;k++){
               for(int j=0;j<n;j++){
                double r= arrayB4[k*n+j];
                  for (int i=0;i<n;i++){
                      arrayC4T[i*n+j] +=arrayA4[k*n+i]*r;
                  }
                }
            }
            // System.out.println(Arrays.toString(arrayC4T)) ;   
         double endTime4  = System.currentTimeMillis();
         double totalTime4 = (endTime4 - startTime4);
        return totalTime4;
       }
//2D
public static double Version4T_KJI2(int n, double[][] A4,   double[][] B4){
          double[][] C4= new double [n][n];
          double startTime4 = System.currentTimeMillis();
           for(int k=0;k<n;k++){
               for(int j=0;j<n;j++){
                double r= B4[k][j];
                  for (int i=0;i<n;i++){
                      C4[i][j] +=A4[k][i]*r;
                  }
                }
            } 
             // System.out.println("C4_V4=");
           // System.out.println(Arrays.deepToString(C4));
         double endTime4  = System.currentTimeMillis();
         double totalTime4 = (endTime4 - startTime4);
        return totalTime4;
       }
  //Version5T_KIJ----------------------------------------------------------------------------------------------
//1D 
     public static double Version5T_KIJ1(int n, double[] arrayA5,   double[] arrayB5){
          double[] arrayC5T= new double [n*n];
          double startTime5 = System.currentTimeMillis();
           for(int k=0;k<n;k++){
               for(int i=0;i<n;i++){
                double r= arrayA5[k*n+i];
                  for (int j=0;j<n;j++){
                      arrayC5T[i*n+j] +=arrayB5[k*n+j]*r;
                  }
                }
            } 
            // System.out.println(Arrays.toString(arrayC5T)) ;           
         double endTime5 = System.currentTimeMillis();
         double totalTime5 = (endTime5 - startTime5);
        return totalTime5;
       }
//2D
    public static double Version5T_KIJ2(int n, double[][] A5,   double[][] B5){
          double[][] C5= new double [n][n];
          double startTime5 = System.currentTimeMillis();
           for(int k=0;k<n;k++){
               for(int i=0;i<n;i++){
                double r= A5[k][i];
                  for (int j=0;j<n;j++){
                      C5[i][j] +=r*B5[k][j];
                  }
                }
            }
            // System.out.println("C5_V5=");
          // System.out.println(Arrays.deepToString(C5)); 
         double endTime5 = System.currentTimeMillis();
         double totalTime5 = (endTime5 - startTime5);
        return totalTime5;
       }

   //Version6T_IKJ----------------------------------------------------------------------------------------------
//1D     
    public static double Version6T_IKJ1(int n, double[] arrayA6,   double[] arrayB6){
          double[] arrayC6T= new double [n*n];
          double startTime6 = System.currentTimeMillis();
           for(int i=0;i<n;i++){
               for(int k=0;k<n;k++){
                double r= arrayA6[k*n+i];
                  for (int j=0;j<n;j++){
                      arrayC6T[i*n+j] +=arrayB6[k*n+j]*r;
                  }
                }
            }
            // System.out.println(Arrays.toString(arrayC6T)) ;  
         double endTime6 = System.currentTimeMillis();
         double totalTime6 = (endTime6 - startTime6);
        return totalTime6;
       }

//2D
 public static double Version6T_IKJ2(int n, double[][] A6,   double[][] B6){
          double[][] C6= new double [n][n];
          double startTime6 = System.currentTimeMillis();
           for(int i=0;i<n;i++){
               for(int k=0;k<n;k++){
                double r= A6[k][i];
                  for (int j=0;j<n;j++){
                      C6[i][j] +=r*B6[k][j];
                  }
                }
            } 
          //   System.out.println("C6_V6=");
         // System.out.println(Arrays.deepToString(C6)); 
         double endTime6 = System.currentTimeMillis();
         double totalTime6 = (endTime6 - startTime6);
        return totalTime6;
       }

 //3333333333333333333333    Method 3: Matrix multiplication by diagonals for C= C+A*B _ 3333333333333333333333333333333333333333333333333333
 // Store elements of matrix in 1D array by diagonal order------------------------      
 public static double [] diagStoreFunction(double[][] A) { 
      double[] array=new double[n*n];
      int index=0;  
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
        } 
        return array;
      }
//Get the requested diagonal array ------------------------------------------------------
 public static double [] getDiagArrayFunction(double[] diagStoreArray, int k) {
    double[] getarray=new double[n-Math.abs(k)];
    if ( k >= 0 && k<=n-1){
      int startindx=k*n-k*(k-1)/2;
      int endindx=startindx + n-k-1;
      int index=0;
      for (int i=startindx; i<=endindx;i++){                   
          getarray[index]=diagStoreArray[i];
          index++;
      }
    } 
   else if ( k <0 && k>=-(n-1)){ 
      int abs_k=Math.abs(k); // take the absolute value of k
      int startindx=n*(n+1)/2 + (abs_k-1)*n - abs_k*(abs_k-1)/2 ;
      int endindx=startindx + n-abs_k-1;
      int index=0;
     for (int i=startindx; i<=endindx;i++){                   
          getarray[index]=diagStoreArray[i];
          index++;           
      }
    }
  return getarray;
}
  // Cut the elements from getDiagArray--------------------------------------------------------
public static double [] cutElementFunction(double[] getDiagArray,int cutBegin, int cutEnd) {
     int size = getDiagArray.length;
     double[] outputArray=new double [size-cutBegin-cutEnd];  //
   for (int i=0; i<size-cutBegin-cutEnd;i++){  
         outputArray[i]=getDiagArray[i+cutBegin];  
       }
     return outputArray;
}

//----------Matrix multiplication by diagonals for C= C+A*B----------------------
public static double AB_multiplydiag(int n,double[] diagStoreArrayA, double[] diagStoreArrayB){
  double [] C = new double[n*n]; 
//getting main and super-diagonals of C
  double totaltime = 0.0;
for (int k=0; k<=n-1;k++){
 int inx_C = k*n-k*(k-1)/2 ;  
 int endinx_C = inx_C + n-k-1;
      for (int i=k+1; i<=n-1;i++){
           double[] diag_a1=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,k-i),0,k);
           double[] diag_b1=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,i),0,0);
           int size1=diag_b1.length;
           double start1   = System.currentTimeMillis();
           for (int j=0; j<=size1-1;j++){
              C[inx_C+i-k+j]= C[inx_C+i-k+j]+diag_a1[j]*diag_b1[j];
            }
           double end1 = (System.currentTimeMillis() - start1);
           totaltime = totaltime + end1;

          double [] diag_a2=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,i),0,0);
          double [] diag_b2=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,k-i),k,0);
          int size2=diag_a2.length;
           
           double start2 = System.currentTimeMillis();
           for (int j=0; j<=size2-1;j++){   
              C[inx_C+j]= C[inx_C+j]+diag_a2[j]*diag_b2[j]; 
            }  
            double end2 = (System.currentTimeMillis() - start2);
            totaltime = totaltime + end2;             
      }
 
      for (int i=0; i<=k; i++){
         double [] diag_a3=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,i),0,k-i);
         double [] diag_b3=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,k-i),i,0);
         int size3=diag_a3.length;

          double start3 = System.currentTimeMillis();
          for (int j=0; j<=size3-1;j++){
             C[inx_C+j]= C[inx_C+j]+diag_a3[j]*diag_b3[j];
          }
          double end3 = (System.currentTimeMillis() - start3);
          totaltime = totaltime + end3;
      }
}

//getting sub-diagonals of C
 for (int k=1; k<=n-1;k++){
   int inx_C = n*(n+1)/2 + (k-1)*n - k*(k-1)/2;  
   int endinx_C = inx_C + n-k-1;
    for (int i=k+1; i<=n-1;i++){
          double [] diag_a1=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,-i),0,0);
          double [] diag_b1=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,i-k),0,k);
          int size1=diag_b1.length;
          double start4 = System.currentTimeMillis();
         for (int j=0; j<=size1-1;j++){
            C[inx_C+i-k+j]= C[inx_C+i-k+j]+diag_a1[j]*diag_b1[j];
         }
         double end4 = (System.currentTimeMillis() - start4);
         totaltime = totaltime + end4;

         double [] diag_a2=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,i-k),k,0);
         double [] diag_b2=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,-i),0,0);
         int size2=diag_a2.length;
  
          double start5 = System.currentTimeMillis();
         for (int j=0; j<=size2-1;j++){   
          C[inx_C+j]= C[inx_C+j]+diag_a2[j]*diag_b2[j];  
          }
          double end5 = (System.currentTimeMillis() - start5);
          totaltime = totaltime + end5;
    }
      for (int i=0; i<=k; i++){
        double [] diag_a3=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,-i),k-i ,0);
        double [] diag_b3=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,i-k),0,i);
        int size3=diag_a3.length;

        double start6 = System.currentTimeMillis();
        for (int j=0; j<=size3-1;j++){
          C[inx_C+j]= C[inx_C+j]+diag_a3[j]*diag_b3[j];
         }
          double end6 = (System.currentTimeMillis() - start6);
          totaltime = totaltime + end6;
      }       
 }
 // System.out.println(Arrays.toString(C)) ;
 return totaltime;
}
// 4444444444444444444444 Method 4: Matrix multiplication by diagonals for C= C+A_transpose*B 444444444444444444444444444444
//------------------Mehod4: Matrix multiplication by diagonals for C= C+A_transpose*B----------------
public static double ATB_multiplydiag(int n, double[] diagStoreArrayA, double[] diagStoreArrayB){
 double [] C = new double[n*n]; 
 double totaltime = 0.0;
 //getting main and super-diagonals of C
    for (int k=0; k<=n-1;k++){
       int inx_C = k*n-k*(k-1)/2 ;  
       int endinx_C = inx_C + n-k-1;
            for (int i=k+1; i<=n-1;i++){
                double[] diag_a1=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,i-k),0,k);
                double [] diag_b1=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,i),0,0);
                int size1=diag_b1.length;
                double start1   = System.currentTimeMillis();
                 for (int j=0; j<=size1-1;j++){
                    C[inx_C+i-k+j]= C[inx_C+i-k+j]+diag_a1[j]*diag_b1[j];
                  }
                 double end1 = (System.currentTimeMillis() - start1);
                 totaltime = totaltime + end1;

                double [] diag_a2=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,-i),0,0);
                double [] diag_b2=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,k-i),k,0);
                int size2=diag_a2.length;
                 
                double start2   = System.currentTimeMillis();
                for (int j=0; j<=size2-1;j++){   
                  C[inx_C+j]= C[inx_C+j]+diag_a2[j]*diag_b2[j]; 
                } 
           
               double end2 = (System.currentTimeMillis() - start2);
               totaltime = totaltime + end2;       
           }
 
      for (int i=0; i<=k; i++){
         double [] diag_a3=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,-i),0,k-i);
         double [] diag_b3=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,k-i),i,0);
         int size3=diag_a3.length;

         double start3   = System.currentTimeMillis();
          for (int j=0; j<=size3-1;j++){
             C[inx_C+j]= C[inx_C+j]+diag_a3[j]*diag_b3[j];
          }
          double end3 = (System.currentTimeMillis() - start3);
           totaltime = totaltime + end3;
      }
}

//getting sub-diagonals of C

for (int k=1; k<=n-1;k++){
 int inx_C = n*(n+1)/2 + (k-1)*n - k*(k-1)/2;  
 int endinx_C = inx_C + n-k-1;
    for (int i=k+1; i<=n-1;i++){
          double [] diag_a1=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,i),0,0);
          double [] diag_b1=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,i-k),0,k);
          int size1=diag_b1.length;
        
        double start4   = System.currentTimeMillis();
         for (int j=0; j<=size1-1;j++){
            C[inx_C+i-k+j]= C[inx_C+i-k+j]+diag_a1[j]*diag_b1[j];
         }
         double end4 = (System.currentTimeMillis() - start4);
           totaltime = totaltime + end4;
         
         double [] diag_a2=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,k-i),k,0);
         double [] diag_b2=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,-i),0,0);
         int size2=diag_a2.length;

         double start5   = System.currentTimeMillis();
         for (int j=0; j<=size2-1;j++){   
          C[inx_C+j]= C[inx_C+j]+diag_a2[j]*diag_b2[j];  
          }
           double end5 = (System.currentTimeMillis() - start5);
           totaltime = totaltime + end5;

    }
     for (int i=0; i<=k; i++){
      double [] diag_a3=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,i),k-i ,0);
      double [] diag_b3=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,i-k),0,i);
      int size3=diag_a3.length;
         double start6   = System.currentTimeMillis();
        for (int j=0; j<=size3-1;j++){
          C[inx_C+j]= C[inx_C+j]+diag_a3[j]*diag_b3[j];
         }
          double end6 = (System.currentTimeMillis() - start6);
           totaltime = totaltime + end6;
      }       
}
// System.out.println(Arrays.toString(C)); 
return totaltime;

}
 //============Main Function========================================================================================  
 // public static void main(String[] args){ 
public static void main(String[] args) throws Throwable {
  PrintStream out = new PrintStream(new FileOutputStream("sixDiag1.xls"));
  System.setOut(out);
  System.out.println("N" +"," + "V1_ijk"  + "," +"V2_jik"  + ","+"V3_jki" + ","+"V4_kji" + "," +"V5_kij"  + ","+ "V6_ikj"+ ","+"AB_diag"+ "," + "V1T_ijk"
          + "," +"V2T_jik" + "," +"V3T_jki" + "," +"V4T_kji" + ","+ "V5T_kij" +","+ "V6T_ikj"+ "," + "ATB_diag");

   // System.out.println("N" +"," + "V1_ijk2D"  + "," +"V2_jik2D"  + ","+"V3_jki2D" + ","+"V4_kji2D" + "," +"V5_kij2D"  + ","+ "V6_ikj2D"+ "," + "V1T_ijk2D"
   //        + "," +"V2T_jik2D" + "," +"V3T_jki2D" + "," +"V4T_kji2D" + ","+ "V5T_kij2D" +","+ "V6T_ikj2D");
  // Increase the array size
for ( n = 1500; n <=4200  ; n+=100) {
  double[][] A =randomMatrixFunction(n); 
  double[][] B =randomMatrixFunction(n); 

 //get 1D array by row-wise order from A, B 
  double[] arrayA=getArray(A, n);          
  double[] arrayB=getArray(B, n);  

 //get 1D array by diagonal order from A, B

 double[] diagStoreArrayA=diagStoreFunction(A);
 double[] diagStoreArrayB=diagStoreFunction(B);

//Timing for C=C+AB in 1D, 2D
   double t1_ijk1=Version1_IJK1(n, arrayA, arrayB); 
 // double t1_ijk2=Version1_IJK2(n, A, B); 

   double t2_jik1=Version2_JIK1(n, arrayA, arrayB); 
  // double t2_jik2=Version2_JIK2(n, A, B); 

   double t3_jki1=Version3_JKI1(n, arrayA, arrayB);
  // double t3_jki2=Version3_JKI2(n, A, B);

   double t4_kji1=Version4_KJI1(n, arrayA, arrayB);
 // double t4_kji2=Version4_KJI2(n, A, B);

   double t5_kij1=Version5_KIJ1(n, arrayA, arrayB);
  // double t5_kij2=Version5_KIJ2(n, A, B);

  double t6_ikj1=Version6_IKJ1(n, arrayA, arrayB);
  // double t6_ikj2=Version6_IKJ2(n, A, B);

   double t7_diag=AB_multiplydiag(n, diagStoreArrayA, diagStoreArrayB);

//Timing for C=C+A'B in 1D, 2D
   double t1T_ijk1=Version1T_IJK1(n, arrayA, arrayB);  
   // double t1T_ijk2=Version1T_IJK2(n, A, B);  

   double t2T_jik1=Version2T_JIK1(n, arrayA, arrayB); 
  // double t2T_jik2=Version2T_JIK2(n, A, B); 

   double t3T_jki1=Version3T_JKI1(n, arrayA, arrayB);
  //double t3T_jki2=Version3T_JKI2(n, A, B); 

   double t4T_kji1=Version4T_KJI1(n, arrayA, arrayB);
  // double t4T_kji2=Version4T_KJI2(n, A, B); 

  double t5T_kij1=Version5T_KIJ1(n, arrayA, arrayB);
  // double t5T_kij2=Version5T_KIJ2(n, A, B); 

  double t6T_ikj1=Version6T_IKJ1(n, arrayA, arrayB);
  // double t6T_ikj2=Version6T_IKJ2(n, A, B); 

  double t7T_diag=ATB_multiplydiag(n, diagStoreArrayA, diagStoreArrayB);
 //  System.out.println(n +"," + t1_ijk2  + ","+t2_jik2 + "," +t3_jki2 + "," +t4_kji2  + "," + t5_kij2 + "," + t6_ikj2+ "," 
 // +  t1T_ijk2 + ","+ t2T_jik2 + "," +t3T_jki2 + "," +t4T_kji2+ "," + t5T_kij2+ ","+t6T_ikj2 );

System.out.println(n +"," + t1_ijk1  + ","+t2_jik1 + "," +t3_jki1 + "," +t4_kji1  + "," + t5_kij1 + "," + t6_ikj1+ "," + t7_diag
 + ","+ t1T_ijk1 + ","+ t2T_jik1 + "," +t3T_jki1 + "," +t4T_kji1+ "," + t5T_kij1+ ","+t6T_ikj1+","+ t7T_diag  );
  
// System.out.print(n +"," + t1_ijk1  + ","+t2_jik1 + "," +t3_jki1 + "," +t4_kji1  + "," + t5_kij1 + "," + t6_ikj1+ "," + t7_diag
//  + ","+ t1T_ijk1 + ","+ t2T_jik1 + "," +t3T_jki1 + "," +t4T_kji1+ "," + t5T_kij1+ ","+t6T_ikj1+","+ t7T_diag + "," 
//  + n +"," + t1_ijk2  + ","+t2_jik2 + "," +t3_jki2 + "," +t4_kji2  + "," + t5_kij2 + "," + t6_ikj2+ "," 
//  +  t1T_ijk2 + ","+ t2T_jik2 + "," +t3T_jki2 + "," +t4T_kji2+ "," + t5T_kij2+ ","+t6T_ikj2 );
          // System.out.print("\n");
        }                                  
 }
}