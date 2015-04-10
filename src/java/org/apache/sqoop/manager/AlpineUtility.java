package org.apache.sqoop.manager;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configuration;

/***
 * Use this to add the double quote for sqoop's table name and column name 
 * @author john
 *
 */
public class AlpineUtility {

	public static String doubleQ(String inputString) {
		if (inputString == null) {
			inputString = "";
		}
		// inputString=inputString.trim();
		if (!inputString.startsWith("\"") || !inputString.endsWith("\"")) {
			//inputString = inputString.replace("\"", "\"\"");     this is no use for sqoop teradata we haveo to input schema"."table ->"schema"."table"
			inputString = "\"" + inputString + "\"";
		}
		return inputString;
	}

	public static String[] doubleQ(String[] fieldNames) {
		if (fieldNames != null) {
			fieldNames = Arrays.copyOf(fieldNames, fieldNames.length);

			for (int i = 0; i < fieldNames.length; i++) {
				fieldNames[i] = AlpineUtility.doubleQ(fieldNames[i]);
			}

		}
		return fieldNames;
	}
	
	public static void notifyJobStart(Configuration conf, Job job) {
		String jobUUID = conf.get("JOB_UUID");
	    
		try {
			Class<?> jobCacheClass = Class.forName("com.alpine.datamining.api.impl.hadoop.postjob.AlpineJobCache");
			if(jobCacheClass!=null){
				Method method = jobCacheClass.
			    		getMethod("notifyNewJob", String.class,Job.class);
			    if(method!=null){
			    	method.invoke(jobCacheClass, jobUUID, job);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
 
}
