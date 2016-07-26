package com.hanyang.iis.tpedu.mlp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

import org.nd4j.linalg.factory.Nd4j;
/**
 * "Linear" Data Classification Example
 *
 * Based on the data from Jason Baldridge:
 * https://github.com/jasonbaldridge/try-tf/tree/master/simdata
 *
 * @author Josh Patterson
 * @author Alex Black (added plots)
 *
 */
public class MLPClassifierLinear {
    
    /*public static int feedForward(int numOfGrade, int numInputs, int seed, int batchSize, int nEpochs, int numHiddenNodes, double learningRate, int dataSize, boolean isNormalized) throws IOException, InterruptedException{*/
    public static int feedForward(double[][] feats, double[][] grade,int numOfGrade, int numInputs, int seed, int batchSize, int nEpochs, int numHiddenNodes, double learningRate) throws IOException, InterruptedException{
 
         int numOutputs = numOfGrade; // 라벨 갯수
         
         /*//Load the training data:
         RecordReader rr = new CSVRecordReader();
         String trainingFile;
         //trainingFile = "src/main/resources/classification/real/TPEDU_train.csv";
         trainingFile = "D:/Temp/TPEDU_train.csv";
         
         rr.initialize(new FileSplit(new File(trainingFile)));
         DataSetIterator trainIter = new RecordReaderDataSetIterator(rr,batchSize,0,numOutputs);
         
         //Load the test/evaluation data:
         RecordReader rrTest = new CSVRecordReader();
         String testFile;
         testFile = "D:/Temp/TPEDU_test.csv";
         
         rrTest.initialize(new FileSplit(new File(testFile)));
         DataSetIterator testIter = new RecordReaderDataSetIterator(rrTest,1,0,numOutputs);

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
         	 //System.out.println(n);
             model.fit( trainIter );
         }

         System.out.println("Evaluate model....");
         int label =0;
         while(testIter.hasNext()){
//        	 System.out.println(num++ +" iteration");
             DataSet t = testIter.next();
             INDArray features = t.getFeatureMatrix();
             INDArray lables = t.getLabels(); // 실제 label 값
             INDArray predicted = model.output(features,false); // 모델에 의해 예측된 label 값
             
             double max = 0.0;
         
             for(int i=0; i< numOutputs;i++){
            	 if( max < predicted.getDouble(i)){
            		 max = predicted.getDouble(i);
            		 label = i;
            	 }
             }
         }*/
         

         // Model Saving & Loading
         ModelUtils mu = new ModelUtils();
//         mu.saveModelAndParameters(model, "C:/Users/GGong/workspace/deeplearning4j-examples/basepath/");
         MultiLayerNetwork model = mu.loadModelAndParameters(new File("D:/Temp/MLP/mlp-conf.json"), "D:/Temp/MLP/mlp.bin");
         
         
         System.out.println("Evaluate model....");
         Evaluation eval = new Evaluation(numOutputs);
         int num=0;
         
         INDArray features = Nd4j.create(feats);
         INDArray lables = Nd4j.create(grade);
         INDArray predicted = model.output(features,false); // 모델에 의해 예측된 label 값
         eval.eval(lables, predicted);
      
         double max = 0.0;
         int label =0;
         for(int i=0; i< numOutputs;i++){
         	 if( max < predicted.getDouble(i)){
           		 max = predicted.getDouble(i);
           		 label = i;
           	 }
         }
         
         /*if(label==0){
           	 System.out.println("초등");
         }else if(label==1){
           	 System.out.println("중등");
         }else if(label==2){
         	 System.out.println("고등");
         }else{
           	 System.err.println("Labeling 에러");
         }*/
         
         // 제일 최근 입력값을 그냥 label로 리턴
         return label;
    }
}