package com.hxsarl.daar_web_app.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
public class Closeness {
	public double[] closeness(double[][] dists,int taille) {
		double[] res = new double[taille];
		double dist =0.0;
		/*for (int i = 0;i<taille;i++) {
			for (int j =0;j<taille;j++) {
				dist = dist + dists[i][j];
			}
			res[i]=(taille-1)/dist;
			dist=0.0;
		}*/
		IntStream.range(0, taille).forEach(i -> {
			OptionalDouble distt=IntStream.range(0, taille).mapToDouble(j -> dists[i][j]).reduce((x,y)->x+y);
			res[i]=(taille-1)/distt.getAsDouble();
			;
			
		});
		return res;
	}
	public double[][] matfromg(Graph g,double[][] dists, int taille) {
		double[][] res = new double[taille][taille];
		for(int i=0;i<taille;i++) {
			for (int j=0;j<taille;j++) {
				res[i][j]=dists[g.prvIndex.get(i)][g.prvIndex.get(j)];
				
			}
		}
		return res;
	}
	public double[][] floydWarshallv2(Graph g, double edgeThreshold) {
	
		ArrayList<Set<Integer>> edge = new ArrayList<>();
		for(Entry<Integer, Set<Integer>> e: g.adjList.entrySet()) {
			edge.add(e.getValue());
		}
		
		int n = edge.size();
		double[][] M = new double[n][n];

		ArrayList<ArrayList<Set<Integer>>> R = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			R.add(new ArrayList<>());
			for (int j = 0; j < n; j++) {
				if (i==j)
					M[i][j]=0;
				else 
					M[i][j]= Double.POSITIVE_INFINITY;

				R.get(i).add(new HashSet<>());
				// System.out.println("R[i][j] vaut " + R[i][j]);
			}
		}
		for (int i=0;i<edge.size();i++) {
			R.get(i).get(i).add(i);
			for (Integer e:edge.get(i)) {
				M[i][e]=1;
				R.get(i).get(e).add(e);
			}
		}
		

		for (int p = 0; p < n; p++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {

					if((i==p || j==p) || i==j) continue;
					
					if(Double.isInfinite(M[i][p]) || Double.isInfinite(M[p][j])) continue;
					
					if (M[i][p] + M[p][j] < M[i][j]) {

						M[i][j] = M[i][p] + M[p][j];
						R.get(i).get(j).clear();
						R.get(i).get(j).add(p);
					}
					if (M[i][p] + M[p][j] == M[i][j]) {
						R.get(i).get(j).add(p);
					}

				}
			}
		}
		return M;
	}
}
