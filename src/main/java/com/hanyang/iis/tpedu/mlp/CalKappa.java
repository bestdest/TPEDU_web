package com.hanyang.iis.tpedu.mlp;

public class CalKappa {
	public static int category=0;
	public static double CalKappa(double[][] grade, int num){
		//we have the counts stored in an nxn matrix
        //convert "observed matrix" from counts to frequencies
		category = num;
		double sum = sumOfAllElements(grade);
        for (int k = 0; k < grade.length; k++) {
            for (int k2 = 0; k2 < grade.length; k2++) {
                grade[k][k2] = grade[k][k2]/sum;
            }
        }
        
        //echo "observed matrix:".json_encode(matrix)."</p>";
        //now, let's find the "chance matrix"
        double chance[][] = new double[category][category];
        for (int k = 0; k < grade.length; k++) {
            for (int k2 = 0; k2 < grade.length; k2++) {
                chance[k][k2] = array_sum(getRow(grade, k))*array_sum(getColumn(grade, k2));
            }
        }
        //echo "chance matrix:".json_encode(chance)."</p>";
        //we will use a weight matrix
        //there are two main ways to calculate the weight matrix;
        //linear or quadratic
        //we will use the linear one
 
        double weight[][] = new double[category][category];
        double rowCount = grade.length;
        //echo "each dimension:".rowCount;
        //rowCount = columnCount, because the observation matrix is an nxn matrix.
        for(int i=0;i<rowCount;i++){
            for(int j=0;j<rowCount;j++){
//                weight[i][j]=1-(Math.abs(i-j)/(rowCount-1));
                //this would be the quadratic one:
                weight[i][j]=1-Math.pow((Math.abs(i-j)/rowCount),2);
            }
        }
        //echo "weight matrix:".json_encode(weight)."</p>";
        //now, 1)multiply each element in the observed matrix
        //by corresponding weight element and sum it all
        //2)do the same thing with chance matrix
        double sumOfObserved = 0;
        double sumOfChance = 0;
        for(int i=0;i<rowCount;i++){
            for(int j=0;j<rowCount;j++){
                sumOfObserved += grade[i][j]*weight[i][j];
                sumOfChance += chance[i][j]*weight[i][j];
            }
        }
        //the formula for grade is this:
        double gradeValue = (sumOfObserved-sumOfChance)/(1-sumOfChance);
//        System.out.println("here is your grade value:"+gradeValue);
		
		return gradeValue;
	}
	
	static double array_sum(double arr[]){
        double sum=0;
        for (int i=0; i < arr.length; i++)
            sum+=arr[i];
        return sum;
    }
    
	static double sumOfAllElements(double[][] matrix){
        double sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                sum+=matrix[i][j];
            }
        }
        return sum;
    }
    
    static double[] getRow(double matrix[][],int row){
        return matrix[row];
    }
    
    static double[] getColumn(double matrix[][],int column){
        double col[]=new double[category];
        for (int i = 0; i < matrix.length; i++) {
                col[i]=matrix[i][column];
        }
        return col;
    }
    
}
