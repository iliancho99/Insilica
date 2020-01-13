package com.insilicokdd.workers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.insilicokdd.data.Diagnosis;
import com.insilicokdd.operational_mode.BottomPanel;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

public class SendDataSetOp extends SwingWorker<List<Diagnosis>, Void> {
    private BottomPanel bottomPanel;
    private File dataSetFile;
    private JButton analyzeBtn;
    private JButton clearBtn;

    public SendDataSetOp(BottomPanel bottomPanel, File dataSetFile, JButton analyzeBtn, JButton clearBtn) {
        this.bottomPanel = bottomPanel;
        this.dataSetFile = dataSetFile;
        this.analyzeBtn = analyzeBtn;
        this.clearBtn = clearBtn;
    }

    @Override
    protected List<Diagnosis> doInBackground() throws NullPointerException, IOException {
        List<Diagnosis> diagnosisList = null;
        CloseableHttpClient httpClient = null;
        try {
            StringBuilder url = new StringBuilder();
            System.out.println(bottomPanel.getAlgorithmChoosed() + "URL");

            url.append("http://81.161.246.43:8080/insilicokdd/webresources/py/");
            url.append(bottomPanel.getAlgorithmChoosed());

            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url.toString());

            FileBody bin = new FileBody(dataSetFile);
            System.out.println(url + " URL");
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("bin", bin)
                    .build();

            httpPost.setEntity(reqEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                if (response.getStatusLine().getStatusCode() != 200) {
                    analyzeBtn.setEnabled(true);
                    clearBtn.setEnabled(true);
                    throw new RuntimeException("Failed : Http error code " +
                            response.getStatusLine().getStatusCode());
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            System.out.println("Response from server: \n");

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            diagnosisList = new Gson().fromJson(br.readLine(), new TypeToken<ArrayList<Diagnosis>>() {
            }.getType());

            System.out.println(diagnosisList);
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            if (httpClient != null)
                httpClient.close();
        }
        if (diagnosisList == null) throw new NullPointerException("Diagnosis list cannot be null");
        return diagnosisList;
    }

    @Override
    protected void done() {
        String diagnosis = "";
        String outcome = "";
        try {
            for (int i = 0; i < get().size(); i++) {
                if (get().get(i).getDiagnosis() != null) {
                    if (get().get(i).getDiagnosis() == 1.0) {
                        diagnosis = "Malignant";
                        bottomPanel.getDiagnosis().setText(diagnosis);
                    } else {
                        diagnosis = "Benign";
                        bottomPanel.getDiagnosis().setText(diagnosis);
                    }
                }
            }
            for (int i = 0; i < get().size(); i++) {
                if (get().get(i).getOutcome() != null) {
                    if (get().get(i).getOutcome() == 1.0) {
                        outcome = "Recurrent";
                        bottomPanel.getOutcome().setText(outcome);
                    } else {
                        outcome = "Non-reccurent";
                        bottomPanel.getOutcome().setText(outcome);
                    }
                }
            }
            bottomPanel.showResult(diagnosis, outcome);
            analyzeBtn.setEnabled(true);
            clearBtn.setEnabled(true);

            System.out.println(get() + " DONE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
