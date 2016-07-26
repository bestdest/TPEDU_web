package com.hanyang.iis.tpedu.svm;

import libsvm.*;

import java.io.*;
import java.util.*;

import com.hanyang.iis.tpedu.dto.Sentence;
import com.opencsv.CSVReader;

public class svm_predict_sentence {

	private static svm_print_interface svm_print_null = new svm_print_interface() {
		public void print(String s) {
		}
	};

	private static svm_print_interface svm_print_stdout = new svm_print_interface() {
		public void print(String s) {
			System.out.print(s);
		}
	};

	private static svm_print_interface svm_print_string = svm_print_stdout;

	static void info(String s) {
		svm_print_string.print(s);
	}

	private static double atof(String s) {
		return Double.valueOf(s).doubleValue();
	}

	private static int atoi(String s) {
		return Integer.parseInt(s);
	}

	private static int predict(svm_model model, int predict_probability, Sentence sentence) throws IOException {
		int svm_type = svm.svm_get_svm_type(model);
		int nr_class = svm.svm_get_nr_class(model);
		double[] prob_estimates = null;

		if (predict_probability == 1) {
			if (svm_type == svm_parameter.EPSILON_SVR || svm_type == svm_parameter.NU_SVR) {
				svm_predict_sentence.info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="
						+ svm.svm_get_svr_probability(model) + "\n");
			} else {
				int[] labels = new int[nr_class];
				svm.svm_get_labels(model, labels);
				prob_estimates = new double[nr_class];
			}
		}

		int num_param = 7;

		/*
		 * here here here here here here here here here here here here here here
		 * here here here here here here here here here here here here here here
		 * here here here here here here here here here here here here here here
		 * here here here here here here here here here here here here here here
		 */

		svm_node[] x = new svm_node[num_param];

		/*
		 * for(int j=0;j<num_param;j++) { x[j] = new svm_node(); x[j].index =
		 * j+1; x[j].value = atof(line[j+1]); }
		 */
		// to

		// result output
		// v = result

		// x[].index -> auto increase
		// x[].value -> feature value

		x[0] = new svm_node();
		x[0].value = sentence.getStruct_type();
		x[0].index = 1;

		x[1] = new svm_node();
		x[1].value = sentence.getCnt_advp();
		x[1].index = 2;

		x[2] = new svm_node();
		x[2].value = sentence.getCnt_adjp();
		x[2].index = 3;

		x[3] = new svm_node();
		x[3].value = sentence.getLength();
		x[3].index = 4;

		x[4] = new svm_node();
		x[4].value = sentence.getWord();
		x[4].index = 5;

		x[5] = new svm_node();
		x[5].value = sentence.getVoca_score();
		x[5].index = 6;

		x[6] = new svm_node();
		x[6].value = sentence.getPattern_score();
		x[6].index = 7;

		double v;
		if (predict_probability == 1 && (svm_type == svm_parameter.C_SVC || svm_type == svm_parameter.NU_SVC)) {
			v = svm.svm_predict_probability(model, x, prob_estimates);
		} else {
			v = svm.svm_predict(model, x);
		}
		// to

