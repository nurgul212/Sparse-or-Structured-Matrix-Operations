
//***************************************************************************
// Random band matrix- matrix multiplication in CRS, JSA and Diag storage formats
// Generate random band  matrix in diagonal storage format, then convert  CRS, JSA form respectively 
// Method 1: Apply C= C+AB in CRS -------Gunderson Approach
// Method 2: Apply C= C+AB in CRS ------- My Approach
// Method 3: Apply C'= C'+ A'B in CRS --- My approach

// Method 4: Apply C= C+AB in JSA ------- Gunderson approach
// Method 5: Apply C= C+AB in JSA --------Myapproach  
// Method 6: Apply C'= C'+ A'B in JSA-----My Approach

// Method 7: Apply C= C+AB in Diag   ------Theorem in Niel k paper
// Method 8: Apply C'= C'+ A'B in Diag ----Theorem in Niel k paper
// ***********************************************************************************
// import java.util.List;
import java.util.Arrays; 
import java.lang.Object;
import java.util.Random;
// import java.io.FileNotFoundException;
import java.io.OutputStream;
// import java.io.FilterOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;;
import java.text.DecimalFormat;
// import java.util.*;
// import java.util.ArrayList;
// import java.lang.*;
// import java.text.DecimalFormat;
// *********************************************************************
public class BandDiag_MM1{
public static int n;
// generate the rabdom sparse band matrix with BW, and return nnz elements in 1D array--diag Array 
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


//Method to return column index of C and row pointer of C in CRS
public static Object[] Cindexpointer(double[] valueA, int[] indexA, int[] pointerA, double[] valueB, int [] indexB, int [] pointerB, int n){
 int [] indexC = new int[n];
 int [] pointerC=new int[n+1];
 pointerC[0]=0;
 int len = -1;
 int lentemp = -1;

 boolean test = true;
 int temp[] = new int[n];
 int[] indexTemp = null;

for(int i=0;i<n;i++){
  temp[i]=-1;
}
 for(int i = 0;i<n;i++){
 int startrowa = pointerA[i];
 int stoprowa =  pointerA[i+1]-1;
    for(;startrowa<=stoprowa;startrowa++){
       int jj = indexA[startrowa];
         int startrowb = pointerB[jj];
        int  stoprowb = pointerB[jj+1]-1;
         for(;startrowb<=stoprowb;startrowb++){
           int jcol=indexB[startrowb];
           int jposition = temp[jcol];
            if(jposition == -1){
                 len++;
                 lentemp++;
                 indexC[lentemp]=jcol;
                 temp[jcol]=len;
                }
            }
    }
// System.out.println(temp.length);
for(int k=0; k<=lentemp; k++){
  temp[indexC[k]]=-1;
}
pointerC[i+1]=len+1;
 if(test){
  indexTemp=new int[len+1];
  System.arraycopy(indexC,0,indexTemp, 0, len+1);
  test=false;
 }
 else {
    if(lentemp>-1){
    int [] a= new int [len+1];
    System.arraycopy(indexTemp,0, a, 0, indexTemp.length);
    System.arraycopy(indexC, 0, a, indexTemp.length, lentemp+1);
    indexTemp=a;
     }
    }

   lentemp=-1;
   indexC=new int[n];
  }

 Object[] objectsC=new Object[]{indexTemp, pointerC};
 return objectsC;
 }

// Method 1: Apply C= C+AB in CRS -------Gunderson Approach 11111111111111111111111111111111111111111111111111111111111111111111111111111111111111
//This is the numerical approach 
 public static double ABinCRS_Gunderson(double[] valueA, int[] indexA, int[] pointerA, double[] valueB, int [] indexB, int [] pointerB, int [] indexC, int [] pointerC, int n ){
   double[] valueC= new double[indexC.length];  
  int len = -1;
  int temp[] = new int[n]; 
 for(int i=0;i<n;i++){
  temp[i]=-1;
}

 double startTime1 = System.currentTimeMillis();
for(int i = 0;i<n;i++){
  int startrowa = pointerA[i];
  int stoprowa = pointerA[i+1]-1;
     for(;startrowa<=stoprowa;startrowa++){
     double scalar = valueA[startrowa];
         int jj = indexA[startrowa];
         int startrowb = pointerB[jj];
         int stoprowb = pointerB[jj+1]-1;
       for(;startrowb<=stoprowb;startrowb++){
      int jcol=indexB[startrowb];
      int jposition = temp[jcol];
      if(jposition == -1){
      len++;
      temp[jcol]=len;
      valueC[len]=scalar*valueB[startrowb];
      }
           else{
           valueC[jposition]+=scalar*valueB[startrowb];
            }
         }
     }
for(int k=pointerC[i];k<=len;k++){
  temp[indexC[k]]=-1;
 }
}

 // System.out.println("Gunderson_CRS_C="+Arrays.toString(valueC));
     double endTime1   = System.currentTimeMillis();
      double totalTime1= endTime1 - startTime1;
      return totalTime1; 
}

// Method 2: Apply C= C+AB in CRS ------- My Approach 2222222222222222222222222222222222222222222222222222222222222222222222
public static double ABinCRS_myApproach(double[] valA, int [] colindA, int [] ptrA, double[] valB, int [] colindB, int [] ptrB, int [] colindC, int [] ptrC, int n ){
  double[] valueC= new double[colindC.length];
  double startTime1 = System.currentTimeMillis();
 for(int i =0;i<n;i++){
  int startrowa = ptrA[i];
  int stoprowa = ptrA[i+1]-1; 
     for( ;startrowa<=stoprowa;startrowa++){
        double scalar = valA[startrowa];
        int ja = colindA[startrowa];  
        int startrowb = ptrB[ja];
        int stoprowb = ptrB[ja+1]-1;           
        
        for(; startrowb<=stoprowb; startrowb++){
           int jb=colindB[startrowb];
           int jc=colindC[ptrC[i]];
            valueC[ptrC[i]-jc + jb]+=scalar*valB[startrowb];            
        }                   
      } 
  }
     
     // System.out.println("my_CRS_C="+Arrays.toString(valueC));
     double endTime1   = System.currentTimeMillis();
      double totalTime1= endTime1 - startTime1;
      return totalTime1; 
}  

// Method 3: Apply C'= C'+ A'B in CRS --- My approach  33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333
public static double ATBinCRS_myApproach(double[] valA, int [] colindA, int [] ptrA, double[] valB, int [] colindB, int [] ptrB, int [] colindC, int [] ptrC, int n){
  double[] valueC= new double[colindC.length];
  double startTime2 = System.currentTimeMillis();
 for(int i =0;i<n;i++){
  int startrowa = ptrA[i];
  int stoprowa = ptrA[i+1]-1;
 
     for( ;startrowa<=stoprowa;startrowa++){
        double scalar = valA[startrowa];
         int ja = colindA[startrowa]; // column index of A can be an index of rowpointerC 
        int startrowb = ptrB[i];
        int stoprowb = ptrB[i+1]-1;           
        
        for(; startrowb<=stoprowb; startrowb++){
           int jb=colindB[startrowb];
           int jc=colindC[ptrC[ja]];
            //poniterC=colindexA[startrowa]=j; columnindexC=jc=colindexC[ptr[ja]]; 
            valueC[ptrC[ja]-jc + jb]+=scalar*valB[startrowb];
             
        }                   
      }           
     }
     
      // System.out.println("myCRS_C' ="+ Arrays.toString(valueC));
     double endTime2  = System.currentTimeMillis();
     double totalTime2 = (endTime2 - startTime2);
      return totalTime2; 
}  

// Method to get the index of C 
public static int[][] indexCinJSA( int [][] indexA, int [][] indexB, int n){
    int[][] indexC = new int[n][1];
    int[] temp = new int[n];
    int[] tempIndex = new int[n]; 
     int nonzero=0;
    int len = -1;

for(int i = 0;i<temp.length;i++){
  temp[i]=-1;
}
     for(int i = 0;i<indexA.length;i++){
         int[] aindex = indexA[i];
      for (int j=0; j<aindex.length; j++){
           int index = aindex[j];             
           int[] bindex = indexB[index];
        for(int k = 0;k<bindex.length;k++){
            int jcol = bindex[k];
             int jpos = temp[jcol];
          if(jpos == -1){
            len++;
            nonzero++;
            tempIndex[len] = jcol;
            temp[jcol] = len;  
          }
          else {
          }
        }
      }  
 int[] cindex = new int[len+1]; 
 System.arraycopy(tempIndex, 0, cindex, 0, len+1);
  indexC[i]=cindex;
for(int ii = 0;ii<len+1;ii++){
  temp[tempIndex[ii]]=-1;
}
  len = -1;
}
// System.out.print("Index C  in JSA");
// System.out.println(Arrays.deepToString(indexC));
return indexC;
}

// Method 4: Apply C= C+AB in JSA ------- Gunderson approach   444444444444444444444444444444444444444444444444444444444444444444444444
 public static double ABinJSA_Gunderson(double [][] valueA, int [][] indexA, double [][] valueB, int [][] indexB, int n){
   double[][] valueC = new double[n][1];
    int[][] indexC = new int[n][1];
    int[] temp = new int[n];
    double[] tempValue = new double[n];
    int[] tempIndex = new int[n]; 
     int nonzero=0;
    int len = -1;

for(int i = 0;i<temp.length;i++){
  temp[i]=-1;
}
 double startTime1 = System.currentTimeMillis();
     for(int i = 0;i<valueA.length;i++){
          double[] avalue = valueA[i];
         int[] aindex = indexA[i];
      for (int j=0; j<avalue.length; j++){
           double scalar= avalue[j]; 
           int index = aindex[j];
           double[] bvalue = valueB[index];               
           int[] bindex = indexB[index];
        for(int k = 0;k<bvalue.length;k++){
            int jcol = bindex[k];
             int jpos = temp[jcol];
          if(jpos == -1){
            len++;
            nonzero++;
            tempIndex[len] = jcol;
            temp[jcol] = len;  
            tempValue[len]=scalar*bvalue[k];    
          }
          else {
              tempValue[jpos]+=scalar*bvalue[k]; 
          }
        }
      }
 double [] cvalue=new double[len+1];   
 int[] cindex = new int[len+1];
 System.arraycopy(tempValue, 0, cvalue, 0, len+1);
 System.arraycopy(tempIndex, 0, cindex, 0, len+1);
  valueC[i]=cvalue;
  indexC[i]=cindex;
for(int ii = 0;ii<len+1;ii++){
  temp[tempIndex[ii]]=-1;
}
  len = -1;
}
    
     // System.out.println("Gundereson_JSA_C ="+ Arrays.deepToString(valueC));
     double endTime1   = System.currentTimeMillis();
      double totalTime1= endTime1 - startTime1;
      return totalTime1;
}

// Method 5: Apply C= C+AB in JSA --------Myapproach  5555555555555555555555555555555555555555555555555555555555555555555555555555555555555
public static double ABinJSA_myApproach(double[][] valA, int [][] colindA, double[][] valB, int [][] colindB, int [][] colindC, int n){
  double[][] valueC= new double[n][1];

  double startTime3 = System.currentTimeMillis();
 for(int i =0;i<n;i++){
   double [] rowa = valA[i];
   int [] indexa=colindA[i];
   int [] indexc=colindC[i];
   double [] rowc=new double[indexc.length];

     for( int aj=0; aj<rowa.length; aj++ ){
         double scalar = rowa[aj];
         int inda= indexa[aj];
         double[] rowb=valB[inda];
         int []   indexb=colindB[inda];
        for (int bj=0; bj<rowb.length; bj++){
               int cj= indexb[bj]-indexc[0];  // indexc=cj=indexb-indexc[0];
             rowc[cj]+= scalar * rowb[bj];
         }              
      } 
     valueC[i]=rowc;                     
  }   
     
     // System.out.println("MyJSA_C="+Arrays.deepToString(valueC));
     double endTime3 = System.currentTimeMillis();
     double totalTime3 = (endTime3 - startTime3);
      return totalTime3; 
} 

// Method 6: Apply C'= C'+ A'B in JSA-----My Approach  6666666666666666666666666666666666666666666666666666666666666666666666666666666666666
public static double ATBinJSA_myApproach(double[][] valA, int [][] colindA, double[][] valB, int [][] colindB, int [][] colindC, int n){
  double[][] valueC= new double[n][1];
   for (int j=0; j<n; j++){
    valueC[j]=new double[colindC[j].length];
   }
  
  double startTime4 = System.currentTimeMillis();
 for(int i =0;i<n;i++){
   double [] rowa = valA[i];
      int [] indexa=colindA[i];
   double [] rowb=valB[i];   
      int [] indexb=colindB[i];

     for( int aj=0; aj<rowa.length; aj++ ){
         double scalar = rowa[aj];
         int inda= indexa[aj];
         int [] indexc=colindC[inda];
        for (int bj=0; bj<rowb.length; bj++){
            int cj= indexb[bj]-indexc[0];  // indexc=cj=indexb[bj]-indexc[0];
            valueC[inda][cj]+= scalar * rowb[bj];                
         } 
        }               
      } 
                 
     // System.out.println("myJSA_CT"+ Arrays.deepToString(valueC));
     double endTime4 = System.currentTimeMillis();
     double totalTime4 = (endTime4 - startTime4);
      return totalTime4; 
}  

//Function to cut the elements of diagonals
 public static double [] getDiagArrayFunction(double[] diagStoreArray, int L, int U, int k, int n) {
   // k=diag[2];
  double[] getarray=new double[n-Math.abs(k)];
   if ( k >= 0 && k<=U){ 
        int startindx=L*(2*n-L-1)/2 + k*(2*n-k-1)/2+k;
        int endindx=startindx + n-k-1;
        int index=0;
      for (int i=startindx; i<=endindx;i++){                   
          getarray[index]=diagStoreArray[i];
          index++;
      }
                 
    } 

   else if ( k <0 && k>=-L){ 
         int abs_k=Math.abs(k); // take the absolute value of k
         int startindx=L*(2*n-L-1)/2 + k*(2*n+k-1)/2;
         int endindx=startindx + n-abs_k-1;
         int index=0;
       for (int i=startindx; i<=endindx;i++){                   
            getarray[index]=diagStoreArray[i];
            index++;           
        }                
    }
  return getarray;        
} 
//Function to cut the elements from getDiagArray
public static double [] cutElementFunction(double[] getDiagArray,int cutBegin, int cutEnd) {
     int size = getDiagArray.length;
     double[] outputArray=new double [size-cutBegin-cutEnd];  //
   for (int i=0; i<size-cutBegin-cutEnd;i++){  
         outputArray[i]=getDiagArray[i+cutBegin];  
       }
     return outputArray;
  }
  
// Method 7: Apply C= C+AB in Diag   ------Theorem in Niel k paper  777777777777777777777777777777777777777777777777777777777777777777777777777777
public static double AB_multiplydiag(double[] diagStoreArrayA, double[] diagStoreArrayB,int LA, int UA, int LB, int UB,int LC, int UC,int n, int sum){
  double [] C = new double[sum]; 
//getting main and super-diagonals of C
  double totaltime = 0.0;
for (int k=0; k<=UC;k++){
 int inx_C = LC*(2*n-LC-1)/2 + k*(2*n-k-1)/2 + k ;  
 int endinx_C = inx_C + n-k-1;
      for (int i=k+1; i<=Math.min(UB, k+LA);i++){
           double[] diag_a1=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA, k-i,n),0,k);
           double[] diag_b1=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB, i,n),0,0);
           int size1=diag_b1.length;

           double start1   = System.currentTimeMillis();
           for (int j=0; j<=size1-1;j++){
              C[inx_C+i-k+j]= C[inx_C+i-k+j]+diag_a1[j]*diag_b1[j];
            }
           double end1 = (System.currentTimeMillis() - start1);
           totaltime = totaltime + end1;

          double [] diag_a2=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,i,n),0,0);
          double [] diag_b2=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,k-i,n),k,0);
          int size2=diag_a2.length;
           
           double start2 = System.currentTimeMillis();
           for (int j=0; j<=size2-1;j++){   
              C[inx_C+j]= C[inx_C+j]+diag_a2[j]*diag_b2[j]; 
            }  
            double end2 = (System.currentTimeMillis() - start2);
            totaltime = totaltime + end2;             
      }
 
      for (int i=Math.max(0, k-UB); i<=Math.min(k, UA); i++){
         double [] diag_a3=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,i,n),0,k-i);
         double [] diag_b3=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,k-i,n),i,0);
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
 
 for (int k=1; k<=LC;k++){
    // abs_k=Math.abs(k);
   int inx_C = LC*(2*n-LC-1)/2 - k*(2*n-k-1)/2;;  
   int endinx_C = inx_C + n-k-1;
    for (int i=k+1; i<=Math.min(LA,k+UB);i++){
          double [] diag_a1=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,-i,n),0,0);
          double [] diag_b1=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,i-k,n),0,k);
          int size1=diag_b1.length;
         
          double start4 = System.currentTimeMillis();
         for (int j=0; j<=size1-1;j++){
            C[inx_C+i-k+j]= C[inx_C+i-k+j]+diag_a1[j]*diag_b1[j];
         }
         double end4 = (System.currentTimeMillis() - start4);
         totaltime = totaltime + end4;

         double [] diag_a2=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,i-k,n),k,0);
         double [] diag_b2=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,-i,n),0,0);
         int size2=diag_a2.length;
  
          double start5 = System.currentTimeMillis();
         for (int j=0; j<=size2-1;j++){   
          C[inx_C+j]= C[inx_C+j]+diag_a2[j]*diag_b2[j];  
          }
          double end5 = (System.currentTimeMillis() - start5);
          totaltime = totaltime + end5;
    }
      for (int i=Math.max(0,k-LB); i<=Math.min(k,LA); i++){
        double [] diag_a3=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,-i,n),k-i ,0);
        double [] diag_b3=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,i-k,n),0,i);
        int size3=diag_a3.length;

        double start6 = System.currentTimeMillis();
        for (int j=0; j<=size3-1;j++){
          C[inx_C+j]= C[inx_C+j]+diag_a3[j]*diag_b3[j];
         }
          double end6 = (System.currentTimeMillis() - start6);
          totaltime = totaltime + end6;
      }       
 }
  // System.out.println("diag_C="+Arrays.toString(C)) ;
 return totaltime;
}

