package com.hxsarl.daar_web_app.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DistanceJaccard {
	public double distanceJaccard(Set<String> f1, Set<String> f2) {
		
		// u_i U v_i
		Set<String> union = new HashSet<>();
		union.addAll(f1);
		union.addAll(f2);
		
		// |(u_i U v_i)|
		double size_u = union.size();
		
		//u_i \ v_i
		Set<String> f1_diff_f2 = new HashSet<>();
		f1_diff_f2.addAll(f1);
		f1_diff_f2.removeAll(f2);
		
		// v_i \ u_i
		Set<String> f2_diff_f1 = new HashSet<>();
		f2_diff_f1.addAll(f2);
		f2_diff_f1.removeAll(f1);
		
		//(u_i \ v_i) U (v_i \ u_i)
		f1_diff_f2.addAll(f2_diff_f1);
		
		//|(u_i \ v_i) U (v_i \ u_i)|
		double size_u_diff = f1_diff_f2.size();
		
		return size_u_diff/size_u;
	}
	
	static public double distanceJaccard(Map<String, Integer> f1, Map<String,Integer> f2) {
		AtomicInteger max = new AtomicInteger();
		AtomicInteger diff = new AtomicInteger();
		Map<String, Integer> ff1=f1.entrySet()
				.parallelStream()
				.collect(Collectors.toMap(Map.Entry::getKey,
										Map.Entry::getValue));
		Map<String, Integer> ff2 =f2.entrySet()
				.parallelStream()
				.collect(Collectors.toMap(Map.Entry::getKey,
										Map.Entry::getValue));

		f2.keySet().parallelStream().forEach(x->{ if (f1.get(x)==null)
			ff1.put(x, 0);});
		f1.keySet().parallelStream().forEach(x->{ if (f2.get(x)==null)
			ff2.put(x, 0);});
		
		ff2.keySet().parallelStream().forEach(x->{ 
			
			diff.addAndGet((Integer)Math.abs(ff2.get(x)- ff1.get(x))
					);
			max.addAndGet((Integer)Math.max(ff2.get(x), ff1.get(x))
					);
			
		} );
		
		return diff.doubleValue()/max.doubleValue();
	}
	public static void main (String []arg) {
		Map<String,Integer> w = new HashMap<>();
		Map<String,Integer> s = new HashMap<>();
		s.put("bien",1);
		s.put("dormir",1);
		s.put("matin",1);
		w.put("lève",1);
		w.put("matin",1);
		DistanceJaccard a = new DistanceJaccard();
		System.out.println((a.distanceJaccard(s, w)));
		System.out.println(a.distanceJaccard(s.keySet(), w.keySet()));
	
	}
}
