package com.pixelcross.cavemanbrawl.neuralnetwork.trainset;

import java.util.ArrayList;
import java.util.Arrays;

import com.pixelcross.cavemanbrawl.neuralnetwork.fullyconnectednetwork.Network;
import com.pixelcross.cavemanbrawl.neuralnetwork.fullyconnectednetwork.NetworkTools;
import com.pixelcross.cavemanbrawl.neuralnetwork.parser.Attribute;
import com.pixelcross.cavemanbrawl.neuralnetwork.parser.Node;
import com.pixelcross.cavemanbrawl.neuralnetwork.parser.Parser;
import com.pixelcross.cavemanbrawl.neuralnetwork.parser.ParserTools;

/**
 * Created by Luecx on 09.08.2017.
 */
public class TrainSet {

    public final int INPUT_SIZE;
    public final int OUTPUT_SIZE;

    //double[][] <- index1: 0 = input, 1 = output || index2: index of element
    private ArrayList<double[][]> data = new ArrayList<>();

    public TrainSet(int INPUT_SIZE, int OUTPUT_SIZE) {
        this.INPUT_SIZE = INPUT_SIZE;
        this.OUTPUT_SIZE = OUTPUT_SIZE;
    }

    public void addData(double[] in, double[] expected) {
        if(in.length != INPUT_SIZE || expected.length != OUTPUT_SIZE) return;
        data.add(new double[][]{in, expected});
    }

    public TrainSet extractBatch(int size) {
        if(size > 0 && size <= this.size()) {
            TrainSet set = new TrainSet(INPUT_SIZE, OUTPUT_SIZE);
            Integer[] ids = NetworkTools.randomValues(0,this.size() - 1, size);
            for(Integer i:ids) {
                set.addData(this.getInput(i),this.getOutput(i));
            }
            return set;
        }else return this;
    }

    public void saveTrainSet(String fileName) throws Exception {
        Parser p = new Parser();
        p.create(fileName);
        Node root = p.getContent();
        Node trainSet = new Node("TrainSet");
        Node set = new Node("Sets");
        trainSet.addAttribute(new Attribute("setSize", "" + data.size()));
        trainSet.addAttribute(new Attribute("inputSize", "" + INPUT_SIZE));
        trainSet.addAttribute(new Attribute("outputSize", "" + OUTPUT_SIZE));
        trainSet.addChild(set);
        root.addChild(trainSet);
        for (int setNum = 1; setNum < data.size(); setNum++) {

            Node c = new Node("Set" + setNum);
            set.addChild(c);

            c.addAttribute("input", Arrays.toString(data.get(setNum)[0]));
            c.addAttribute("output", Arrays.toString(data.get(setNum)[1]));

        }
        p.close();
    }

    public static TrainSet loadTrainSet(String fileName) throws Exception {

        Parser p = new Parser();

            p.load(fileName);
            int setSize = Integer.parseInt(p.getValue(new String[] { "TrainSet" }, "setSize"));
            int inputSize = Integer.parseInt(p.getValue(new String[] { "TrainSet" }, "inputSize"));
            int outputSize = Integer.parseInt(p.getValue(new String[] { "TrainSet" }, "outputSize"));
            TrainSet set = new TrainSet(inputSize, outputSize);

            for (int i = 1; i < setSize; i++) {
                String inputString = p.getValue(new String[] { "TrainSet", "Sets", "Set" + i}, "input");
                double[] input = ParserTools.parseDoubleArray(inputString);
                String outputString = p.getValue(new String[] { "TrainSet", "Sets", "Set" + i}, "output");
                double[] output = ParserTools.parseDoubleArray(outputString);

                set.addData(input, output);
            }
            p.close();
            
            return set;
    }

    public String toString() {
        String s = "TrainSet ["+INPUT_SIZE+ " ; "+OUTPUT_SIZE+"]\n";
        int index = 0;
        for(double[][] r:data) {
            s += index +":   "+Arrays.toString(r[0]) +"  >-||-<  "+Arrays.toString(r[1]) +"\n";
            index++;
        }
        return s;
    }

    public int size() {
        return data.size();
    }

    public double[] getInput(int index) {
        if(index >= 0 && index < size())
            return data.get(index)[0];
        else return null;
    }

    public double[] getOutput(int index) {
        if(index >= 0 && index < size())
            return data.get(index)[1];
        else return null;
    }

    public int getINPUT_SIZE() {
        return INPUT_SIZE;
    }

    public int getOUTPUT_SIZE() {
        return OUTPUT_SIZE;
    }
}