// Method 8: Apply C'= C'+ A'B in Diag ----Theorem in Niel k paper  888888888888888888888888888888888888888888888888888888888888888888888888888888
public static double ATB_multiplydiag(double[] diagStoreArrayA, double[] diagStoreArrayB,int LA, int UA, int LB, int UB,int LC, int UC,int n, int sum){
  double [] C = new double[sum]; 
//getting main and super-diagonals of C
  double totaltime = 0.0;
for (int k=0; k<=UC;k++){
 int inx_C = LC*(2*n-LC-1)/2 + k*(2*n-k-1)/2 + k ;  
 int endinx_C = inx_C + n-k-1;
      for (int i=k+1; i<=Math.min(UB, k+LA);i++){
           double[] diag_a1=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA, i-k,n),0,k);
           double[] diag_b1=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB, i,n),0,0);
           int size1=diag_b1.length;
           double start1   = System.currentTimeMillis();

           for (int j=0; j<=size1-1;j++){
              C[inx_C+i-k+j]= C[inx_C+i-k+j]+diag_a1[j]*diag_b1[j];
            }
           double end1 = (System.currentTimeMillis() - start1);
           totaltime = totaltime + end1;

          double [] diag_a2=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,-i,n),0,0);
          double [] diag_b2=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,k-i,n),k,0);
          int size2=diag_a2.length;
           
           double start2 = System.currentTimeMillis();
           for (int j=0; j<=size2-1;j++){   
              C[inx_C+j]= C[inx_C+j]+diag_a2[j]*diag_b2[j]; 
            }  
            double end2 = (System.currentTimeMillis() - start2);
            totaltime = totaltime + end2;             
      }
 
      for (int i=Math.max(0, k-UB); i<=Math.min(k, UA); i++){
         double [] diag_a3=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,-i,n),0,k-i);
         double [] diag_b3=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,k-i,n),i,0);
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
 
 for (int k=1; k<=LC;k++){
    // abs_k=Math.abs(k);
   int inx_C = LC*(2*n-LC-1)/2 - k*(2*n-k-1)/2;;  
   int endinx_C = inx_C + n-k-1;
    for (int i=k+1; i<=Math.min(LA,k+UB);i++){
          double [] diag_a1=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,i,n),0,0);
          double [] diag_b1=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,i-k,n),0,k);
          int size1=diag_b1.length;
          double start4 = System.currentTimeMillis();
         for (int j=0; j<=size1-1;j++){
            C[inx_C+i-k+j]= C[inx_C+i-k+j]+diag_a1[j]*diag_b1[j];
         }
         double end4 = (System.currentTimeMillis() - start4);
         totaltime = totaltime + end4;

         double [] diag_a2=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,k-i,n),k,0);
         double [] diag_b2=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,-i,n),0,0);
         int size2=diag_a2.length;
  
          double start5 = System.currentTimeMillis();
         for (int j=0; j<=size2-1;j++){   
          C[inx_C+j]= C[inx_C+j]+diag_a2[j]*diag_b2[j];  
          }
          double end5 = (System.currentTimeMillis() - start5);
          totaltime = totaltime + end5;
    }
      for (int i=Math.max(0,k-LB); i<=Math.min(k,LA); i++){
        double [] diag_a3=cutElementFunction(getDiagArrayFunction(diagStoreArrayA,LA, UA,i,n),k-i ,0);
        double [] diag_b3=cutElementFunction(getDiagArrayFunction(diagStoreArrayB,LB, UB,i-k,n),0,i);
        int size3=diag_a3.length;

        double start6 = System.currentTimeMillis();
        for (int j=0; j<=size3-1;j++){
          C[inx_C+j]= C[inx_C+j]+diag_a3[j]*diag_b3[j];
         }
          double end6 = (System.currentTimeMillis() - start6);
          totaltime = totaltime + end6;
      }       
 }
  // System.out.println("diag_CT="+ Arrays.toString(C)) ;
 return totaltime;
}
private static DecimalFormat df2= new DecimalFormat(".##");
//  Main function starts here ***********************************************************************************
// public static void main(String[] args){ 
 public static void main(String[] args) throws Throwable {
     PrintStream out = new PrintStream(new FileOutputStream("CRS_JSA_Diag_Jul30.xlsx"));
     System.setOut(out);

System.out.println("n" + "," + "#diags"+ "," + "%of nnz" + "," + "AB_CRS_Geir" + "," + "AB_CRS_My"+ ","+ "AB_JSA_Geir" + ","+ "AB_JSA_My"
  + "," + "AB_Diag" + ","+"A'B_CRS_My" +","+ "A'B_JSA_My" + "," + "A'B_Diag");
 for ( int n =10000; n <=15000; n+=1000) {
 int t=(2*n-1)/1000;

        if ((t % 2) == 0){
          t=t+1;
        }
        else{
         t=t;
        }
        int [] diag= new int[t]; 
         diag[(t-1)/2]=0;
          for (int i=0; i< (t-1)/2; i++){
            diag[i]=i-(t-1)/2;
          }   
          for (int i=(t-1)/2+1;i<t; i++){
            diag[i]=i-(t-1)/2;
          }
          
     int bandwidth =diag.length;
    //find number of nonzero elements
    int numnz=0;
   for (int i=0; i<diag.length; i++){
     numnz+=n- Math.abs(diag[i]);
   }

     int nnz=numnz;  
     double a=nnz;
     double b=(long)n*(long)n;
     double percent=(a/b)*100;

     int LA=(t-1)/2 ;
     int UA=(t-1)/2;
     int LB=(t-1)/2;
     int UB=(t-1)/2;
     int LC, UC;
    LC=Math.min(n-1,LA+LB );
    UC=Math.min(n-1,UA+UB );
    int sum=0;
    for (int i=-LC; i<=UC; i++){
     sum+=n-Math.abs(i);
    }
// System.out.println(Arrays.toString(diag));
    
 //Output in diag
  Object[] diagObjectsA=RandomArray(n,diag);
  double[] diagStoreArrayA=(double[]) diagObjectsA[0];
     int[] pointerA_Diag=(int[]) diagObjectsA[1];
     int[] colindA_Diag=(int[]) diagObjectsA[2];
     int[] rowindA_Diag=(int[]) diagObjectsA[3];

  Object[] diagObjectsB=RandomArray(n,diag);
  double[] diagStoreArrayB=(double[]) diagObjectsB[0];
     int[] pointerB_Diag=(int[]) diagObjectsB[1];
     int[] colindB_Diag=(int[]) diagObjectsB[2];
     int[] rowindB_Diag=(int[]) diagObjectsB[3];

// Output in CRS 
 Object[] CRSObjectsA=ConvertDiagToCRS_JSA(n, diagStoreArrayA, colindA_Diag, diag);
   double[] valA_CRS=(double[]) CRSObjectsA[0];
      int[] rowptrA_CRS=(int[]) CRSObjectsA[1];
      int[] colindA_CRS=(int[]) CRSObjectsA[2];

  Object[] CRSObjectsB=ConvertDiagToCRS_JSA(n, diagStoreArrayB, colindB_Diag, diag);
   double[] valB_CRS=(double[]) CRSObjectsB[0];
      int[] rowptrB_CRS=(int[]) CRSObjectsB[1];
      int[] colindB_CRS=(int[]) CRSObjectsB[2];

//CRS ---C
 Object[] indexPointerC=Cindexpointer(valA_CRS, colindA_CRS, rowptrA_CRS, valB_CRS, colindB_CRS, rowptrB_CRS, n);  
 int[] indexC_CRS =  (int[])indexPointerC[0];
 int[] pointerC=(int[])indexPointerC[1];



//output in JSA 
  double[][] valA_JSA=(double[][]) CRSObjectsA[3];
     int[][] colindA_JSA=(int[][]) CRSObjectsA[4];
     double[][] valB_JSA=(double[][]) CRSObjectsB[3];
     int[][] colindB_JSA=(int[][]) CRSObjectsB[4];
//JSA ---C
int [][] indexC_JSA=indexCinJSA( colindA_JSA, colindB_JSA, n);


 // Timing 
  double t1_CRS= ABinCRS_Gunderson(valA_CRS, colindA_CRS, rowptrA_CRS, valB_CRS, colindB_CRS, rowptrB_CRS, indexC_CRS, pointerC, n); 
  double t2_CRS=ABinCRS_myApproach(valA_CRS, colindA_CRS, rowptrA_CRS, valB_CRS, colindB_CRS, rowptrB_CRS, indexC_CRS, pointerC, n);
  double t3_CRS=ATBinCRS_myApproach(valA_CRS, colindA_CRS, rowptrA_CRS, valB_CRS, colindB_CRS, rowptrB_CRS, indexC_CRS, pointerC, n);
  double t1_diag=AB_multiplydiag( diagStoreArrayA,diagStoreArrayB,LA,UA, LB, UB, LC, UC,n, sum);

  double  t1_JSA=ABinJSA_Gunderson(valA_JSA, colindA_JSA,valB_JSA,colindB_JSA, n);
  double t2_JSA= ABinJSA_myApproach(valA_JSA, colindA_JSA,valB_JSA,colindB_JSA, indexC_JSA, n);
  double t3_JSA= ATBinJSA_myApproach(valA_JSA, colindA_JSA,valB_JSA,colindB_JSA, indexC_JSA, n);     
  double t2_diag=ATB_multiplydiag( diagStoreArrayA,diagStoreArrayB,LA,UA, LB, UB, LC, UC,n, sum);


System.out.println(n +","+ bandwidth+ "," +percent+ ", "+ t1_CRS + ","+ t2_CRS  +","+  t1_JSA + "," + t2_JSA + ","+ t1_diag+ ","+ t3_CRS+","+ t3_JSA + "," +t2_diag);

  }
  }
}



