package com.insilicokdd.workers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.insilicokdd.data.DataSetHeaders;
import com.insilicokdd.data.Model;
import com.insilicokdd.machine_learning.ConfigurationPanel;
import com.insilicokdd.machine_learning.ResultModelPanel;
import com.insilicokdd.machine_learning.ResultPanel;
import com.insilicokdd.operational_mode.SelectionPanel;

import com.insilicokdd.operational_mode.WorkflowPanel;
import com.opencsv.CSVReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SendDataSet extends SwingWorker<Model, Void> {
    private File dataSetFile;
    private List<String> dataSetheaders;
    private JLabel jLabel;
    private String configChoosed;
    private ResultPanel resultPanel;
    private SelectionPanel selectionPanel;
    private List<Model> models;
    private static int predictiveCounter;
    private static int diagnosisCounter;
    private static int counter;
    private ConfigurationPanel preferencesPanel;
    private WorkflowPanel workflowPanel;
    private String dataSetHeadersShow;
    private JButton executeBtn;
    private JButton clearBtn;

    static {
        predictiveCounter = 0;
        diagnosisCounter = 0;
        counter = 0;
    }

    public SendDataSet(File dataSetFile, ResultPanel resultPanel, SelectionPanel selectionPanel, String configChoosed,
                      ConfigurationPanel preferencePanel, WorkflowPanel workflowPanel, JButton executeBtn, JButton clearBtn) {
        this.dataSetFile = dataSetFile;
        dataSetheaders = new ArrayList<>();
        jLabel = new JLabel();
        this.resultPanel = resultPanel;
        this.selectionPanel = selectionPanel;
        models = new ArrayList<>();
        this.configChoosed = configChoosed;
        this.preferencesPanel = preferencePanel;
        this.workflowPanel = workflowPanel;
        this.executeBtn = executeBtn;
        this.clearBtn = clearBtn;
    }

    @Override
    protected Model doInBackground() throws Exception {
        List<Model> result = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response2 = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(dataSetFile))) {
            String[] nextRecord;
            int pointer = 0;
            while ((nextRecord = csvReader.readNext()) != null && pointer++ < 1) {
                for (String cell : nextRecord) {
                    dataSetheaders.add(cell);
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
            dataSetHeadersShow = String.join(",", dataSetheaders);

            Gson gson = new Gson();
            DataSetHeaders dataSetHeaders = new DataSetHeaders();
            dataSetHeaders.setTokens(dataSetheaders);

            httpclient = HttpClients.createDefault();
            StringBuilder url = new StringBuilder();

            url.append("http://81.161.246.43:8080/insilicokdd/webresources/py/dataheaders/").append(configChoosed);
            System.out.println(url + " URL");
            System.out.println(url + "D");
            HttpPost httpPost = new HttpPost(url.toString());
            String JSON_STRING = gson.toJson(dataSetHeaders);
            System.out.println(JSON_STRING);
            StringBody stringBody = new StringBody(JSON_STRING, ContentType.APPLICATION_JSON);
            StringBuilder sb = new StringBuilder();


            FileBody bin = new FileBody(dataSetFile);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("jsonData", stringBody)
                    .addPart("bin", bin)
                    .build();

            httpPost.setEntity(reqEntity);
            response2 = httpclient.execute(httpPost);

            if (response2.getStatusLine().getStatusCode() != 200) {
                executeBtn.setEnabled(true);
                clearBtn.setEnabled(true);
                resultPanel.getLoadingLabel().setVisible(false);
                throw new RuntimeException("Failed : HTTP error code : "
                        + response2.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response2.getEntity().getContent())));
            System.out.println("Output from server ... \n");
            result = new Gson().fromJson(br.readLine(), new TypeToken<ArrayList<Model>>() {
            }.getType());
            System.out.println(result);
        } finally {
            if (httpclient != null)
                httpclient.close();
            if (response2 != null)
                response2.close();
        }

        return result.get(0);
    }


    @Override
    protected void done() {
        try {
            if (get() == null)  { resultPanel.getLoadingLabel().setVisible(false); return;}
            executeBtn.setEnabled(true);
            clearBtn.setEnabled(true);
//            clearBtn.setEnabled(false);
            boolean isPersisted = SelectionPanel.getPrefs().getBoolean("isPersisted", false);
            System.out.println("persistance in datasend" + isPersisted);
            if (isPersisted) {
                diagnosisCounter = SelectionPanel.getPrefs().getInt("diagnosticListSize", 0);
                predictiveCounter = SelectionPanel.getPrefs().getInt("predictiveListSize", 0);
            }
            if (get().getName().contains("(D)")) {
                selectionPanel.getDiagnosisModels().add(get());
                diagnosisCounter++;
                selectionPanel.getDiagnosisModels().get(diagnosisCounter - 1).setSavedPathModel(preferencesPanel.getModelPathLabel().getText().trim());
                selectionPanel.getDiagnosisModels().get(diagnosisCounter - 1).setId();
                System.out.println(selectionPanel.getDiagnosisModels().get(diagnosisCounter - 1).getId() + " Diagnosis id");
                selectionPanel.getDiagnosisJList().updateUI();
            } else {
                selectionPanel.getPredictiveModels().add(get());
                predictiveCounter++;
                selectionPanel.getPredictiveModels().get(predictiveCounter - 1).setSavedPathModel(preferencesPanel.getModelPathLabel().getText().trim());
                selectionPanel.getPredictiveModels().get(predictiveCounter - 1).setId();
                System.out.println(selectionPanel.getPredictiveModels().get(predictiveCounter - 1).getId() + " Predictive id");
                selectionPanel.getPredictiveList().updateUI();
            }

            resultPanel.getLoadingLabel().setVisible(false);
            resultPanel.getAccuracyLbl().setText(String.valueOf(get().getAccuracy()));
            resultPanel.getRecallLbl().setText(String.valueOf(get().getRecall()));
            resultPanel.getF1MeasureLbl().setText(String.valueOf(get().getF1_measure()));
            resultPanel.getAurocLbl().setText(String.valueOf(get().getAuroc()));
            resultPanel.getAuprLbl().setText(String.valueOf(get().getAupr()));
            resultPanel.getPrecisionLbl().setText(String.valueOf(get().getPrecision()));
            resultPanel.getResultPanel().updateUI();
            resultPanel.getResultPanel().repaint();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
