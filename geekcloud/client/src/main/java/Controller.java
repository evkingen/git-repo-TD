
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Controller {
    private ClientConnection clientConnection;
    public static class ClientConnection {
        private Socket socket;
        private ObjectEncoderOutputStream oeos;
        private ObjectDecoderInputStream odis;
        private boolean isAuthorize;
        private String nickname;
        private Thread listener;

        public void setListener(Thread listener) {
            this.listener = listener;
        }

        public Thread getListener() {
            return listener;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Socket getSocket() {
            return socket;
        }

        public ObjectDecoderInputStream getOdis() {
            return odis;
        }

        public ObjectEncoderOutputStream getOeos() {
            return oeos;
        }

        public void setAuthorize(boolean authorize) {
            isAuthorize = authorize;
        }

        public boolean isAuthorize() {
            return isAuthorize;
        }

        public void initConnection() {
            try{
                if (socket==null || socket.isClosed()) {
                    this.socket = new Socket("localhost", 8189);
                    this.oeos = new ObjectEncoderOutputStream(socket.getOutputStream());
                    this.odis = new ObjectDecoderInputStream(socket.getInputStream());
                    this.isAuthorize = false;
                }
            }catch(Exception e){
                Alert warningMsgbox = new Alert(Alert.AlertType.WARNING);
                warningMsgbox.setHeaderText("Connection failed");
                warningMsgbox.showAndWait();
                e.printStackTrace();
            }
        };
    }
    @FXML
    private ProgressBar operationProgress;
    @FXML
    private HBox authPanel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;
    @FXML
    private HBox actionPanel1;
    @FXML
    private HBox actionPanel2;
    @FXML
    private ListView<String> localList;
    @FXML
    private ListView<String> cloudList;
    @FXML
    public void tryToRegistration() {
        Alert msgbox = new Alert(Alert.AlertType.WARNING);
        Object answer;
        String login = loginField.getText();
        String pass = passField.getText();
        String reg = "/reg" + " " + login + " " + pass;
        if(login.equals("") || pass.equals("")) {
            msgbox.setTitle("Attention");
            msgbox.setHeaderText(null);
            msgbox.setContentText("Please, input login and password!");
            msgbox.showAndWait();
        }else {
            if (clientConnection == null) clientConnection = new ClientConnection();
            clientConnection.initConnection();
            try {

                clientConnection.getOeos().writeObject(new CommandMessage(reg));
                listnerAnswer();
                /*
                answer = clientConnection.getOdis().readObject();
                String[] msg = ((String) answer).split(" ", 2);
                msgbox.setAlertType(Alert.AlertType.INFORMATION);
                msgbox.setTitle("Information");
                msgbox.setHeaderText(null);
                msgbox.setContentText(msg[1]);
                msgbox.showAndWait();
                if(msg[0].equals("/authok")) {
                    answer = clientConnection.getOdis().readObject();

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void tryToAuthorize() {
        Alert msgbox = new Alert(Alert.AlertType.WARNING);
        Object answer;
        String login = loginField.getText();
        String pass = passField.getText();
        String auth = "/auth" + " " + login + " " + pass;
        if(login.equals("") || pass.equals("")) {
            msgbox.setTitle("Attention");
            msgbox.setHeaderText(null);
            msgbox.setContentText("Please, input login and password!");
            msgbox.showAndWait();
        }else {
            if (clientConnection == null) clientConnection = new ClientConnection();
            clientConnection.initConnection();
            try {

                clientConnection.getOeos().writeObject(new CommandMessage(auth));
                listnerAnswer();
                /*
                answer = clientConnection.getOdis().readObject();
                String[] msg = ((String) answer).split(" ", 2);
                msgbox.setAlertType(Alert.AlertType.INFORMATION);
                msgbox.setTitle("Information");
                msgbox.setHeaderText(null);
                msgbox.setContentText(msg[1]);
                msgbox.showAndWait();
                if(msg[0].equals("/authok")) {
                    answer = clientConnection.getOdis().readObject();

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void listnerAnswer() {
        if(clientConnection.getListener()==null) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Object answer;

                    while (clientConnection.getSocket() != null && !clientConnection.getSocket().isClosed()) {
                        try {
                            answer = clientConnection.getOdis().readObject();
                            readObjectHandler(clientConnection, answer, answer.getClass().getSimpleName());
                            //
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            clientConnection.setListener(t);
            t.setDaemon(true);
            t.start();
        }
    }

    public void readObjectHandler(ClientConnection clientConnection, Object obj, String className) {
        System.out.println(className);
        switch(className) {
            case "CommandMessage":
                CommandMessage command = (CommandMessage)obj;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert msgbox = new Alert(Alert.AlertType.WARNING);
                        msgbox.setAlertType(Alert.AlertType.INFORMATION);
                        msgbox.setTitle("Information");
                        msgbox.setHeaderText(null);
                        msgbox.setContentText(Arrays.toString(((CommandMessage) obj).getArguments()));
                        msgbox.showAndWait();
                    }
                });
                if(command.getCommand().equals("/authok")) {
                    clientConnection.setAuthorize(true);
                    operationProgress.setVisible(true);
                    authPanel.setVisible(false);
                    actionPanel1.setManaged(true);
                    actionPanel1.setVisible(true);
                    actionPanel2.setVisible(true);
                    actionPanel2.setManaged(true);
                    clientConnection.setNickname(((CommandMessage) obj).getArguments()[0]);
                }
                break;
            case "FileMessage":
                FileMessage file = (FileMessage) obj;
                String owner = file.getOwner();
                String fileName = file.getName();
                byte[] content = file.getContent();

                System.out.println("Owner:" + owner);
                System.out.println("FileName: " + fileName);
                System.out.println("Content:" + (new String(content)));

                InputStream is = new BufferedInputStream(new ByteArrayInputStream(content));
                Path newFile = Paths.get(".\\client\\myDir\\" + fileName);
                try {
                    if(!Files.exists(newFile)) Files.createFile(newFile);
                    Files.copy(is, newFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "FilesListMessage":
                FilesListMessage pathList = (FilesListMessage) obj;


                ObservableList<String> cloudItems = FXCollections.observableArrayList(pathList.getFilesList());

                FilesListMessage localFiles = new FilesListMessage();
                localFiles.initMessage(Paths.get(".\\client\\myDir"));
                ObservableList<String> items = FXCollections.observableArrayList(localFiles.getFilesList());

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        cloudList.setItems(cloudItems);
                        localList.setItems(items);
                    }
                });
                break;
            default:
                break;
        }
    }
@FXML
    public void sendFileOnServer() {
        try {

            Path path = Paths.get(".\\client\\myDir\\" + localList.getSelectionModel().getSelectedItem());

            FileMessage file = new FileMessage(path,clientConnection.getNickname());
            clientConnection.getOeos().writeObject(file);
        } catch (IOException e) {
          e.printStackTrace();
       }
    }
    @FXML
    public void updateFileList() {
        String nickname = clientConnection.getNickname();
        if (nickname != null && !nickname.equals("")) {
            String command = "/getfileslist " + nickname;
            try {
                clientConnection.getOeos().writeObject(new CommandMessage(command));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void downloadFile() {
        String nickname = clientConnection.getNickname();
        if (nickname != null && !nickname.equals("")) {
            String command = "/getfile " + nickname + "\\" +cloudList.getSelectionModel().getSelectedItem();
            try {
                clientConnection.getOeos().writeObject(new CommandMessage(command));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void deleteFile() {
        String nickname = clientConnection.getNickname();
        if (nickname != null && !nickname.equals("")) {
            String command = "/deletefile " + nickname + "\\" + cloudList.getSelectionModel().getSelectedItem();
            try {
                clientConnection.getOeos().writeObject(new CommandMessage(command));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void localDeleteFile() {
        String fileName = localList.getSelectionModel().getSelectedItem();
        String fullPath = ".\\client\\myDir\\" + fileName;
        Path filePath = Paths.get(fullPath);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void localUpdateFileList() {
        FilesListMessage localFiles = new FilesListMessage();
        localFiles.initMessage(Paths.get(".\\client\\myDir"));
        ObservableList<String> items = FXCollections.observableArrayList(localFiles.getFilesList());
        localList.setItems(items);
    }
}

