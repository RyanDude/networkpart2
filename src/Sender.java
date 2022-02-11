/** To just mimic ARQ, using socket programming **/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Timer;

/** selective repeat + NACK signal **/
public class Sender {
    // the packet size of link layer is 1500 bytes including the headers and, etc.
    // assume each packet is 1400 bytes, since we need add sequence number
    // the sequence number will be added to the end of the packet
    // size == 1494 bytes, sequence number will be added to the end of the string,the seq number maybe "999"(6 bytes),so the max packet size is 1500
    private static final String packet = "efghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwx" +
            "yzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz" +
            "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzab" +
            "cdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd" +
            "efghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdef" +
            "ghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefgh" +
            "ijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij" +
            "klmnopqrstuvwxyzabcdefghijklmnopqrt";
    DataOutputStream dout= null;
    DataInputStream in = null;
    Socket s=null;
    int num = 0;
    private int window_size = 0;
    private int start = 0;
    private String[] data;
    public Sender(int packet_num){
        // assume the window size is half of the number of packets
        // For selective repeat, the window size is half the maximum sequence number of the frame
        num = packet_num;
        window_size = packet_num / 2;
        data = new String[packet_num];
        for(int i = 0; i < data.length; ++i){
            // add seq number to the end
            data[i] += (packet+i);
            // System.out.println(data[i]);
        }
    }
    public void communicate() throws Exception{
        // ip & port = "192.168.0.154" & 5000
        int count = 0;
        try{
            while(count < num){
                s=new Socket("localhost",5000);
                dout=new DataOutputStream(s.getOutputStream());
                in = new DataInputStream(s.getInputStream());
                dout.writeUTF(data[count]);

                String str = (String) in.readUTF();

                String NACK = "";
                for(int i = 0; i < str.length() && i < 4; ++i){
                    NACK += str.charAt(i);
                }
                if(NACK.equals("NACK")){
                    // not receive NACK from receiver
                    String t = "";
                    for (int i = 4; i < str.length(); ++i){
                        t += str.charAt(i);
                    }
                    // receive NACK -> retransmit
                    dout.writeUTF(data[Integer.parseInt(t)]);
                    System.out.println("from receiver: "+str);
                }
                dout.flush();
                dout.close();
                count++;
            }
            s.close();
        }catch(Exception e){System.out.println(e);}
    }
    public static void main(String[] args)throws Exception{
        // main thread start after 10 seconds
        //Thread.sleep(10000);
        Sender sender = new Sender(1000);
        // timer to calculate the total time for transmitting N packets
        // it is used to calculate the RTT
        Instant start = Instant.now();
        sender.communicate();
        Instant end = Instant.now();
        long duration = Duration.between(start,end).toMillis();
        System.out.println(duration +" ms");
}
}
