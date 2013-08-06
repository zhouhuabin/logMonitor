package com.palmcity.rtti.maintenancemonitor.service;

import java.util.ArrayList;
import java.util.List;


public class ThreadTest {

	/**
	 * FIXME 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int ThreadNum=3;
		int MaxSvg=8/ThreadNum;
		System.out.println("sad");
		
		for(int i=0;i<ThreadNum;i++)
		{
			System.out.println(i*MaxSvg+"  "+(i==ThreadNum-1?8:MaxSvg*(i+1)));
		}
		
	}

}
