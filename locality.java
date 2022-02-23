//***********************************************************************************************
// This code is used for the purpose of memory performance
// Code for general matrix multiplication in 6 versions
// Generate random matrix A, B size of n*n
// Method 1: Apply 6-version of matrix multiplicaiton C= C+A*B in 2D ,  rearranging loops to improve spacial locality 
// Method 2: Apply 6-version of matrix multiplicaiton C= C+A*B in 1D , blocking technique to improve temporal locality

// ***********************************************************************************************

// import java.util.Arrays;
import java.util.Arrays; 
import java.lang.Object;
import java.io.FileNotFoundException;

import java.io.OutputStream;
import java.io.FilterOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class locality{
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
            // System.out.println("v11D"+Arrays.toString(arrayC1)) ;
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
           // System.out.println("v12D"+Arrays.deepToString(C1)) ;
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
            // System.out.println("v21D"+Arrays.toString(arrayC2)) ;
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
          // System.out.println("v22D"+Arrays.deepToString(C2)) ;
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
           // System.out.println("v31D"+Arrays.toString(arrayC3)) ; 
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
           // System.out.println("v32D"+Arrays.deepToString(C3)) ; 
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
           // System.out.println("v41D"+Arrays.toString(arrayC4)) ; 
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
           // System.out.println("v42D"+Arrays.deepToString(C4)) ;
         double endTime4  = System.currentTimeMillis();
         double totalTime4 = (endTime4 - startTime4);
        return totalTime4;
    }

  //Version5_KIJ***********************************************************************************
// //1D
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
           // System.out.println("v51D"+Arrays.toString(arrayC5)) ;  
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
             // System.out.println("v52D"+ Arrays.deepToString(C5)) ; 
         double endTime5 = System.currentTimeMillis();
         double totalTime5 = (endTime5 - startTime5);
        return totalTime5;
       }

  //Version6_IKJ***********************************************************************************
// //1D
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
           // System.out.println("v61D"+ Arrays.toString(arrayC6)) ;     
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
            // System.out.println("v62D"+ Arrays.deepToString(C6)) ;
         double endTime6 = System.currentTimeMillis();
         double totalTime6 = (endTime6 - startTime6);
        return totalTime6;
  }
 
//Blocked matrix multiplication
  //1D
   public static double  Version_BIJK1(int n, double[] arrayA7,   double[] arrayB7, int bsize){
        double[] arrayC7= new double [n*n];
          // int bsize=25;
          int i,j,k, i1, j1, k1;
         
          // double sum;
         double startTime7 = System.currentTimeMillis();
  for (i1=0; i1<n ; i1+=bsize){
    for (j1=0; j1<n ; j1+=bsize){
        for (k1=0; k1<n ; k1+=bsize){
            // bsize * bsize mini matrix multiplication    
          for (i=i1; i<=Math.min(i1+bsize-1, n); i++){
             for (j=j1; j<=Math.min(j1+bsize-1, n) ; j++){
                for (k=k1; k<=Math.min(k1+bsize-1, n); k++){       
                  arrayC7[i*n+j]+=arrayA7[i*n+k]*arrayB7[k*n+j];
                 }     
              }
          }
        }        
    }
  }
    // System.out.println("v71D"+ Arrays.toString(arrayC7)) ;
         double endTime7 = System.currentTimeMillis();
         double totalTime7 = (endTime7 - startTime7);
        return totalTime7;

}
  //2D  

  public static double  Version_BIJK2(int n, double[][] A7,  double[][] B7, int bsize){
         double[][] C7= new double [n][n];
          // int bsize=25;
          int i,j,k, i1,j1, k1;
          // int en=bsize*(n/bsize);  // Amount that fits evenly into block
          // double sum;
         double startTime7 = System.currentTimeMillis();
       
  for (i1=0; i1<n ; i1+=bsize){
    for (j1=0; j1<n ; j1+=bsize){
      for (k1=0; k1<n ; k1+=bsize){
        for (i=i1; i<=Math.min(i1+bsize-1, n); i++){
             for (j=j1; j<=Math.min(j1+bsize-1, n) ; j++){
                for (k=k1; k<=Math.min(k1+bsize-1, n) ; k++){ 
                   C7[i][j]+=A7[i][k]*B7[k][j];                  
                 }                
             }
        }        
      }
    }
  }
    // System.out.println("v72D"+ Arrays.deepToString(C7)) ;
         double endTime7 = System.currentTimeMillis();
         double totalTime7 = (endTime7 - startTime7);
        return totalTime7;

}

