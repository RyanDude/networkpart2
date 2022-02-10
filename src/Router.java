import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/** NAT to transfer IP **/
public class Router {
    // the packet sent to router will be 1494 + 6 bytes, including 747 characters of payload and header, 6 bytes for sequence number.
    // drop probability  = (0+X) * 0.001, suppose X = 5
    private static final int probability = 5;
    Socket s= null;
    DataInputStream dis=null;
    DataOutputStream out = null;
    // NAT table
    // NAT table structure
    /**
     * private ip & port | public ip & port
     * format: String "ip:port"
     **/
    private Map<String, String> map = new HashMap<>();
    public Router(){
    }
    // packet drop/lost/corrupted probability is 0.5%
    public boolean packet_drop() {
        return Math.random()*1000 <= probability;
    }
    public void communicate(){
        int cnt = 0;
        // port 5000
        try{
            ServerSocket ss=new ServerSocket(5000);

            while(cnt < 5){
                Socket s=ss.accept();//establishes connection
                DataInputStream dis=new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                String  str=(String)dis.readUTF();
                out.writeUTF("ACK");
                System.out.println("from sender "+str);
                cnt++;
            }
            // ss.close();
        }catch(Exception e){System.out.println(e);}
    }
    public static void main(String[] args){
        Router router = new Router();
        router.communicate();


}
}
