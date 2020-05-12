package com.pixelcross.cavemanbrawl.util;

/**
 * @author Justin Schreiber
 *
 * Useful debugger tool for finding loop times
 */
public class Timer {

	private static double startTime;
	
	public static void start() {
		startTime = System.currentTimeMillis();
	}
	
	public static double getSeconds() {
		return (System.currentTimeMillis()-startTime)/1000;
	}
	
	public static double getMilliseconds() {
		return (System.currentTimeMillis()-startTime);
	}
	
	public static void printSeconds() {
		double elapsedSeconds = (System.currentTimeMillis()-startTime)/1000;
		System.out.printf("Time taken: %.2fs\n", elapsedSeconds);
	}
	
	public static void printMilliseconds() {
		double elapsedMilliseconds = (System.currentTimeMillis()-startTime);
		System.out.printf("Time taken: %.2fms\n", elapsedMilliseconds);
	}
}