//**************************************************************************************
//1D
 public static double  Version_BKJI1(int n, double[] arrayA44,   double[] arrayB44, int bsize){
        double[] arrayC44= new double [n*n];
          // int bsize=25;
          int i,j,k, i1, j1, k1;
         
          double r;
         double startTime44 = System.currentTimeMillis();
 
     for (k1=0; k1<n ; k1+=bsize){
         for (j1=0; j1<n ; j1+=bsize){
           for (i1=0; i1<n ; i1+=bsize){
       
            // bsize * bsize mini matrix multiplication    
         
             for (k=k1; k<=Math.min(k1+bsize-1, n); k++){  
                for (j=j1; j<=Math.min(j1+bsize-1, n) ; j++){
                     r= arrayB44[k*n+j];
                   for (i=i1; i<=Math.min(i1+bsize-1, n); i++){
                     arrayC44[i*n+j]+=arrayA44[i*n+k] * r;
                 }     
              }
          }
        }        
    }
  }
      // System.out.println("v441D"+ Arrays.toString(arrayC44)) ;
         double endTime44 = System.currentTimeMillis();
         double totalTime44 = (endTime44 - startTime44);
        return totalTime44;

}

//2D
  public static double  Version_BKJI2(int n, double[][] A44,  double[][] B44, int bsize){
         double[][] C44= new double [n][n];
          // int bsize=25;
          int i,j,k, i1,j1, k1;
          // int en=bsize*(n/bsize);  // Amount that fits evenly into block
         double r;
         double startTime44 = System.currentTimeMillis();
       
 
    for (k1=0; k1<n ; k1+=bsize){
       for (j1=0; j1<n ; j1+=bsize){ 
          for (i1=0; i1<n ; i1+=bsize){
            
            for (k=k1; k<=Math.min(k1+bsize-1, n) ; k++){               
               for (j=j1; j<=Math.min(j1+bsize-1, n) ; j++){  
                  r= B44[k][j];
             for (i=i1; i<=Math.min(i1+bsize-1, n); i++){
                             
                 C44[i][j]+=A44[i][k]*r;                  
                 }                
             }
        }        
      }
    }
  }
      // System.out.println("v442D"+ Arrays.deepToString(C44)) ;
         double endTime44 = System.currentTimeMillis();
         double totalTime44 = (endTime44 - startTime44);
        return totalTime44;

}
//**************************************************************************************
//1D
 public static double  Version_BIKJ1(int n, double[] arrayA8,   double[] arrayB8, int bsize){
        double[] arrayC8= new double [n*n];
          // int bsize=25;
          int i,j,k, i1, j1, k1;
         
          double r;
         double startTime8 = System.currentTimeMillis();
  for (i1=0; i1<n ; i1+=bsize){
     for (k1=0; k1<n ; k1+=bsize){
         for (j1=0; j1<n ; j1+=bsize){
       
            // bsize * bsize mini matrix multiplication    
          for (i=i1; i<=Math.min(i1+bsize-1, n); i++){
             for (k=k1; k<=Math.min(k1+bsize-1, n); k++){  
               r= arrayA8[i*n+k];

                for (j=j1; j<=Math.min(j1+bsize-1, n) ; j++){
                     arrayC8[i*n+j]+=r*arrayB8[k*n+j];
                 }     
              }
          }
        }        
    }
  }
     // System.out.println("v81D"+ Arrays.toString(arrayC8)) ;
         double endTime8 = System.currentTimeMillis();
         double totalTime8 = (endTime8 - startTime8);
        return totalTime8;

}

//2D
  public static double  Version_BIKJ2(int n, double[][] A8,  double[][] B8, int bsize){
         double[][] C8= new double [n][n];
          // int bsize=25;
          int i,j,k, i1,j1, k1;
          // int en=bsize*(n/bsize);  // Amount that fits evenly into block
         double r;
         double startTime8 = System.currentTimeMillis();
       
  for (i1=0; i1<n ; i1+=bsize){
    for (k1=0; k1<n ; k1+=bsize){
       for (j1=0; j1<n ; j1+=bsize){ 
        for (i=i1; i<=Math.min(i1+bsize-1, n); i++){
           for (k=k1; k<=Math.min(k1+bsize-1, n) ; k++){ 
               r= A8[i][k];
             for (j=j1; j<=Math.min(j1+bsize-1, n) ; j++){               
                 C8[i][j]+=r*B8[k][j];                  
                 }                
             }
        }        
      }
    }
  }
     // System.out.println("v82D"+ Arrays.deepToString(C8)) ;
         double endTime8 = System.currentTimeMillis();
         double totalTime8 = (endTime8 - startTime8);
        return totalTime8;

}


