package com.insilicokdd.gui;

import javax.swing.*;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.insilicokdd.data.MemberView;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class Register extends JFrame {
    private static final long serialVersionUID = 1L;

    private final String[] bulgarianCities = { "Varna", "Bourgas", "Sofia", "Plovdiv", "Montana", "Vraca", "Haskovo", "Veliko Turnovo",  };

    private final Container container;
    private final JTextField tName;
    private final JRadioButton male;
    private final JRadioButton female;
    private final  ButtonGroup gengp;


    private JLabel mno;
    private JTextField tfMobile;

    private JComboBox cbCountry;
    private JComboBox cbCity;
    private JComboBox cbYear;

    private JTextArea tadd;
    private JTextArea taResult;

    private JCheckBox term;
    private JButton sub;
    private JButton reset;

    private JLabel res;
    private JLabel passwordNotifyLbl;
    private JTextArea resadd;
    private JTextField tUsername;
    private JPasswordField tPassword;

    private JTextField tCountry;
    private JTextField tCity;
    private JTextField tPostalCode;
    private JTextField tGender;
    private JTextField tMobile;

    private MemberView member;

    Register() {
        setTitle("Registration Form");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        container = getContentPane();
        container.setLayout(null);

        FComponent.getJLabel(this, "Registration Form", 30, 300, 30, 300, 30);
        FComponent.getJLabel(this, "Name", 15, 100, 20, 100, 100);
        FComponent.getJLabel(this, "Mobile", 15, 100, 20, 100, 150);
        FComponent.getJLabel(this, "Gender", 15, 100, 20, 100, 200);
        FComponent.getJLabel(this, "Country", 15, 100, 20, 100, 250);
        FComponent.getJLabel(this, "City", 15, 100, 20, 100, 300);
        FComponent.getJLabel(this, "Postal code", 15, 100, 20, 100, 350);
        FComponent.getJLabel(this, "Username", 15, 100, 20, 100, 400);
        FComponent.getJLabel(this, "Password", 15, 100, 20, 100, 450);

        resadd = FComponent.getJTextArea(this, 15, 200, 75, 580, 175);
        cbCity = FComponent.getJComboBox(this, bulgarianCities, 15, 190, 20, 200, 250);
        cbCountry = FComponent.getJComboBox(this, new String[] { "Bulgaria" }, 15, 190, 20, 200, 300);
        tMobile = FComponent.getJTextField(this, 15, 190, 20, 200, 150);
        tPostalCode = FComponent.getJTextField(this, 15, 190, 20, 200, 350);
        tUsername = FComponent.getJTextField(this, 15, 190, 20, 200, 400);
        tPassword = FComponent.getJTextPassword(this, 15, 190, 20, 200, 450);

        taResult = FComponent.getJTextArea(this, 15, 300, 400, 500, 100);
        taResult.setEditable(false);

        male = FComponent.getJRadionButton(this, "Male", 15, 75, 20, 200, 200, true);
        female = FComponent.getJRadionButton(this, "Female", 15, 80, 20, 275, 200, false);
        tName = FComponent.getJTextField(this, 15, 190, 20, 200, 100);

        passwordNotifyLbl = FComponent.getJLabel(this, "", 15, 200, 20, 300, 500);

        gengp = FComponent.getButtonGroup(male, female);

        term = new JCheckBox("Accept Terms And Conditions.");
        term.setFont(new Font("Arial", Font.PLAIN, 15));
        term.setSize(250, 20);
        term.setLocation(550, 510);
        container.add(term);

        sub = FComponent.getJButton(this, "Submit", 15, 100, 20, 530, 540,
                e -> {

                    String gender = "";
                    if (male.isSelected())
                        gender = "MALE";
                    else
                        gender = "FEMALE";

                    if (SecureUtils.is_Valid_Password(tPassword.getText())) {
                        passwordNotifyLbl.setText("");
                        member = new MemberView(gender, tName.getText(), tMobile.getText(),
                                (String) cbCountry.getSelectedItem(), (String) cbCity.getSelectedItem(), tPostalCode.getText(),
                                tUsername.getText(), tPassword.getText(), "ACTIVE");
                        String jsonDate = member.toString();
                        System.out.println("Fire fire");
                        new PostForm(jsonDate).execute();
                    } else {
                        passwordNotifyLbl.setText("Please enter a valid password");
                    }

                });

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);

        container.add(res);

        setVisible(true);
    }

    private class PostForm extends SwingWorker<MemberView, Void> {
        private String jsonDate = "";
        private final String URL = "http://81.161.246.43:8080/insilicokdd/webresources/py/member";

        PostForm(final String jsonData) {
            this.jsonDate = jsonData;
        }

        private void createMember() throws UnsupportedOperationException, IOException {

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpEntity httpEntity = new StringEntity(jsonDate, ContentType.APPLICATION_JSON);

            HttpPost httpPost = new HttpPost(URL);
            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            try {

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException(
                            "Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
                System.out.println("Output from server ... \n");
                System.out.println(br.readLine());

            } finally {
                if (httpClient != null)
                    httpClient.close();
                if (response != null)
                    httpClient.close();
            }
        }

        @Override
        protected MemberView doInBackground() {
            try {
                createMember();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}