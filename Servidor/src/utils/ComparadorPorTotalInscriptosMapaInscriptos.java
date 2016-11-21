package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import entities.Report;

public class ComparadorPorTotalInscriptosMapaInscriptos implements Comparator<Map.Entry<String, ArrayList<Integer>>> {
	
    @Override
    public int compare(Map.Entry<String, ArrayList<Integer>> e1, Map.Entry<String, ArrayList<Integer>> e2) {
    	ArrayList<Integer> listado_1 = e1.getValue();
    	ArrayList<Integer> listado_2 = e2.getValue();
    	
    	Integer total_1 = 0;
    	for( Integer valor : listado_1 ) {
    		total_1 += valor;
    	}
    	
    	Integer total_2 = 0;
    	for( Integer valor : listado_2 ) {
    		total_2 += valor;
    	}
    	
    	return total_2.compareTo(total_1);
    }
}