// public static double  Version_BKJI1(int n, double[] arrayA8,   double[] arrayB8){
//         double[] arrayC8= new double [n*n];
//           int bsize=25;
//           int i,j,k, i1, j1, k1;
         
//            double r;
//          double startTime8 = System.currentTimeMillis();
//         for (k1=0; k1<n ; k1+=bsize);     
//          for (j1=0; j1<n ; j1+=bsize){
//           for (i1=0; i1<n ; i1+=bsize){
       
//             // bsize * bsize mini matrix multiplication 
//              for (k=k1; k<=Math.min(k1+bsize-1, n); k++){  
//                for (j=j1; j<=Math.min(j1+bsize-1, n) ; j++){  
//                r=  arrayB8[k*n+j];  
//                   for (i=i1; i<=Math.min(i1+bsize-1, n); i++){
//                      arrayC8[i*n+j]+=arrayA8[i*n+k]*r;
//                  }     
//               }
//           }
//         }        
//     }
//   }
//     // System.out.println("v71D"+ Arrays.toString(arrayC7)) ;
//          double endTime8 = System.currentTimeMillis();
//          double totalTime8 = (endTime8 - startTime8);
//         return totalTime8;

// }

// //2D
//   public static double  Version_BKJI2(int n, double[][] A8,  double[][] B8){
//          double[][] C8= new double [n][n];
//           int bsize=25;
//           int i,j,k, i1,j1, k1;
//           // int en=bsize*(n/bsize);  // Amount that fits evenly into block
//           // double sum;
//          double startTime8 = System.currentTimeMillis();
//   for (k1=0; k1<n ; k1+=bsize){
//       for (j1=0; j1<n ; j1+=bsize){      
//         for (i1=0; i1<n ; i1+=bsize){
    
//         for (i=i1; i<=Math.min(i1+bsize-1, n); i++){
//            for (k=k1; k<=Math.min(k1+bsize-1, n) ; k++){ 
//              for (j=j1; j<=Math.min(j1+bsize-1, n) ; j++){               
//                  C8[i][j]+=A8[i][k]*B8[k][j];                  
//                  }                
//              }
//         }        
//       }
//     }
//   }
//     // System.out.println("v72D"+ Arrays.deepToString(C7)) ;
//          double endTime8 = System.currentTimeMillis();
//          double totalTime8 = (endTime8 - startTime8);
//         return totalTime8;

// }






//  public static double  Version_BIKJ2(int n, double[][] A8,  double[][] B8){
//          double[][] C8= new double [n][n];
//           int bsize=2;
//           int i,j,k, jj, kk;
//           int en=bsize*(n/bsize);  // Amount that fits evenly into block
//           double sum;
//          double startTime8 = System.currentTimeMillis();
       
//   for (i=0; i<n ; i++){
//     for (k=0; k<n ; k++){
//       C8[i][k]=0;

//   for (jj=0; jj<en ; jj+=bsize){
//     for (kk=0; kk<en ; kk+=bsize){ 
//           for (i=0; i<n; i++){
//              for (k=kk; k<kk+bsize ; kk++){
//                sum=C8[i][k];
//                 for (j=jj; j<jj+bsize ; j++){ 
//                    sum+=A8[i][k]*B8[k][j];                  
//                  } 
//                  C8[i][k]=sum;    
//               }
//           }
//         }        
//     }
//   }
// }
//     System.out.println("v82D"+ Arrays.deepToString(C8)) ;
//          double endTime8 = System.currentTimeMillis();
//          double totalTime8 = (endTime8 - startTime8);
//         return totalTime8;

// }

 //============Main Function========================================================================================  
