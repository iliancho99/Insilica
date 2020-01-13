package com.insilicokdd.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.insilicokdd.Utilities.PyExecutor;
import com.insilicokdd.Utilities.PyServeContext;
import com.insilicokdd.data.ChainedModel;
import com.insilicokdd.data.Diagnosis;
import com.insilicokdd.data.Model;
import com.insilicokdd.data.PyResult;
import com.insilicokdd.data.RandomForestModel;
import com.insilicokdd.data.Risk;
import com.insilicokdd.utils.HibernateUtils;
import com.insilicokdd.utils.SecureUtils;
import com.opencsv.CSVWriter;

@Path("py")
public class MyResource {
	private List<Model> result;
	private List<Model> resultGson;
	private List<Diagnosis> resultDiagnosis;
	private Risk riskResult;
	private List<String> tokens;
	private int noOfRows;
	

	    @Path("/authentication")
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response authenticateUser(Credentials credentials) {
			String username = credentials.getUsername();
			String password = credentials.getPassword();

			try {
				// Authenticate the user using the credentials provided
				// and issue a token
			
				String token = authenticate(username, password);

				// Return the token on the response
				return Response.ok(token).build();

			} catch (IllegalArgumentException e) {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
		}
	    

	    @GET
		@Path("/risk/{patientAge}/{motherAge}/{sisterAge}/{maternalAnutAge}/{paternalAnutAge}/{maternalGMAge}/{paternalGMAge}/{maternalHSAge}/{paternalHSAge}")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Risk getIt1(@PathParam("patientAge") String patientAge, @PathParam("motherAge") String motherAge, @PathParam("sisterAge") String sisterAge, 
				@PathParam("maternalAnutAge") String maternalAnutAge, @PathParam("paternalAnutAge") String paternalAnutAge, 
				@PathParam("maternalGMAge") String maternalGMAge, @PathParam("paternalGMAge") String paternalGMAge, 
				@PathParam("maternalHSAge") String maternalHSAge, @PathParam("paternalHSAge") String paternalHSAge) {
	    	
	    	riskResult = null;
	    	PyServeContext pyServeContext = null;
	    	
			try {
				pyServeContext = new PyServeContext("localhost", 8888);
						
//				PyServeContext.init("localhost", 8888);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pyServeContext.close();
			}
					
			ArrayList<String> scripts = new ArrayList<String>();
		
			String script = "from __future__ import division\n" + 
					"import sys\n" + 
					"import os\n" + 
					"\n" + 
					"\n" + 
					"os.environ[\"PYSPARK_SUBMIT_ARGS\"] = \"pyspark-shell\"\n" + 
					"\n" + 
					"\n" + 
					"sys.path.insert(0, \"/home/pborovska/PycharmProjects/default/venv\")\n" + 
					"sys.path.insert(1, \"/home/pborovska/PycharmProjects/default\")\n" + 
					"sys.path.insert(2, \"/usr/lib64/python2.7/site-packages/mpich\")\n" + 
					"sys.path.insert(3, \"'/usr/lib64/python36.zip\")\n" + 
					"sys.path.insert(4, \"/usr/lib64/python3.6\")\n" + 
					"sys.path.insert(5, \"/usr/lib64/python3.6/lib-dynload\")\n" + 
					"\n" + 
					"sys.path.insert(6, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages\")\n" + 
					"sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n" + 
					"sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n" + 
					"sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages\")\n" + 
					"sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n" + 
					"sys.path.insert(9, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n" + 
					"\n" + 
					"sys.path.insert(0, \"/home/pborovska/PycharmProjects/default/venv\")\n" + 
					"sys.path.insert(1, \"/home/pborovska/PycharmProjects/default\")\n" + 
					"sys.path.insert(2, \"/usr/lib64/python2.7/site-packages/mpich\")\n" + 
					"sys.path.insert(3, \"'/usr/lib64/python36.zip\")\n" + 
					"sys.path.insert(4, \"/usr/lib64/python3.6\")\n" + 
					"sys.path.insert(5, \"/usr/lib64/python3.6/lib-dynload\")\n" + 
					"sys.path.insert(6, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages\")\n" + 
					"sys.path.insert(7,\n" + 
					"                \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n" + 
					"sys.path.insert(8,\n" + 
					"                \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n" + 
					"sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages\")\n" + 
					"sys.path.insert(8,\n" + 
					"                \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n" + 
					"sys.path.insert(9,\n" + 
					"                \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n" + 
					"sys.path.insert(10, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree\")\n" + 
					"sys.path.insert(11, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict\")\n" + 
					"sys.path.insert(10, \"/home/pborovska/PycharmProjects/default/venv/claus\")\n" + 
					"sys.path.insert(11, \"/home/pborovska/PycharmProjects/default/venv/clas_tables\")\n" + 
					"sys.path.insert(12, \"/home/pborovska/PycharmProjects/default/venv/dsada\")\n" + 
					"sys.path.insert(13, \"/home/pborovska/PycharmProjects/default/venv/RandomForest\")\n" + 
					"sys.path.insert(14, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict\")\n" + 
					"sys.path.insert(15, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree/file1/metadata\")\n" + 
					"sys.path.insert(16, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree/file1/stages\")\n" + 
					"sys.path.insert(17, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1\")\n" + 
					"sys.path.insert(18, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1/metadata\")\n" + 
					"sys.path.insert(19, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1/stages\")\n" + 
					"sys.path.insert(20, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1\")\n" + 
					"sys.path.insert(21, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1/metadata\")\n" + 
					"sys.path.insert(22, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1/stages\")\n" + 
					"sys.path.insert(23, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1\")\n" + 
					"sys.path.insert(24, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1/metadata\")\n" + 
					"sys.path.insert(25, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1/stages\")\n" + 
					"sys.path.insert(26, \"/home/pborovska/PycharmProjects/default/venv/file1\")\n" + 
					"sys.path.insert(27, \"/home/pborovska/PycharmProjects/default/venv/file1/metadata\")\n" + 
					"sys.path.insert(28, \"/home/pborovska/PycharmProjects/default/venv/file1/metadata/\")\n" + 
					"sys.path.insert(29, \"/home/pborovska/PycharmProjects/default/venv/file1/stages\")\n" + 
					"\n" + 
					"from claus import risk\n" + 
					"\n" +
					"accuracy = risk(" + patientAge + "," + motherAge + "," +  sisterAge + "," + maternalAnutAge + "," + paternalAnutAge + "," + maternalGMAge + "," + paternalGMAge + "," + maternalHSAge + "," + paternalHSAge + ")\n" + 
					"\n" + 
					"_result_ = {\"result\": accuracy}\n" + 
					"\n" + 
					"print(_result_)";
			
			System.out.println(script);
			scripts.add(script);
			for (String s : scripts) {
//				PyResult rs = PyServeContext.getExecutor().exec(s);
				PyResult rs = pyServeContext.getExecutor().exec(s);
				if (rs.isSuccess()) {
					System.out.println("Result: " + rs.getResult());

					try {
						riskResult = new Gson().fromJson(rs.getResult(), Risk.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Execute python script failed: " + rs.getMsg());
				}
			}
//			PyServeContext.close();
			pyServeContext.close();
		
			return riskResult;
		}
	    
	    
	    
	    @POST
		@Path("member")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Member createMember(String json) throws IOException {
			Member member = new Gson().fromJson(json, Member.class);
			new UserService().addMember(Objects.requireNonNull(member));
			return member;
	    }

		@SuppressWarnings("unchecked")
		private String authenticate(String username, String password) throws IllegalArgumentException {
			Transaction transaction = null;
			String token = "";
			String token1 = "";
			try (Session session = HibernateUtils.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();

				@SuppressWarnings("deprecation")
				List<Member> members = session.createCriteria(Member.class)
						.add(Restrictions.eq("account.username", username))
						.add(Restrictions.eq("account.password", password)).list();

				Member member = members.get(0);

				if (member.getToken() == null) {
					// issue a token and persist it into db
					token = issueToken(username);
					token1 = token + member.getId();
					member.getAccount().setToken(token1);
				} else {
					token1 = member.getToken();
					member.getAccount().setToken(token1);
				}

				session.merge(member);
				transaction.commit();
				session.close();
			} catch (HibernateException e) {
				if (transaction != null)
					transaction.rollback();
				e.printStackTrace();
			}
			return token1;
		}

	private String issueToken(String username) {
		return SecureUtils.generateString();
	}


	private String assertOutputCol(String str) {
		StringBuilder sb = new StringBuilder();
		if (!str.startsWith("\""))
			sb.append("\"");
		sb.append(str);
		if (!str.endsWith("\""))
			sb.append("\"");
		return sb.toString();

	}

	private void readFile(InputStream fileIn, String path, boolean excludeHeaders) throws IOException {
		File file = new File("/home/pborovska/Downloads/data_set_api/" + path + ".csv");
		System.out.println("/home/pborovska/Downloads/data_set_api/" + path + ".csv");
		FileWriter outputfile = new FileWriter(file);

		// create CSVWriter with ',' as separator
		CSVWriter writer = new CSVWriter(outputfile, ',', CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

		List<String[]> data = new ArrayList<String[]>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(fileIn))) {
			String row;
			String[] rowData;
			int count = 0;
			if (excludeHeaders) {
				while ((row = br.readLine()) != null) {
					if (count >= 1) {
						rowData = row.split(",");
						data.add(rowData);
					}
					count++;
				}
			} else {
				while ((row = br.readLine()) != null) {
					rowData = row.split(",");
					data.add(rowData);
				}
			}

			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("dataheaders/{modeParam}/{saveTo}/{param1}/{param2}/")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_JSON)
	public List<Model> getHeaders(@FormDataParam("jsonData") final String jsonData,
			@FormDataParam("bin") InputStream bin, @PathParam("modeParam") String modeParam,
			@PathParam("saveTo") String saveTo, @PathParam("param1") String param1,
			@PathParam("param2") String param2) {
		
		try {
			readFile(bin, saveTo, false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String result = "header absorbed " + jsonData;
		System.out.println(result);
		PyServeContext pyServeContext = null;
		try {
//			PyServeContext.init("localhost", 8888);
			pyServeContext = new PyServeContext("localhost", 8888);
	
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("# Test exec Python script");

		ArrayList<String> scripts = new ArrayList<String>();
		String script = "import sys\n" + "# import os\n" + "\n"
				+ "sys.path.insert(0, \"/home/pborovska/PycharmProjects/default/venv\")\n"
				+ "sys.path.insert(1, \"/home/pborovska/PycharmProjects/default\")\n"
				+ "sys.path.insert(2, \"/usr/lib64/python2.7/site-packages/mpich\")\n"
				+ "sys.path.insert(3, \"'/usr/lib64/python36.zip\")\n"
				+ "sys.path.insert(4, \"/usr/lib64/python3.6\")\n"
				+ "sys.path.insert(5, \"/usr/lib64/python3.6/lib-dynload\")\n"
				+ "sys.path.insert(6, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages\")\n"
				+ "sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n"
				+ "sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n"
				+ "sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages\")\n"
				+ "sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n"
				+ "sys.path.insert(9, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n"
				+ "sys.path.insert(10, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree\")\n"
				+ "sys.path.insert(11, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict\")\n"
				+ "sys.path.insert(12, \"/home/pborovska/PycharmProjects/default/venv/dsada\")\n"
				+ "sys.path.insert(13, \"/home/pborovska/PycharmProjects/default/venv/RandomForest\")\n"
				+ "sys.path.insert(14, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict\")\n"
				+ "sys.path.insert(15, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree/file1/metadata\")\n"
				+ "sys.path.insert(16, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree/file1/stages\")\n"
				+ "sys.path.insert(17, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1\")\n"
				+ "sys.path.insert(18, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1/metadata\")\n"
				+ "sys.path.insert(19, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1/stages\")\n"
				+ "sys.path.insert(20, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1\")\n"
				+ "sys.path.insert(21, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1/metadata\")\n"
				+ "sys.path.insert(22, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1/stages\")\n"
				+ "sys.path.insert(23, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1\")\n"
				+ "sys.path.insert(24, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1/metadata\")\n"
				+ "sys.path.insert(25, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1/stages\")\n" 
				+ "sys.path.insert(29, \"/home/pborovska/PycharmProjects/default/venv/file1\")\n"
				+ "sys.path.insert(30, \"/home/pborovska/PycharmProjects/default/venv/file1/metadata\")\n"
				+ "sys.path.insert(31, \"/home/pborovska/PycharmProjects/default/venv/file1/metadata/\")\n"
				+ "sys.path.insert(32, \"/home/pborovska/PycharmProjects/default/venv/file1/stages\")\n" + "\n" + "\n"
				+ "\n" + "def train_patient(mode, diagnosis_model, predict_model):\n" + "    a1 = \"" + saveTo + "\""
				+ "\n" + "    categoricalCols = []\n" + "    _result_ = []\n" + "    res = []\n" + "    \n" + "\n"
				+ "    if diagnosis_model == 1 and mode == 'train':\n"
				+ "        from DecisionTreeTraining import DecisionTreeClassifierTest\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path, type, param0, param1, \n"
				+ "        mode) = DecisionTreeClassifierTest(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure, \"auroc\": auroc,\n"
				+ "                     \"accuracy\": accuracy, \"path\": path, \"type\": type, \"param0\": param0, \"param1\": param1, \"mode\": mode}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if diagnosis_model == 2 and mode == 'train':\n"
				+ "        from RandomForestTrain import RandomForestTrainExecute\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path, type, param0, param1, mode) = RandomForestTrainExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure,\n"
				+ "                     \"auroc\": auroc, \"path\": path, \"type\": type, \"param0\": param0, \"param1\": param1, \"mode\": mode}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if diagnosis_model == 3 and mode == 'train':\n"
				+ "        from LogisticRegressionTrain1 import LogisticRegressionTrainExecutes\n"
				+ "        (name, precision, accuracy, recall, aupr, f1_measure,  auroc, path, type, param0, param1,\n"
				+ "         mode) = LogisticRegressionTrainExecutes(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                    \"f1_measure\": f1_measure,\n"
				+ "                    \"auroc\": auroc, \"path\": path, \"type\": type, \"param0\": param0, \"param1\": param1, \"mode\": mode}\n"
				+ "        res.append(_result_)\n" + "\n" + "\n" + "    # Test 1\n"
				+ "    if diagnosis_model == 1 and mode == 'test':\n"
				+ "        from DecisionTreeClassifierTest import DecisionTreeClassifierTestFunction\n"
				+ "        (predictionsVector, path1, type1) = DecisionTreeClassifierTestFunction(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"diagnosis\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if diagnosis_model == 2 and mode == 'test':\n"
				+ "        from RandomForestTest import RandomForestTestExecute\n"
				+ "        (predictionsVector, path1, type1) = RandomForestTestExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"diagnosis\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if diagnosis_model == 3 and mode == 'test':\n"
				+ "        from LogisticRegressionTest1 import LogisticRegressionTesting\n"
				+ "        (predictionsVector, path1, type1) = LogisticRegressionTesting(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"diagnosis\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n"
				+ "# PREDICTIVE\n" + "\n"
				+ "    if predict_model == 1 and mode == 'train':\n"
				+ "        from DecisionTreeClassifierPredictTrain import DecisionTreeClassifierPredictExec1\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path, type, param0, param1, mode) = DecisionTreeClassifierPredictExec1(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure,\n"
				+ "                     \"auroc\": auroc, \"path\": path, \"type\": type, \"param0\": param0, \"param1\": param1, \"mode\": mode}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if predict_model == 2 and mode == 'train':\n"
				+ "        from RandomForestTrainPredict import RandomForestTrainExecute\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path, type, param0, param1, mode) = RandomForestTrainExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure, \"auroc\": auroc,\n"
				+ "                     \"accuracy\": accuracy, \"path\": path, \"type\": type, \"param0\": param0, \"param1\": param1, \"mode\": mode}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if predict_model == 3 and mode == 'train':\n"
				+ "        from LogisticRegressionPredictTrain import LogisticRegressionTrainExecutes\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path) = LogisticRegressionTrainExecutes(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure, \"auroc\": auroc,\n"
				+ "                     \"accuracy\": accuracy, \"path\": path}\n" + "\n" + "\n"
				+ "    if predict_model == 1 and mode == 'test':\n"
				+ "        from DecisionTreeClassifierPredictTest import DecisionTreeClassifierPredictTestFunction\n"
				+ "        (predictionsVector, path1, type1) = DecisionTreeClassifierPredictTestFunction(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    # Test 2\n"
				+ "    if predict_model == 2 and mode == 'test':\n"
				+ "        from RandomForestTestPredict import RandomForestTestExecute\n"
				+ "        (predictionsVector, path, type) = RandomForestTestExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path, \"type\": type}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if predict_model == 3 and mode == 'test':\n"
				+ "        from LogisticRegressionPredictTest1 import LogisticRegressionTest\n"
				+ "        (predictionsVector, path1, type1) = LogisticRegressionTestExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    return res\n" + "\n" + "\n"
				+ "_result_ = train_patient(\"" + modeParam + "\"" + "," + param1 + "," + param2 + ")" + "\n"
				+ "print(_result_)";
		int pointer = 0;
		tokens = new ArrayList<>();
		String outputCol = "";

		outerloop: for (int i = 0; i < result.length(); i++) {

			if (result.charAt(i) == '[') {
				String[] in = result.split(",");
				if (!in[0].substring(27).equals("\"id\"")) {
					outputCol = in[0].substring(28);
					outputCol = assertOutputCol(outputCol);
					System.out.println(outputCol + "OUTPUT -1");

				} else if (in[0].substring(27).equals("\"id\"")) {
					System.out.println(outputCol + " OUTPUT 1");
					outputCol = in[1];
					outputCol = assertOutputCol(outputCol);
				}
				System.out.println(in[0].substring(27));
				for (int j = 2; j < in.length; j++) {
					tokens.add(in[j].replaceAll("\\]\\}", " "));
					if (j == in.length - 1)
						break outerloop;
				}
			}
		}
		boolean moved = false;

		StringBuilder scriptBuilder = new StringBuilder();
		int count = 0;
		for (int d = 0; d < script.length(); d++) {
			scriptBuilder.append(script.charAt(d));
			if (script.charAt(d) == '[' && count < 1) {
				for (int j = 0; j < tokens.size(); j++) {
					count = 1;
					scriptBuilder.append(tokens.get(j));
					if (j != tokens.size() - 1)
						scriptBuilder.append(",");
					if (script.charAt(d) == ']')
						break;
				}
				moved = true;
			}

			if (count == 2 && moved) {
				moved = false;
				count = 2;
				scriptBuilder.append("\n").append("    ").append("inputCol = ").append(outputCol).append("\n");
				System.out.println(outputCol + "OUTPUTCOL fdasdasfdasfdasfdsafdasfdasfdasfdasfdas");
			}

			if (moved)
				count++;
		}

		String script1 = scriptBuilder.toString();

		System.out.println(script1);
		scripts.add(script1);

		for (String s : scripts) {
//			PyResult rs = PyServeContext.getExecutor().exec(s);
			PyResult rs = pyServeContext.getExecutor().exec(s);
			if (rs.isSuccess()) {
				System.out.println("Result: " + rs.getResult());

				try {
					resultGson = new Gson().fromJson(rs.getResult(), new TypeToken<ArrayList<Model>>() {
					}.getType());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Execute python script failed: " + rs.getMsg());
			}
		}
		pyServeContext.close();

		return resultGson;
	}

	@POST
	@Path("{modeParam}/{saveTo}/{saveTo1}/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public List<Diagnosis> getIt(@FormDataParam("bin") InputStream bin, @PathParam("modeParam") String modeParam,
			@PathParam("saveTo") String saveTo, @PathParam("saveTo1") String saveTo1,
			@PathParam("param1") String param1, @PathParam("param2") String param2) {
		System.out.println("# Test exec Python script");

		try {
			String saveToFinal = saveTo + "_test";
			readFile(bin, saveToFinal, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		PyServeContext pyServeContext = null;
		try {
//			PyServeContext.init("localhost", 8888);
			pyServeContext = new PyServeContext("localhost", 8888);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("# Test exec Python script");

		ArrayList<String> scripts = new ArrayList<String>();
		System.out.println(param1);
		String script = "import sys\n" + "# import os\n" + "\n"
				+ "sys.path.insert(0, \"/home/pborovska/PycharmProjects/default/venv\")\n"
				+ "sys.path.insert(1, \"/home/pborovska/PycharmProjects/default\")\n"
				+ "sys.path.insert(2, \"/usr/lib64/python2.7/site-packages/mpich\")\n"
				+ "sys.path.insert(3, \"'/usr/lib64/python36.zip\")\n"
				+ "sys.path.insert(4, \"/usr/lib64/python3.6\")\n"
				+ "sys.path.insert(5, \"/usr/lib64/python3.6/lib-dynload\")\n"
				+ "sys.path.insert(6, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages\")\n"
				+ "sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n"
				+ "sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n"
				+ "sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages\")\n"
				+ "sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n"
				+ "sys.path.insert(9, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n"
				+ "sys.path.insert(10, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree\")\n"
				+ "sys.path.insert(11, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict\")\n"
				+ "sys.path.insert(12, \"/home/pborovska/PycharmProjects/default/venv/dsada\")\n"
				+ "sys.path.insert(13, \"/home/pborovska/PycharmProjects/default/venv/RandomForest\")\n"
				+ "sys.path.insert(14, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict\")\n"
				+ "sys.path.insert(15, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree/file1/metadata\")\n"
				+ "sys.path.insert(16, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree/file1/stages\")\n"
				+ "sys.path.insert(17, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1\")\n"
				+ "sys.path.insert(18, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1/metadata\")\n"
				+ "sys.path.insert(19, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1/stages\")\n"
				+ "sys.path.insert(20, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1\")\n"
				+ "sys.path.insert(21, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1/metadata\")\n"
				+ "sys.path.insert(22, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1/stages\")\n"
				+ "sys.path.insert(23, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1\")\n"
				+ "sys.path.insert(24, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1/metadata\")\n"
				+ "sys.path.insert(25, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1/stages\")\n"
				+ "sys.path.insert(26, \"/home/pborovska/PycharmProjects/default/venv/file1\")\n"
				+ "sys.path.insert(27, \"/home/pborovska/PycharmProjects/default/venv/file1/metadata\")\n"
				+ "sys.path.insert(28, \"/home/pborovska/PycharmProjects/default/venv/file1/metadata/\")\n"
				+ "sys.path.insert(29, \"/home/pborovska/PycharmProjects/default/venv/file1/stages\")\n" + "\n" + "\n"
				+ "\n" + "def train_patient(mode, diagnosis_model, predict_model):\n" + "    a1 = " + "\"" + saveTo
				+ "\"" + "\n" + "    a2 = " + "\"" + saveTo1 + "\"" + "\n" + "    inputCol = \"diagnosis\"" + "\n"
				+ "    categoricalCols = []\n" + "    _result_ = []\n" + "    res = []\n" + "\n" + "\n"
				+ "    if diagnosis_model == 1 and mode == 'train':\n"
				+ "        from DecisionTreeTraining import DecisionTreeClassifierTest\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path) = DecisionTreeClassifierTest(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure, \"auroc\": auroc,\n"
				+ "                     \"accuracy\": accuracy, \"path\": path}\n" + "        res.append(_result_)\n"
				+ "\n" + "    if diagnosis_model == 2 and mode == 'train':\n"
				+ "        from RandomForestTrain import RandomForestTrainExecute\n"
				+ "        (path, name, precision, recall, f1_measure, aupr, auroc, sensitivity, accuracy) = RandomForestTrainExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure,\n"
				+ "                     \"auroc\": auroc, \"path\": path}\n" + "        res.append(_result_)\n" + "\n"
				+ "\n" + "\n" + "    if diagnosis_model == 1 and mode == 'test':\n"
				+ "        from DecisionTreeClassifierTest import DecisionTreeClassifierTestFunction\n"
				+ "        (predictionsVector, path1, type1) = DecisionTreeClassifierTestFunction(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"diagnosis\": predictionsVector, \"path1\": path1, \"type1\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if diagnosis_model == 2 and mode == 'test':\n"
				+ "        from RandomForestTest import RandomForestTestExecute\n"
				+ "        (predictionsVector, path1, type1) = RandomForestTestExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"diagnosis\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" 
				+ "    if diagnosis_model == 3 and mode == 'test':\n"
				+ "        from LogisticRegressionTest1 import LogisticRegressionTesting\n"
				+ "        (predictionsVector, path1, type1) = LogisticRegressionTesting(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"diagnosis\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" 
				+ "\n" + "    # PREDICTIVE\n" + "\n"
				+ "    if predict_model == 1 and mode == 'train':\n"
				+ "        from DecisionTreeClassifierPredict import DecisionTreeClassifierPredictExec\n"
				+ "        (name, precision, accuracy, recall, aupr, f1_measure, auroc, path) = DecisionTreeClassifierPredictExec(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure,\n"
				+ "                     \"auroc\": auroc, \"path\": path}\n" + "        res.append(_result_)\n" + "\n"
				+ "    if predict_model == 2 and mode == 'train':\n"
				+ "        from RandomForestTrainPredict import RandomForestTrainExecute\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path) = RandomForestTrainExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure,\n"
				+ "                     \"auroc\": auroc, \"path\": path}\n" + "        res.append(_result_)\n" + "\n"
				+ "    if predict_model == 3 and mode == 'train':\n"
				+ "        from LogisticRegressionPredictTrain import LogisticRegressionTrainExecute\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path) = LogisticRegressionTrainExecute(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure, \"auroc\": auroc,\n"
				+ "                     \"accuracy\": accuracy, \"path\": path}\n" + "\n" + "\n"
				+ "    if predict_model == 1 and mode == 'test':\n"
				+ "        from DecisionTreeClassifierPredictTest import DecisionTreeClassifierPredictTestFunction\n"
				+ "        (predictionsVector, path1, type1) = DecisionTreeClassifierPredictTestFunction(a2, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if predict_model == 2 and mode == 'test':\n"
				+ "        from RandomForestTestPredict import RandomForestTestExecute\n"
				+ "        (predictionsVector, path, type) = RandomForestTestExecute(a2, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path, \"type\": type}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if predict_model == 3 and mode == 'test':\n"
				+ "        from LogisticRegressionPredictTest import LogisticRegressionTestExecute\n"
				+ "        (predictionsVector, path1, type1) = LogisticRegressionTestExecute(a2, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    return res\n" + "\n" + "\n"
				+ "_result_ = train_patient(" + "\"" + modeParam + "\"" + "," + param1 + "," + param2 + ")\n"
				+ "print(_result_)";
		System.out.println(script);
		scripts.add(script);
		for (String s : scripts) {
			PyResult rs = pyServeContext.getExecutor().exec(s);
			if (rs.isSuccess()) {
				System.out.println("Result: " + rs.getResult());

				try {
					resultDiagnosis = new Gson().fromJson(rs.getResult(), new TypeToken<ArrayList<Diagnosis>>() {
					}.getType());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Execute python script failed: " + rs.getMsg());
			}
		}
		pyServeContext.close();
		return resultDiagnosis;
	}

	@GET
	@Path("op/{modeParam}/{saveTo}/{param1}/{param2}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Diagnosis> getDiagnosis(@PathParam("modeParam") String modeParam, @PathParam("saveTo") String saveTo,
			@PathParam("param1") String param1, @PathParam("param2") String param2) {
		System.out.println("# Test exec Python script");
			PyServeContext pyServeContext = null;
		try {
//			PyServeContext.init("localhost", 8888);		
			pyServeContext = new PyServeContext("localhost", 8888);
		} catch (Exception e) {							
			e.printStackTrace();				
		}							
			
		System.out.println("# Test exec Python script");

		ArrayList<String> scripts = new ArrayList<String>();
		System.out.println(param1);
		String script = "import sys\n" + "# import os\n" + "\n"
				+ "sys.path.insert(0, \"/home/pborovska/PycharmProjects/default/venv\")\n"
				+ "sys.path.insert(1, \"/home/pborovska/PycharmProjects/default\")\n"
				+ "sys.path.insert(2, \"/usr/lib64/python2.7/site-packages/mpich\")\n"
				+ "sys.path.insert(3, \"'/usr/lib64/python36.zip\")\n"
				+ "sys.path.insert(4, \"/usr/lib64/python3.6\")\n"
				+ "sys.path.insert(5, \"/usr/lib64/python3.6/lib-dynload\")\n"
				+ "sys.path.insert(6, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages\")\n"
				+ "sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n"
				+ "sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib64/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n"
				+ "sys.path.insert(7, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages\")\n"
				+ "sys.path.insert(8, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/setuptools-41.0.1-py3.6.egg\")\n"
				+ "sys.path.insert(9, \"/home/pborovska/PycharmProjects/default/venv/lib/python3.6/site-packages/distribute-0.7.3-py3.6.egg\")\n"
				+ "sys.path.insert(10, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree\")\n"
				+ "sys.path.insert(11, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict\")\n"
				+ "sys.path.insert(12, \"/home/pborovska/PycharmProjects/default/venv/dsada\")\n"
				+ "sys.path.insert(13, \"/home/pborovska/PycharmProjects/default/venv/RandomForest\")\n"
				+ "sys.path.insert(14, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict\")\n"
				+ "sys.path.insert(15, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree/file1/metadata\")\n"
				+ "sys.path.insert(16, \"/home/pborovska/PycharmProjects/default/venv/DecisionTree/file1/stages\")\n"
				+ "sys.path.insert(17, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1\")\n"
				+ "sys.path.insert(18, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1/metadata\")\n"
				+ "sys.path.insert(19, \"/home/pborovska/PycharmProjects/default/venv/DecisionTreeClassifierPredict/file1/stages\")\n"
				+ "sys.path.insert(20, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1\")\n"
				+ "sys.path.insert(21, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1/metadata\")\n"
				+ "sys.path.insert(22, \"/home/pborovska/PycharmProjects/default/venv/RandomForest/file1/stages\")\n"
				+ "sys.path.insert(23, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1\")\n"
				+ "sys.path.insert(24, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1/metadata\")\n"
				+ "sys.path.insert(25, \"/home/pborovska/PycharmProjects/default/venv/RandomForestTrainPredict/file1/stages\")\n"
				+ "sys.path.insert(26, \"/home/pborovska/PycharmProjects/default/venv/file1\")\n"
				+ "sys.path.insert(27, \"/home/pborovska/PycharmProjects/default/venv/file1/metadata\")\n"
				+ "sys.path.insert(28, \"/home/pborovska/PycharmProjects/default/venv/file1/metadata/\")\n"
				+ "sys.path.insert(29, \"/home/pborovska/PycharmProjects/default/venv/file1/stages\")\n" + "\n" + "\n"
				+ "\n" + "def train_patient(mode, diagnosis_model, predict_model):\n" + "    a1 = " + "\"" + saveTo
				+ "\"" + "\n" + "    _result_ = []\n" + "    res = []\n" + "\n" + "\n" + "\n"
				+ "    if diagnosis_model == 1 and mode == 'train':\n"
				+ "        from DecisionTreeTraining import DecisionTreeClassifierTest\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path) = DecisionTreeClassifierTest(a1)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure, \"auroc\": auroc,\n"
				+ "                     \"accuracy\": accuracy, \"path\": path}\n" + "        res.append(_result_)\n"
				+ "\n" + "    if diagnosis_model == 2 and mode == 'train':\n"
				+ "        from RandomForestTrain import RandomForestTrainExecute\n"
				+ "        (path, name, precision, recall, f1_measure, aupr, auroc, sensitivity, accuracy) = RandomForestTrainExecute(a1)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure,\n"
				+ "                     \"auroc\": auroc, \"path\": path}\n" + "        res.append(_result_)\n" + "\n"
				+ "\n" + "\n" + "    if diagnosis_model == 1 and mode == 'test':\n"
				+ "        from DecisionTreeClassifierTest import DecisionTreeClassifierTestFunction\n"
				+ "        (predictionsVector, path1, type1) = DecisionTreeClassifierTestFunction(a1)\n"
				+ "        _result_ = {\"diagnosis\": predictionsVector, \"path1\": path1, \"type1\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if diagnosis_model == 2 and mode == 'test':\n"
				+ "        from RandomForestTest import RandomForestTestExecute\n"
				+ "        (predictionsVector, path1, type1) = RandomForestTestExecute(a1)\n"
				+ "        _result_ = {\"diagnosis\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    # PREDICTIVE\n" + "\n"
				+ "    if predict_model == 1 and mode == 'train':\n"
				+ "        from DecisionTreeClassifierPredict import DecisionTreeClassifierPredictExec\n"
				+ "        (name, precision, accuracy, recall, aupr, f1_measure, auroc, path) = DecisionTreeClassifierPredictExec(a1)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure,\n"
				+ "                     \"auroc\": auroc, \"path\": path}\n" + "        res.append(_result_)\n" + "\n"
				+ "    if predict_model == 2 and mode == 'train':\n"
				+ "        from RandomForestTrainPredict import RandomForestTrainExecute\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path) = RandomForestTrainExecute(a1)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"accuracy\": accuracy, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure,\n"
				+ "                     \"auroc\": auroc, \"path\": path}\n" + "        res.append(_result_)\n" + "\n"
				+ "    if predict_model == 3 and mode == 'train':\n"
				+ "        from LogisticRegressionPredictTrain import LogisticRegressionTrainExecute\n"
				+ "        (name, precision, recall, aupr, f1_measure, auroc, accuracy, path) = LogisticRegressionTrainExecute(a1)\n"
				+ "        _result_ = {\"name\": name, \"precision\": precision, \"recall\": recall, \"aupr\": aupr,\n"
				+ "                     \"f1_measure\": f1_measure, \"auroc\": auroc,\n"
				+ "                     \"accuracy\": accuracy, \"path\": path}\n" + "\n" + "\n"
				+ "    if predict_model == 1 and mode == 'test':\n"
				+ "        from DecisionTreeClassifierPredictTest import DecisionTreeClassifierTestFunction\n"
				+ "        (predictionsVector, path1, type1) = DecisionTreeClassifierTestFunction(a1, categoricalCols, inputCol)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if predict_model == 2 and mode == 'test':\n"
				+ "        from RandomForestTestPredict import RandomForestTestExecute\n"
				+ "        (predictionsVector, path, type) = RandomForestTestExecute(a1)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path, \"type\": type}\n"
				+ "        res.append(_result_)\n" + "\n" + "    if predict_model == 3 and mode == 'test':\n"
				+ "        from LogisticRegressionPredictTest import LogisticRegressionTestExecute\n"
				+ "        (predictionsVector, path1, type1) = LogisticRegressionTestExecute(a1)\n"
				+ "        _result_ = {\"outcome\": predictionsVector, \"path\": path1, \"type\": type1}\n"
				+ "        res.append(_re" + "sult_)\n" + "\n" + "    return res\n" + "\n" + "\n"
				+ "_result_ = train_patient(" + "\"" + modeParam + "\"" + "," + param1 + "," + param2 + ")\n"
				+ "print(_result_)";
		System.out.println(script);
		scripts.add(script);
		for (String s : scripts) {
			PyResult rs = pyServeContext.getExecutor().exec(s);
			if (rs.isSuccess()) {
				System.out.println("Result: " + rs.getResult());

				try {
					resultDiagnosis = new Gson().fromJson(rs.getResult(), new TypeToken<ArrayList<Diagnosis>>() {
					}.getType());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Execute python script failed: " + rs.getMsg());
			}
		}
		pyServeContext.close();

		return resultDiagnosis;
	}

//	@GET
//	@Path("rndForest/{scriptParam}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<RandomForestModel> getRandomForestResult(@PathParam("scriptParam") String scriptParam) {
//		System.out.println(scriptParam);
//		try {
//			PyServeContext.init("localhost", 8889);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		List<RandomForestModel> result = new PythonAdapter().testExecFileRandomForest(scriptParam);
//		PyServeContext.close();
//		return result;
//	}
//
//	@GET
//	@Path("chainedmodel/{scriptParam}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<ChainedModel> execChainedModelTest(@PathParam("scriptParam") String scriptParam) {
//		System.out.println("Script param" + scriptParam);
//		try {
//			PyServeContext.init("localhost", 8889);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		List<ChainedModel> diagnosisResult = new PythonAdapter().execChainedModelTest(scriptParam);
//		System.out.print(diagnosisResult);
//		PyServeContext.close();
//		return diagnosisResult;
//	}		
//	
//	@GET
//	@Path("test/{scriptParam}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Diagnosis> getDiagnosis(@PathParam("scriptParam") String scriptParam) {
//		System.out.println("Script param" + scriptParam);
//		try {
//			PyServeContext.init("localhost", 8889);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		List<Diagnosis> diagnosisResult = new PythonAdapter().getDiagnosis(scriptParam);
//		System.out.print(diagnosisResult);
//		PyServeContext.close();
//		return diagnosisResult;
//	}

}
