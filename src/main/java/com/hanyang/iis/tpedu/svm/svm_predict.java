package com.hanyang.iis.tpedu.svm;

import libsvm.*;

import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;

class svm_predict {
	public static int Grade = 5;
	public static String setname = "0529_sum_"+Grade;
	
	private static svm_print_interface svm_print_null = new svm_print_interface()
	{
		public void print(String s) {}
	};

	private static svm_print_interface svm_print_stdout = new svm_print_interface()
	{
		public void print(String s)
		{
			System.out.print(s);
		}
	};

	private static svm_print_interface svm_print_string = svm_print_stdout;

	static void info(String s) 
	{
		svm_print_string.print(s);
	}

	private static double atof(String s)
	{
		return Double.valueOf(s).doubleValue();
	}

	private static int atoi(String s)
	{
		return Integer.parseInt(s);
	}

	private static void predict(CSVReader input, DataOutputStream output, svm_model model, int predict_probability) throws IOException
	{
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		//QWKAPPA
		
		int[][] QWkappa = new int[Grade][Grade];
		
		for(int i = 0;i<Grade;i++){
			for(int j =0;j<Grade;j++){
				QWkappa[i][j] = 0;
			}
		}
		

		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);
		double[] prob_estimates=null;

		if(predict_probability == 1)
		{
			if(svm_type == svm_parameter.EPSILON_SVR ||
			   svm_type == svm_parameter.NU_SVR)
			{
				svm_predict.info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
			}
			else
			{
				int[] labels=new int[nr_class];
				svm.svm_get_labels(model,labels);
				prob_estimates = new double[nr_class];
				output.writeBytes("labels");
				for(int j=0;j<nr_class;j++)
					output.writeBytes(" "+labels[j]);
				output.writeBytes("\n");
			}
		}
		
		//precision
		double vList[] = new double[Grade];
		double targetList[] = new double[Grade];
		
		
		int num_param = 6;
		while(true)
		{
			//test input from
		
			String[] line = input.readNext();
			if(line == null) break;

			double target = atof(line[0]);
			
			svm_node[] x = new svm_node[num_param];
			for(int j=0;j<num_param;j++)
			{
				x[j] = new svm_node();
				x[j].index = j+1;
				x[j].value = atof(line[j+1]);
			}
			//to

			
			//result output
			double v;
			if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
			{
				v = svm.svm_predict_probability(model,x,prob_estimates);
				output.writeBytes(v+" ");
				for(int j=0;j<nr_class;j++)
					output.writeBytes(prob_estimates[j]+" ");
				output.writeBytes("\n");
			}
			else
			{
				v = svm.svm_predict(model,x);
				output.writeBytes(v+"\n");
			}
			//to
			
			
			
			

			if(v == target){
				++correct;
				targetList[(int) v] ++;
			}
			vList[(int) v] ++;
			
			error += (v-target)*(v-target);
			sumv += v;
			sumy += target;
			sumvv += v*v;
			sumyy += target*target;
			sumvy += v*target;
			++total;
			QWkappa[(int) target][(int) v]++;
		}
		if(svm_type == svm_parameter.EPSILON_SVR ||
		   svm_type == svm_parameter.NU_SVR)
		{
			svm_predict.info("Mean squared error = "+error/total+" (regression)\n");
			svm_predict.info("Squared correlation coefficient = "+
				 ((total*sumvy-sumv*sumy)*(total*sumvy-sumv*sumy))/
				 ((total*sumvv-sumv*sumv)*(total*sumyy-sumy*sumy))+
				 " (regression)\n");
		}
		else{
			svm_predict.info("Accuracy = "+(double)correct/total*100+
				 "% ("+correct+"/"+total+") (classification)\n");
			//precision print
			
			
			double pre = 0;
			
			for(int i = 0;i<Grade;i++){
				if(vList[i] != 0){

					pre += targetList[i]/vList[i];	
				}
			}
			
			System.out.println("pre : "+pre/Grade);
			
			
			
		}
		
