import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Receiver {
    // receive the packets from router
    public static void main(String[] args){
        DataOutputStream dout= null;
        DataInputStream in = null;
        Socket s=null;
        int count = 0;
        //需要服务器的正确的IP地址和端口号 "192.168.0.145", 5000
        try{
            while (count < 4){
                s=new Socket("localhost",5000);
                dout=new DataOutputStream(s.getOutputStream());
                in = new DataInputStream(s.getInputStream());
                dout.writeUTF("host B");
                String str = (String)in.readUTF();
                System.err.println(str);
                System.err.println(count);
                count++;
                dout.flush();

            }
            dout.close();
            s.close();
        }catch(Exception e){System.out.println(e);}
    }
}
