import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class Receiver {
    // drop probability  = (0+X) * 0.001, suppose X = 5
    private static final int probability = 5;
    Socket s= null;
    DataInputStream dis=null;
    DataOutputStream out = null;
    private int num = 0;
    private int window_size = 0;
    public Receiver(int num){
        this.num = num;
        window_size = num / 2;
    }
    // packet drop/lost/corrupted probability is 0.5%
    public boolean packet_drop() {
        return Math.random()*1000 <= probability;
    }
    public void communicate(){
        int pre = 0;
        int current = 0;
        int cnt = 0;
        // port 5000
        try{
            ServerSocket ss=new ServerSocket(5000);

            while(cnt < num + 5){
                Socket s=ss.accept();//establishes connection
                DataInputStream dis=new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                String  str=(String)dis.readUTF();
                // sequence number
                String sq = "";
                for(int i = 747; i < str.length(); ++i){
                    sq += str.charAt(i);
                }
                int seq = Integer.parseInt(sq);
                if(packet_drop()){
                    out.writeUTF("NACK" + seq);
                }else{
                    out.writeUTF("");
                    System.out.println("from sender : "+str + ", seq = "+ str.charAt(747));
                    cnt++;
                    continue;
                }
                out.flush();
                System.err.println(cnt);
            }
            // System.err.println("total packet = " + cnt);
            ss.close();
        }catch(Exception e){System.out.println(e);}
    }
    public static void main(String[] args){
        Receiver receiver = new Receiver(1000);
        System.err.println();
        receiver.communicate();


    }
}
