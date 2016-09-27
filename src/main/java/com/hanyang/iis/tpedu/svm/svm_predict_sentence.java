package com.hanyang.iis.tpedu.svm;

import libsvm.*;

import java.io.*;
import java.util.*;

import com.hanyang.iis.tpedu.dto.Sentence;

public class svm_predict_sentence {
	
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

	private static int predict(svm_model model, int predict_probability, Sentence sentence, int is_paragraph) throws IOException
	{
		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);
		double[] prob_estimates=null;

		if(predict_probability == 1)
		{
			if(svm_type == svm_parameter.EPSILON_SVR ||
			   svm_type == svm_parameter.NU_SVR)
			{
				svm_predict_sentence.info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
			}
			else
			{
				int[] labels=new int[nr_class];
				svm.svm_get_labels(model,labels);
				prob_estimates = new double[nr_class];
			}
		}
		
		int num_param = 24;
		
		if(is_paragraph == 0){		//문장
			num_param = 20;
		}
			/*
			 * here here here here here here here here here here here here here here 
			 * here here here here here here here here here here here here here here 
			 * here here here here here here here here here here here here here here 
			 * here here here here here here here here here here here here here here		
			 * */
		
		svm_node[] x = new svm_node[num_param];
		
		
		
		/*for(int j=0;j<num_param;j++)
		{
			x[j] = new svm_node();
			x[j].index = j+1;
			x[j].value = atof(line[j+1]);
		}*/
		//to

		
		//result output
		//v = result
		

		//x[].index -> auto increase
		//x[].value -> feature value
		int idx = 0;
		
		
		if(is_paragraph == 1){
			x[idx] = new svm_node();
			x[idx].value = sentence.getNum_sen();
			x[idx].index = ++idx;
		}
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getStruct_type();
		x[idx].index = ++idx;

		x[idx] = new svm_node();
		x[idx].value = sentence.getCnt_advp();
		x[idx].index = ++idx;

		x[idx] = new svm_node();
		x[idx].value = sentence.getCnt_adjp();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getCnt_modifier();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getLength();
		x[idx].index = ++idx;

		x[idx] = new svm_node();
		x[idx].value = sentence.getWord();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getAvg_char();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getAvg_syllables();
		x[idx].index = ++idx;

		x[idx] = new svm_node();
		x[idx].value = sentence.getVoca_score();
		x[idx].index = ++idx;

		x[idx] = new svm_node();
		x[idx].value = sentence.getPattern_score();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getRatio_awl();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getVar_modifier();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getVar_adv();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getVar_adj();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getCnt_cc();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getCnt_sbar();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getCnt_compound();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getCnt_gr();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getAvg_dis_gr();
		x[idx].index = ++idx;
		
		x[idx] = new svm_node();
		x[idx].value = sentence.getMax_dis_gr();
		x[idx].index = ++idx;
		
		
		if(is_paragraph == 1){
			
			x[idx] = new svm_node();
			x[idx].value = sentence.getTtr();
			x[idx].index = ++idx;
			
			x[idx] = new svm_node();
			x[idx].value = sentence.getCli();
			x[idx].index = ++idx;
			
			x[idx] = new svm_node();
			x[idx].value = sentence.getLix();
			x[idx].index = ++idx;
			
		}
		
		
		double v;
		if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
		{
			v = svm.svm_predict_probability(model,x,prob_estimates);
		}
		else
		{
			v = svm.svm_predict(model,x);
		}
		//to
		
		System.out.println(v);
		int result = (int)v;
		return result;
	}

	private static void exit_with_help()
	{
		System.err.print("usage: svm_predict [options] test_file model_file output_file\n"
		+"options:\n"
		+"-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n"
		+"-q : quiet mode (no outputs)\n");
		System.exit(1);
	}

	public int getSVMResult(Sentence sentence, int is_paragraph) throws IOException 
	{
		int i, predict_probability=0;
        	svm_print_string = svm_print_stdout;
        int result = 0;
        String Option = "";
        
        String modelfile = "";
        if(is_paragraph == 0){	//문장
        	modelfile = "D:\\Temp\\SVM\\weebit_sent_train.model"; 
        }else{					//문단
        	modelfile = "D:\\Temp\\SVM\\SVM_Para.model"; 
        }
        		
        
        StringTokenizer stkn = new StringTokenizer(Option);
        String model_arr[];
        String tmpargv[] = new String[stkn.countTokens()+3];
        
        int cnt = 0;
        
        while(stkn.hasMoreTokens()){        	
        	String curtkn = stkn.nextToken();
        	tmpargv[cnt] = new String();
        	tmpargv[cnt] = curtkn;
        	cnt ++;
        }
        
        tmpargv[cnt] = new String();
        tmpargv[cnt] = "asdf.txt";
        tmpargv[cnt+1] = new String();
        tmpargv[cnt+1] = modelfile;
        tmpargv[cnt+2] = new String();
        tmpargv[cnt+2] = "asdf.txt";
        
        	
        model_arr = tmpargv;
		// parse options
		for(i=0;i<model_arr.length;i++)
		{
			if(model_arr[i].charAt(0) != '-') break;
			++i;
			switch(model_arr[i-1].charAt(1))
			{
				case 'b':
					predict_probability = atoi(model_arr[i]);
					break;
				case 'q':
					svm_print_string = svm_print_null;
					i--;
					break;
				default:
					System.err.print("Unknown option: " + model_arr[i-1] + "\n");
					exit_with_help();
			}
		}
		if(i>=model_arr.length-2){
			exit_with_help();
		}
			
		try 
		{
			svm_model model = svm.svm_load_model(model_arr[i+1]);
			if (model == null)
			{
				System.err.print("can't open model file "+model_arr[i+1]+"\n");
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
					svm_predict_sentence.info("Model supports probability estimates, but disabled in prediction.\n");
				}
			}
			
			/*
			 * hereherehereherehereherehereherehereherehere
			 * hereherehereherehereherehereherehereherehere
			 * */
			result = predict(model,predict_probability, sentence, is_paragraph);
		} 
		catch(FileNotFoundException e) 
		{
			exit_with_help();
		}
		catch(ArrayIndexOutOfBoundsException e) 
		{
			exit_with_help();
		}
		
		return result;
		
	}
}
