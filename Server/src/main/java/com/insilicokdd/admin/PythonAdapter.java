package com.insilicokdd.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.insilicokdd.Utilities.PyExecutor;
import com.insilicokdd.Utilities.PyServeContext;
import com.insilicokdd.data.PyResult;
import com.insilicokdd.data.RandomForestModel;
import com.insilicokdd.data.ChainedModel;
import com.insilicokdd.data.Diagnosis;
import com.insilicokdd.data.Model;

//public class PythonAdapter {
//	private String param = "";
//	private List<Model> result;
//	private List<RandomForestModel> randomForestResult;
//	private List<Diagnosis> diagnosisResult;
//	private List<ChainedModel> chainedResult;
//
//	List<ChainedModel> execChainedModelTest(String scriptParam) {
//		System.out.println("\n# Test exec Python file");
//		PyResult rs = PyServeContext.getExecutor()
//				.exec(new File("/home/pborovska/PycharmProjects/default/venv/" + scriptParam));
//		if (rs.isSuccess()) {
//			System.out.println("Result: " + rs.getResult());
//			
//			try {
//				chainedResult = new Gson().fromJson(rs.getResult(), new TypeToken<ArrayList<ChainedModel>>() {}.getType());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("Execute python script failed: " + rs.getMsg());
//		}
//		return chainedResult;	
//	}
//
//	List<Model> testExecFile() {
//		System.out.println("# Test exec Python script");
//		PyExecutor executor = PyServeContext.getExecutor();
//		ArrayList<String> scripts = new ArrayList<String>();
//		
//		String script = "import sys\n" + 
//				"# import os\n" + 
//				"\n" + 
//				"sys.path.insert(0, \"/home/pborovska/PycharmProjects/default/venv\")\n" + 
//				"sys.path.insert(1, \"/home/pborovska/PycharmProjects/default\")\n" + 
//				"sys.path.insert(2, \"/usr/lib64/python2.7/site-packages/mpich\")\n" + 
//				"sys.path.insert(3, \"'/usr/lib64/python36.zip\")\n" + 
//				"sys.path.insert(4, \"/usr/lib64/python3.6\")\n" + 
//				"sys.path.insert(5, \"/usr/lib64/python3.6/lib-dynload\")\n" + 
//				"sys.path.insert(6, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages\")\n" + 
//				"sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n" + 
//				"sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n" + 
//				"sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages\")\n" + 
//				"sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n" + 
//				"sys.path.insert(9, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n" + 
//				"# os.open('python FetchData.py fetcherTree')\n" + 
//				"\n" + 
//				"# a1 = sys.argv[1]\n" + 
//				"from DecisionTreeTraining import DecisionTreeClassifierTest\n" + 
//				"\n" + 
//				"(name, precision, recall, aupr, f1_measure, auroc, accuracy, path) = DecisionTreeClassifierTest(a1)\n" + 
//				"_result_ = [{\"name\": name, \"precision\": precision, \"recall\": recall,\"aupr\": aupr,\n" + 
//				"             \"f1_measure\": f1_measure, \"auroc\": auroc,\n" + 
//				"             \"accuracy\": accuracy, \"path\": path }]\n" + 
//				"\n" + 
//				"print(_result_)\n" + 
//				"";
//		scripts.add(script);
//		
//		for (String s : scripts) {
//			PyResult rs = executor.exec(s);
//			if (rs.isSuccess()) {
//				System.out.println("Result: " + rs.getResult())	;
//				
//				try {
//					result = new Gson().fromJson(rs.getResult(), new TypeToken<ArrayList<Model>>() {}.getType());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				System.out.println("Execute python script failed: " + rs.getMsg());
//			}
//		}
//		
//		return result;	
//	}
//	
//	List<RandomForestModel> testExecFileRandomForest(String scriptParam) {
//		System.out.println("\n# Test exec Python file");
//		PyResult rs = PyServeContext.getExecutor()
//				.exec(new File("/home/pborovska/PycharmProjects/default/venv/" + scriptParam));
//		if (rs.isSuccess()) {
//			System.out.println("Result: " + rs.getResult());
//			
//			try {
//				randomForestResult = new Gson().fromJson(rs.getResult(), new TypeToken<ArrayList<RandomForestModel>>() {}.getType());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("Execute python script failed: " + rs.getMsg());
//		}
//		return randomForestResult;	
//	}
//	
//	List<Diagnosis> getDiagnosis(String scriptParam) {
//		System.out.println("\n# Test exec Python file");
//		PyResult rs = PyServeContext.getExecutor()
//				.exec(new File("/home/pborovska/PycharmProjects/default/venv/" + scriptParam));
//		if (rs.isSuccess()) {
//			System.out.println("Diagnosis Result: " + rs.getResult());
//			try {
//				diagnosisResult = new Gson().fromJson(rs.getResult(), new TypeToken<ArrayList<Diagnosis>>() {}.getType());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("Execute python script failed: " + rs.getMsg());
//		}
//		return diagnosisResult;	
//	}
//}
//
//
//
//
//
//
//
