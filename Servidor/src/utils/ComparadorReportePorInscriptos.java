package utils;

import java.util.Comparator;

import entities.Report;

public class ComparadorReportePorInscriptos implements Comparator<Report> {
	
    @Override
    public int compare(Report r1, Report r2) {
        return r2.getTotalPupils().compareTo( r1.getTotalPupils() );
    }
}