		System.out.println("QWK");
		for(int i =0;i<Grade;i++){
			for(int j =0;j<Grade;j++){
				System.out.print(QWkappa[i][j] + " ");
			}
			System.out.println();
		}
		
	}

	private static void exit_with_help()
	{
		System.err.print("usage: svm_predict [options] test_file model_file output_file\n"
		+"options:\n"
		+"-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n"
		+"-q : quiet mode (no outputs)\n");
		System.exit(1);
	}

	public static void main(String argv[]) throws IOException
	{
		int set = 6;

        
		for(int s = 3;s<=set;s++){
			System.out.println(s);

			int i, predict_probability=0;
	        svm_print_string = svm_print_stdout;
	        	
	        String Option = "";
	        
	        String evalfile =   "C:/Users/hyukLab/Desktop/공부/꽁혁식/testdata/essay/"+setname+"/TPEDU_eval_set"+s+".csv";
	        String modelfile = "C:/Users/hyukLab/Desktop/공부/꽁혁식/testdata/essay/"+setname+"/TPEDU_train_set"+s+".model";
	        String outfile =   "C:/Users/hyukLab/Desktop/공부/꽁혁식/testdata/essay/"+setname+"/TPEDU_train_set"+s+".txt";
	        
	        modelfile = "C:/Users/hyukLab/Desktop/공부/꽁혁식/testdata/0615_essay_Normalized/TPEDU_essay"+s+"_withoutPattern.model";
	        evalfile = "C:/Users/hyukLab/Desktop/공부/꽁혁식/testdata/0615_essay_Normalized/TPEDU_essay"+s+"_test.csv";
	        outfile = "C:/Users/hyukLab/Desktop/공부/꽁혁식/testdata/0615_essay_Normalized/TPEDU_essay"+s+"_withoutPattern.txt";
	        
	        System.out.println(modelfile);
	        System.out.println(evalfile);
	        
	        
	        StringTokenizer stkn = new StringTokenizer(Option);
	        
	        String tmpargv[] = new String[stkn.countTokens()+3];
	        
	        int cnt = 0;
	        
	        while(stkn.hasMoreTokens()){        	
	        	String curtkn = stkn.nextToken();
	        	tmpargv[cnt] = new String();
	        	tmpargv[cnt] = curtkn;
	        	cnt ++;
	        }
	        
	        tmpargv[cnt] = new String();
	        tmpargv[cnt] = evalfile;
	        tmpargv[cnt+1] = new String();
	        tmpargv[cnt+1] = modelfile;
	        tmpargv[cnt+2] = new String();
	        tmpargv[cnt+2] = outfile;
	        
	        argv = tmpargv;
			// parse options
			for(i=0;i<argv.length;i++)
			{
				if(argv[i].charAt(0) != '-') break;
				++i;
				switch(argv[i-1].charAt(1))
				{
					case 'b':
						predict_probability = atoi(argv[i]);
						break;
					case 'q':
						svm_print_string = svm_print_null;
						i--;
						break;
					default:
						System.err.print("Unknown option: " + argv[i-1] + "\n");
						exit_with_help();
				}
			}
			if(i>=argv.length-2)
				exit_with_help();
			try 
			{
				CSVReader input = new CSVReader(new FileReader(argv[i]));
				DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(argv[i+2])));
				svm_model model = svm.svm_load_model(argv[i+1]);
				if (model == null)
				{
					System.err.print("can't open model file "+argv[i+1]+"\n");
					System.exit(1);
				}
				if(predict_probability == 1)
				{
					if(svm.svm_check_probability_model(model)==0)
					{
						System.err.print("Model does not support probabiliy estimates\n");
						System.exit(1);
					}
				}
				else
				{
					if(svm.svm_check_probability_model(model)!=0)
					{
						svm_predict.info("Model supports probability estimates, but disabled in prediction.\n");
					}
				}
				predict(input,output,model,predict_probability);
				input.close();
				output.close();
			} 
			catch(FileNotFoundException e) 
			{
				exit_with_help();
			}
			catch(ArrayIndexOutOfBoundsException e) 
			{
				exit_with_help();
			}
		}
		
	}
}
