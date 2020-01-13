package com.insilicokdd.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.insilicokdd.buttons.MainPageBtn;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;



public final class Authenticate extends JFrame implements Runnable {
    private static final String AUTHENTICATION_END_POINT = "http://81.161.246.43:8080/insilicokdd/webresources/py/authentication";
    private static int userId;

    private final JLabel labelUsername = new JLabel("Enter username: ");
    private final JLabel labelPassword = new JLabel("Enter password: ");

    private final JTextField textUsername = new JTextField(20);
    private final JPasswordField fieldPassword = new JPasswordField(20);
    private MainPageBtn tuLbl;
    private MainPageBtn fniLbl;
    private MainPageBtn insilicoLbl;

    private static String token;
    private boolean canLog;

    private JLabel loadingLbl;
    private JLabel labelLoadingProcess;
    private GUI loggedUserView;

    private final JButton btnRegister = getButton(e -> new Register().setVisible(true), "Register");


    private final JButton btnLogin = getButton( e -> {
        String password = fieldPassword.getText();
        String username = textUsername.getText();
        loadingLbl.setVisible(true);
        Login authenticate = new Login(username, password);
        authenticate.execute();

        visibilityOfComponent(btnRegister, labelUsername, labelPassword,
                textUsername, fieldPassword, fniLbl, tuLbl, insilicoLbl, false);
    }, "Login");

    private void visibilityOfComponent(JButton buttonRegister,
                                       JLabel label1, JLabel label2, JTextField txtField, JPasswordField passField, MainPageBtn btn1, MainPageBtn btn2, MainPageBtn btn3, boolean param) {

        buttonRegister.setVisible(param);
        label1.setVisible(param);
        label2.setVisible(param);
        txtField.setVisible(param);
        passField.setVisible(param);
        btn1.setVisible(param);
        btn2.setVisible(param);
        btn3.setVisible(param);
    }

    private JLabel getLoadingLbl() {
        ClassLoader classLoader = getClass().getClassLoader();
        ImageIcon loading = new ImageIcon(Objects.requireNonNull(classLoader.getResource("Gif/loader.gif")).getFile());
        JLabel loadingLbl = new JLabel("Checking Credentials... ", loading, JLabel.CENTER);
        loadingLbl.setVisible(false);
        return loadingLbl;
    }

    @Override
    public void run() {
        final JPanel loginPanel = getLoginPanel();
        loginPanel.setBackground(new Color(255, 255, 255));
        setTitle("Insilico Kdd");
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    class Login extends SwingWorker<String, Void> {
        private String username = "";
        private String password = "";
        Login(String username, String password) {
            btnLogin.setVisible(false);
            this.username = username;
            this.password = password;

        }

        private String login(String username, String password) throws
                RuntimeException, ClientProtocolException, IOException {

            final String credentials = new Credentials(username, password).toString();
            System.out.println(credentials);

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpEntity httpEntity = new StringEntity(credentials, ContentType.APPLICATION_JSON);

            HttpPost httpPost = new HttpPost(AUTHENTICATION_END_POINT);
            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            String token = "";

            try {
                if (response.getStatusLine().getStatusCode() != 200) {
                    canLog = false;
                } else {
                    System.out.println("Output from server ... \n");
                    canLog = true;
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader((response.getEntity().getContent())))) {

                        token = br.readLine();

                        userId = Integer.parseInt(token.substring(token.length() - 1));
                    }
                }

            } finally {
                httpClient.close();
                if (response != null)
                    httpClient.close();
            }
            return token;
        }

        @Override
        protected String doInBackground() throws Exception {
            String user = "";
            try {
                user = login(username, password);
            } catch (RuntimeException | ClientProtocolException e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        public void done() {
            try {
                if (get() == null) return;

                token = get();

                loadingLbl.setVisible(false);
                visibilityOfComponent(btnRegister, labelUsername, labelPassword,
                        textUsername, fieldPassword, fniLbl, tuLbl, insilicoLbl, true);

                btnLogin.setVisible(true);

                if (canLog) {
                    new GUI().setVisible(true);
                    setVisible(false);
                } else {

                }


            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    private JButton getButton(ActionListener actionListener, String text) {
        final JButton btn = new JButton(text);
        btn.setFont(new Font("Verdana", Font.BOLD, 13));
        btn.addActionListener(actionListener);
        return btn;
    }


    private JPanel getLoginPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        final JPanel loginPanel = new JPanel(gridBagLayout);
        loadingLbl = getLoadingLbl();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 4;
        constraints.gridy = 1;
        loginPanel.add(loadingLbl, constraints);
        //  public MainPageBtn(String imgPath, String text, int imgWidth, int imgHeight)

        tuLbl = null;
        fniLbl = null;
        insilicoLbl = null;
        try {
            tuLbl = new MainPageBtn("Images/logo_tu.png",
                     "Tu-Sofia", 90 , 70);
            fniLbl = new MainPageBtn("Images/logo_fni.png",
                    "Fni", 90 , 70);
            insilicoLbl = new MainPageBtn("Images/logo.png",
                    "Insilico Kdd", 90 , 70);
        } catch (IOException e) {
            e.printStackTrace();
        }

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.WEST;
        loginPanel.add(tuLbl, constraints);

        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        loginPanel.add(insilicoLbl, constraints);

        constraints.gridy = 0;
        constraints.gridx = 2;
        constraints.anchor = GridBagConstraints.EAST;
        loginPanel.add(fniLbl, constraints);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 1;
        loginPanel.add(labelUsername, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        loginPanel.add(textUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        loginPanel.add(labelPassword, constraints);

        constraints.gridx = 1;
        loginPanel.add(fieldPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.WEST;
        loginPanel.add(btnLogin, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.EAST;
        loginPanel.add(btnRegister, constraints);

        loginPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Login Panel"));
        add(loginPanel);

        return loginPanel;
    }

    public static String getToken() {
        return token;
    }

    public static int getUserId() {
        return userId;
    }


}