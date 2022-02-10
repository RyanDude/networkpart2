import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/** NAT to transfer IP **/
public class Router {
    // the packet sent to router will be 1494 + 6 bytes, including 747 characters of payload and header, 6 bytes for sequence number.
    // drop probability  = (0+X) * 0.001, suppose X = 5
    private static final int probability = 5;
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
    public static void main(String[] args){
        Socket s= null;
        DataInputStream dis=null;
        DataOutputStream out = null;
        int cnt = 0;
        // port 5000
        try{
            ServerSocket ss=new ServerSocket(5000);
            while(cnt < 4){
                s=ss.accept();//establishes connection
                dis=new
                        DataInputStream(s.getInputStream());
                out = new DataOutputStream(s.getOutputStream());
                String  str=(String)dis.readUTF();
                System.out.println("message= "+str);
                out.writeUTF("message from host A"+str);
                out.flush();
            }
            ss.close();
        }catch(Exception e){System.out.println(e);}
    }
}
