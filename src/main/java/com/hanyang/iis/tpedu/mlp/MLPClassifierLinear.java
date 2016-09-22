package com.hanyang.iis.tpedu.mlp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.canova.api.records.reader.RecordReader;
import org.canova.api.records.reader.impl.CSVRecordReader;
import org.canova.api.split.FileSplit;
import org.deeplearning4j.datasets.canova.RecordReaderDataSetIterator;
import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class MLPClassifierLinear {
    
	@SuppressWarnings("deprecation")
	public static HashMap<String,Float> feedForward(int numOfGrade, int numInputs, int seed, int batchSize, int nEpochs, int numHiddenNodes, float learningRate) throws IOException, InterruptedException{
		HashMap<String,Float> result = new HashMap<String,Float>();
		int numOutputs = numOfGrade; // 라벨 갯수

        
        //Load the training data:
        RecordReader rr = new CSVRecordReader();
        String trainingFile;
        trainingFile = "src/main/resources/weebit/5 Grade/TPEDU_train_2.csv";
        rr.initialize(new FileSplit(new File(trainingFile)));
        DataSetIterator trainIter = new RecordReaderDataSetIterator(rr,batchSize,0,numOutputs);

        //Load the test/evaluation data:
        RecordReader rrTest = new CSVRecordReader();
        rrTest.initialize(new FileSplit(new File("src/main/resources/weebit/5 Grade/TPEDU_test_2.csv")));
        DataSetIterator testIter = new RecordReaderDataSetIterator(rrTest,batchSize,0,numOutputs);

        
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(1)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(learningRate)
                .updater(Updater.NESTEROVS).momentum(0.9)
                .list(2)
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes)
                        .weightInit(WeightInit.XAVIER)
                        .activation("relu")
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                        .weightInit(WeightInit.XAVIER)
                        .activation("softmax").weightInit(WeightInit.XAVIER)
                        .nIn(numHiddenNodes).nOut(numOutputs).build())
                .pretrain(false).backprop(true).build();


        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(Collections.singletonList((IterationListener) new ScoreIterationListener(1)));
        for ( int n = 0; n < nEpochs; n++) {
        	System.out.println(n);
            model.fit( trainIter );
        }
        
        // Model Saving & Loading
//        ModelUtils mu = new ModelUtils();
//        mu.saveModelAndParameters(model, "C:/Users/Administrator/workspace/deeplearning4j-TPEDU/basepath");
//        model = mu.loadModelAndParameters(new File("C:/Users/Administrator/workspace/deeplearning4j-TPEDU/basepath/mlp-conf.json"), "C:/Users/Administrator/workspace/deeplearning4j-TPEDU/basepath/mlp.bin");
        
        
        System.out.println("Evaluate model....");
        Evaluation eval = new Evaluation(numOutputs);
        int num=0;

        while(testIter.hasNext()){
        	DataSet t = testIter.next();
        	 INDArray features = t.getFeatureMatrix();
             INDArray lables = t.getLabels();
             INDArray predicted = model.output(features,false); // 모델에 의해 예측된 label 값
        
             eval.eval(lables, predicted);
          
             float max = 0;
             int label =0;
             for(int i=0; i< numOutputs;i++){
             	 if( max < predicted.getFloat(i)){
               		 max = predicted.getFloat(i);
               		 label = i;
               	 }
             }
             
             if(label==0){
//               	 System.out.println("초등");
             }else if(label==1){
//               	 System.out.println("중등");
             }else if(label==2){
//             	 System.out.println("고등");
             }else if(label==3){
            	 
             }else if(label==4){

             }else{
               	 System.err.println("Labeling 에러");            	 
             }
        }
       
            
        
//        // 성능평가 결과 출력하는 부분.
        System.out.println(eval.stats());
        result.put("accuracy", (float) eval.accuracy());
        result.put("precision",(float)eval.precision());
        result.put("QWKappa", (float)eval.QWKappa());
		
		return result;
	}
	
	/*
	 * Dynamic 출력
	 */
    public static int feedForward(float[][] feats, float[][] grade, int numOfGrade, boolean isPara) throws IOException, InterruptedException{
         int numOutputs = numOfGrade; // 라벨 갯수
         // Model Saving & Loading
         ModelUtils mu = new ModelUtils();
//         mu.saveModelAndParameters(model, "C:/Users/Administrator/workspace/deeplearning4j-TPEDU/basepath/");
         String bin = "";
         String conf = "";
         if(isPara){
        	 bin = "D:\\Temp\\MLP\\Paragraph\\mlp.bin";
        	 conf = "D:\\Temp\\MLP\\Paragraph\\mlp-conf.json";
         }else{
        	 bin = "D:\\Temp\\MLP\\Sentence\\mlp.bin";
        	 conf = "D:\\Temp\\MLP\\Sentence\\mlp-conf.json";
         }
        	 MultiLayerNetwork model = mu.loadModelAndParameters(new File(conf), bin);
         
         
         System.out.println("Evaluate model....");
         Evaluation eval = new Evaluation(numOutputs);
         int num=0;

         
         INDArray features = Nd4j.create(feats);
         INDArray lables = Nd4j.create(grade);
         INDArray predicted = model.output(features,false); // 모델에 의해 예측된 label 값
         eval.eval(lables, predicted);
      
         float max = 0;
         int label =0;
         for(int i=0; i< numOutputs;i++){
         	 if( max < predicted.getFloat(i)){
           		 max = predicted.getFloat(i);
           		 label = i;
           	 }
         }
         
         if(label==0){
           	 System.out.println("Grade 1");
         }else if(label==1){
           	 System.out.println("Grade 2");
         }else if(label==2){
         	 System.out.println("Grade 3");
         }else if(label==3){
           	 System.out.println("Grade 4");
         }else if(label==4){
         	 System.out.println("Grade 5");
         }else{
           	 System.err.println("Labeling 에러");
         }
             
         
//         // 성능평가 결과 출력하는 부분.
//         System.out.println(eval.stats());
//         ArrayList<Float> ret = new ArrayList<Float>();
//         ret.add(eval.accuracy());
//         ret.add(eval.precision());
//         ret.add(eval.QWKappa());
         
         return label;

    }
}