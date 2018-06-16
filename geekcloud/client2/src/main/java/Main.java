import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

/**
 * Created by evkingen on 28.05.2018.
 */
public class Main {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedOutputStream bufOs = null;
        BufferedReader br = null;
        ObjectEncoderOutputStream oeos = null;
        try {
            socket = new Socket("localhost", 8189);
            FileMessage file = new FileMessage(Paths.get("1.txt"));
            oeos = new ObjectEncoderOutputStream(socket.getOutputStream());
            oeos.writeObject(file);
            oeos.flush();
            /*
            br = new BufferedReader(new InputStreamReader(System.in));
            bufOs = new BufferedOutputStream(socket.getOutputStream());
            String newMessage;
            while((newMessage = br.readLine())!=null) {
                bufOs.write(newMessage.getBytes());
                bufOs.flush();
                if(newMessage.equals("end")) break;
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

