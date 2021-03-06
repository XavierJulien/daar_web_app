package com.hxsarl.daar_web_app.java;

public class PageRank {

	void prodmatvect(Graph G, double []A, int n, double []B){
		int i;
		for(i=0; i < n; i++){
			B[i] = 0;
		}
		for(i=0; i < n; i++){
			int degree = G.degree(i);
			for(int v : G.voisins(i)){
				B[v] += (A[i]/degree);
			}
		}
	}

	public double [] page_rank(Graph G, double alpha, int t, int n) {
		double[] I = new double[n];
		double[] P = new double[n];
		double[] P_tmp = new double[n];
		int i, j;

		for(i = 0; i < n; i++) {
			I[i] = 1.0 / n;
			P_tmp[i] = 1.0/n;
			P[i] = 1.0/n;
		}

		double norm = 0;
		boolean converging = false;
		i=0;
		while (!converging  && i < t){
			converging = true;
			prodmatvect(G, P_tmp, n, P);

			norm = 0;
			for(j=0; j<n; j++){
				P[j] = (1-alpha)*P[j] + alpha*I[j];
				norm += P[j];
				//printf("P[%d] before vaut %g\n", j,P[j]);
			}
			for(j=0; j<n; j++){
				P[j] += (1-norm)/(1.0*n);
				if(P_tmp[j] != P[j])
					converging = false;
				P_tmp[j] = P[j];
			}
			//printf("P[j] after vaut %lf\n", P[j]);
			System.out.println("Finished iteration " + i);
			i = i+1;
		}

		return P;
	}
	
	public  void test() {
		Graph g = new Graph(8);
		g.addEdgeDirected(0, 1);
		g.addEdgeDirected(0, 3);
		g.addEdgeDirected(0, 2);
		g.addEdgeDirected(1, 2);
		g.addEdgeDirected(3, 2);
		g.addEdgeDirected(1, 4);
		g.addEdgeDirected(4, 0);
		g.addEdgeDirected(3, 5);
		g.addEdgeDirected(5, 0);
		g.addEdgeDirected(2, 6);
		g.addEdgeDirected(6, 0);
		g.addEdgeDirected(2, 7);
		g.addEdgeDirected(7, 0);
		
		double[] scores = page_rank(g, 0.15, 10, g.size());
		
	}
	
	public static void main(String[] args) {
		PageRank p = new PageRank();
		p.test();
		
	}
}
