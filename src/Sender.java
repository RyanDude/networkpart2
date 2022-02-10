/** To just mimic ARQ, using socket programming **/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;

/** selective repeat + NACK signal **/
public class Sender {
    // the packet size of link layer is 1500 bytes including the headers and, etc.
    // assume each packet is 1400 bytes, since we need add sequence number
    // the sequence number will be added to the end of the packet
    // size == 1494 bytes, sequence number takes 6 bytes
    private static final String packet = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwx" +
            "yzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz" +
            "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzab" +
            "cdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd" +
            "efghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdef" +
            "ghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefgh" +
            "ijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij" +
            "klmnopqrstuvwxyzabcdefghijklmnopqrt";
    private int window_size = 0;
    private int start = 0;
    private String[] data;
    public Sender(int packet_num){
        // assume the window size is half of the number of packets
        // For selective repeat, the window size is half the maximum sequence number of the frame
        window_size = packet_num / 2;
        data = new String[packet_num];
        for(int i = 0; i < data.length; ++i){
            // add seq number to the end
            data[i] += (packet+i);
            System.err.println(data[i]);
        }
    }
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
                dout.writeUTF("host A");
                String str = (String)in.readUTF();
                System.err.println(str);
                System.err.println(count);
                count++;
                dout.flush();
                dout.close();
            }

            s.close();
        }catch(Exception e){System.out.println(e);}
    }
}
