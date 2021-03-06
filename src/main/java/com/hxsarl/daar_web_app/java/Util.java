package com.hxsarl.daar_web_app.java;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Util {
	
	static public Graph chooseThreshold(double[][] mat) {
		double[] matDevelopped = new double[mat.length*mat.length];
		
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				matDevelopped[i*mat.length + j] = mat[i][j];
			}
		}
		
		Arrays.sort(matDevelopped);
		Graph res = null;
		
		int indexEdgeThreshold = Math.floorDiv(3*matDevelopped.length, 4);
		double edgeThreshold ;
		do {

			edgeThreshold = matDevelopped[indexEdgeThreshold];
			res = Graph.gFromMat(edgeThreshold, mat);
			indexEdgeThreshold ++;
		}while(!res.isConnex());
		
		return res;
	}
	
	static public List<Graph> threshold75(double[][] mat) throws IOException{
		double[] matDevelopped = new double[mat.length*mat.length];
		
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				matDevelopped[i*mat.length + j] = mat[i][j];
			}
		}
		
		Arrays.sort(matDevelopped);
		Graph res = null;
		
		int indexEdgeThreshold = Math.floorDiv(3*matDevelopped.length, 4);
		double edgeThreshold = matDevelopped[indexEdgeThreshold];
		res = Graph.gFromMat(edgeThreshold, mat);
		res.writeGraph("../graphNonConnex.txt");
		return res.connexComp();
		
	}
}