		System.out.println(v);
		int result = (int)v;
		return result;

	}

	private static void exit_with_help() {
		System.err.print("usage: svm_predict [options] test_file model_file output_file\n" + "options:\n"
				+ "-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n" + "-q : quiet mode (no outputs)\n");
		System.exit(1);
	}

	/*public static void main(String argv[]) throws IOException {
		int i, predict_probability = 0;
		svm_print_string = svm_print_stdout;
		String Option = "";
		String modelfile = "D:\\Temp\\TPEDU_train.model";
		StringTokenizer stkn = new StringTokenizer(Option);
		String tmpargv[] = new String[stkn.countTokens() + 3];
		int cnt = 0;

		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();
			tmpargv[cnt] = new String();
			tmpargv[cnt] = curtkn;
			cnt++;
		}

		tmpargv[cnt] = new String();
		tmpargv[cnt] = "asdf.txt";
		tmpargv[cnt + 1] = new String();
		tmpargv[cnt + 1] = modelfile;
		tmpargv[cnt + 2] = new String();
		tmpargv[cnt + 2] = "asdf.txt";

		argv = tmpargv;
		// parse options
		for (i = 0; i < argv.length; i++) {
			if (argv[i].charAt(0) != '-')
				break;
			++i;
			switch (argv[i - 1].charAt(1)) {
			case 'b':
				predict_probability = atoi(argv[i]);
				break;
			case 'q':
				svm_print_string = svm_print_null;
				i--;
				break;
			default:
				System.err.print("Unknown option: " + argv[i - 1] + "\n");
				exit_with_help();
			}
		}
		if (i >= argv.length - 2) {
			exit_with_help();
		}

		try {
			svm_model model = svm.svm_load_model(argv[i + 1]);
			if (model == null) {
				System.err.print("can't open model file " + argv[i + 1] + "\n");
				System.exit(1);
			}
			if (predict_probability == 1) {
				if (svm.svm_check_probability_model(model) == 0) {
					System.err.print("Model does not support probabiliy estimates\n");
					System.exit(1);
				}
			} else {
				if (svm.svm_check_probability_model(model) != 0) {
					svm_predict_sentence.info("Model supports probability estimates, but disabled in prediction.\n");
				}
			}

			
			 *//** hereherehereherehereherehereherehereherehere *//*
			 
			 
			predict(model, predict_probability);
		} catch (FileNotFoundException e) {
			exit_with_help();
		} catch (ArrayIndexOutOfBoundsException e) {
			exit_with_help();
		}

	}*/
	
	
	public int getSVMResult(Sentence sentence) throws IOException {
		int i, predict_probability = 0;
		int cnt = 0;
		int result = 0;
		
		String Option = "";
		String modelfile = "D:\\Temp\\TPEDU_train.model";
		StringTokenizer stkn = new StringTokenizer(Option);
		String tmpargv[] = new String[stkn.countTokens() + 3];
		String model_arr[];
		svm_print_string = svm_print_stdout;

		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();
			tmpargv[cnt] = new String();
			tmpargv[cnt] = curtkn;
			cnt++;
		}

		tmpargv[cnt] = new String();
		tmpargv[cnt] = "asdf.txt";
		tmpargv[cnt + 1] = new String();
		tmpargv[cnt + 1] = modelfile;
		tmpargv[cnt + 2] = new String();
		tmpargv[cnt + 2] = "asdf.txt";

		model_arr = tmpargv;
		// parse options
		for (i = 0; i < model_arr.length; i++) {
			if (model_arr[i].charAt(0) != '-')
				break;
			++i;
			switch (model_arr[i - 1].charAt(1)) {
			case 'b':
				predict_probability = atoi(model_arr[i]);
				break;
			case 'q':
				svm_print_string = svm_print_null;
				i--;
				break;
			default:
				System.err.print("Unknown option: " + model_arr[i - 1] + "\n");
				exit_with_help();
			}
		}
		if (i >= model_arr.length - 2) {
			exit_with_help();
		}

		try {
			svm_model model = svm.svm_load_model(model_arr[i + 1]);
			if (model == null) {
				System.err.print("can't open model file " + model_arr[i + 1] + "\n");
				System.exit(1);
			}
			if (predict_probability == 1) {
				if (svm.svm_check_probability_model(model) == 0) {
					System.err.print("Model does not support probabiliy estimates\n");
					System.exit(1);
				}
			} else {
				if (svm.svm_check_probability_model(model) != 0) {
					svm_predict_sentence.info("Model supports probability estimates, but disabled in prediction.\n");
				}
			}
			
			result = predict(model, predict_probability, sentence);
			
		} catch (FileNotFoundException e) {
			exit_with_help();
		} catch (ArrayIndexOutOfBoundsException e) {
			exit_with_help();
		}
		
		
		return result;
	}
}
