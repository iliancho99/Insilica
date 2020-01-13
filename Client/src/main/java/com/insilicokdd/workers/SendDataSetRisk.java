package com.insilicokdd.workers;

import com.google.gson.Gson;
import com.insilicokdd.data.Risk;
import com.insilicokdd.machine_learning.ConfigurationPanel;
import com.insilicokdd.operational_mode.BottomPanel;
import com.insilicokdd.operational_mode.SelectionPanel;

import com.insilicokdd.operational_mode.WorkflowPanel;
import com.insilicokdd.risk_mode.RiskPanel;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SendDataSetRisk extends SwingWorker<Risk, Void> {
    private static final String URL_ENDPOINT_RISK = "http://81.161.246.43:8080/insilicokdd/webresources/py/risk/";
    private File dataSetFile;
    private List<String> dataSetheaders;
    private JLabel jLabel;
    private String configChoosed;
    private SelectionPanel selectionPanel;
    private List<Risk> models;
    private ConfigurationPanel preferencesPanel;
    private WorkflowPanel workflowPanel;
    private String dataSetHeadersShow;
    private JButton executeBtn;
    private String queryParam;
    private RiskPanel riskPanel;
    private BottomPanel bottomPanel;


    public SendDataSetRisk (RiskPanel riskPanel, SelectionPanel selectionPanel, String configChoosed,
                            WorkflowPanel workflowPanel, JButton executeBtn, com.insilicokdd.operational_mode.BottomPanel bottomPanel) {
        this.dataSetFile = dataSetFile;
        dataSetheaders = new ArrayList<>();
        jLabel = new JLabel();

        this.selectionPanel = selectionPanel;
        models = new ArrayList<>();
        this.configChoosed = configChoosed;
        this.workflowPanel = workflowPanel;
        this.executeBtn = executeBtn;
        this.queryParam = queryParam;
        this.riskPanel = riskPanel;
        this.bottomPanel = bottomPanel;
    }

    @Override
    protected Risk doInBackground() throws Exception {
        Risk result = null;
        CloseableHttpClient httpClient = null;
        try {

            String query = new StringBuilder(URL_ENDPOINT_RISK).append(riskPanel.getQuery()).toString();
            System.out.println(query + " query in http request ");

            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(query);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);

                if (response.getStatusLine().getStatusCode() != 200) {
                    riskPanel.getResultLbl().setText("There was error in getting result");
                    executeBtn.setEnabled(true);
                    throw new RuntimeException(
                            "Failed : Http error code " + response.getStatusLine().getStatusCode());

                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            result = new Gson().fromJson(br.readLine(), Risk.class);
            System.out.println("Response from server: \n");

            System.out.println(br.readLine());
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            if (httpClient != null)
                httpClient.close();
        }

        return result;
    }


    @Override
    protected void done() {
        try {
            executeBtn.setEnabled(true);
            System.out.println(executeBtn.isDefaultCapable());

            riskPanel.getResultLbl().setText(String.valueOf(get().getResult()));

            riskPanel.getRiskPanel().updateUI();
            riskPanel.getRiskPanel().repaint();

            bottomPanel.setRisk(String.valueOf(get().getResult()));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