// public static void main(String[] args){ 
public static void main(String[] args) throws Throwable {
  PrintStream out = new PrintStream(new FileOutputStream("locality_blocking.xls"));
  System.setOut(out);

   // System.out.println("N" +"," + "bijk"  + "," +"bikj"  + "," +
   //                   "N" +"," + "ijk" + "," +"jik"  + "," +"jki" + ","+"kji" + "," +"kij"  + ","+ "ikj") ;  
  System.out.println("N" +"," + "V1_ijk"  + "," +"V2_jik"  + "," +"V3_jki" + ","+"V4_kji" + "," +"V5_kij"  + ","+ "V6_ikj"  + ","+ "V11_bijk" + "," + "V44_bkji"+ ","+ "V66_bikj" + "," +
                     "N" +"," + "V1_ijk2D" + "," +"V2_jik2D"  + "," +"V3_jki2D" + ","+"V4_kji2D" + "," +"V5_kij2D"  + ","+ "V6_ikj2D" + "," + "V11_bijk2D" + "," + "V44_bkji2D" + "," + "V66_bikj2D") ;       
  // System.out.println("N" +"," + "V1_ijk2D"  + "," +"V2_jik2D"  + ","+"V3_jki2D" + ","+"V4_kji2D" + "," +"V5_kij2D"  + ","+ "V6_ikj2D"+ "," + "V1T_ijk2D"
   //        + "," +"V2T_jik2D" + "," +"V3T_jki2D" + "," +"V4T_kji2D" + ","+ "V5T_kij2D" +","+ "V6T_ikj2D");
  // Increase the array size
for ( n = 100; n <=1500; n+=100) {
  int bsize=25;
  double[][] A =randomMatrixFunction(n); 
  double[][] B =randomMatrixFunction(n); 

 //get 1D array by row-wise order from A, B 
  double[] arrayA=getArray(A, n);          
  double[] arrayB=getAr 
 
//Timing for C=C+AB in 1D, 2D
    double t1_ijk1=Version1_IJK1(n, arrayA, arrayB); 
   double t1_ijk2=Version1_IJK2(n, A, B); 

   double t2_jik1=Version2_JIK1(n, arrayA, arrayB); 
   double t2_jik2=Version2_JIK2(n, A, B); 

  double t3_jki1=Version3_JKI1(n, arrayA, arrayB);
  double t3_jki2=Version3_JKI2(n, A, B);

  double t4_kji1=Version4_KJI1(n, arrayA, arrayB);
  double t4_kji2=Version4_KJI2(n, A, B);

  double t5_kij1=Version5_KIJ1(n, arrayA, arrayB);
  double t5_kij2=Version5_KIJ2(n, A, B);

  double t6_ikj1=Version6_IKJ1(n, arrayA, arrayB);
  double t6_ikj2=Version6_IKJ2(n, A, B);
//blockinng ijk version and ikj version
 double t11_bijk1=Version_BIJK1(n, arrayA, arrayB, bsize); 
double t11_bijk2=Version_BIJK2(n, A, B, bsize); 

double t44_bkji1=Version_BKJI1(n, arrayA, arrayB, bsize);
  double t44_bkji2=Version_BKJI2(n, A, B, bsize);

double t66_bikj1=Version_BIKJ1(n, arrayA, arrayB, bsize); 
 double t66_bikj2=Version_BIKJ2(n, A, B, bsize); 



// //Timing for C=C+A'B in 1D, 2D
//    double t1T_ijk1=Version1T_IJK1(n, arrayA, arrayB);  
//    // double t1T_ijk2=Version1T_IJK2(n, A, B);  

//    double t2T_jik1=Version2T_JIK1(n, arrayA, arrayB); 
//   // double t2T_jik2=Version2T_JIK2(n, A, B); 

//    double t3T_jki1=Version3T_JKI1(n, arrayA, arrayB);
//   //double t3T_jki2=Version3T_JKI2(n, A, B); 

//    double t4T_kji1=Version4T_KJI1(n, arrayA, arrayB);
//   // double t4T_kji2=Version4T_KJI2(n, A, B); 

//   double t5T_kij1=Version5T_KIJ1(n, arrayA, arrayB);
//   // double t5T_kij2=Version5T_KIJ2(n, A, B); 

//   double t6T_ikj1=Version6T_IKJ1(n, arrayA, arrayB);
//   // double t6T_ikj2=Version6T_IKJ2(n, A, B); 

  
 //  System.out.println(n +"," + t1_ijk2  + ","+t2_jik2 + "," +t3_jki2 + "," +t4_kji2  + "," + t5_kij2 + "," + t6_ikj2+ "," 
 // +  t1T_ijk2 + ","+ t2T_jik2 + "," +t3T_jki2 + "," +t4T_kji2+ "," + t5T_kij2+ ","+t6T_ikj2 );

System.out.println(n +"," + t1_ijk1  + ","+t2_jik1 + "," +t3_jki1 + "," +t4_kji1  + "," + t5_kij1 + "," + t6_ikj1 + "," + t11_bijk1 + "," + t44_bkji1 + "," + t66_bikj1 + "," +
                   n+ "," + t1_ijk2  + ","+t2_jik2 + "," +t3_jki2 + "," +t4_kji2  + "," + t5_kij2 + "," + t6_ikj2+ "," + t11_bijk2 + "," + t44_bkji2 + "," + t66_bikj2 );
  

  }                                  
 }
}