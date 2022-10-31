import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.Arrays;
public class Worker extends Node {

    static final int DEFAULT_PORT = 50002;
    static final int SERVER_PORT = 50001;
    static final String SERVER_NODE = "server";
    static int workerNumber = 0;
    InetSocketAddress dstAddress;
    /*
     *
     */
    Worker(int port) {
        try {
            socket = new DatagramSocket(DEFAULT_PORT);
            listener.go();
        }
        catch(java.lang.Exception e) {e.printStackTrace();}
    }

    /**
     * Assume that incoming packets contain a String and print the string.
     */
    public void onReceipt(DatagramPacket packet) {
        try {
            System.out.println("Received packet");

            PacketContent content = PacketContent.fromDatagramPacket(packet);
            if (content.getType() == PacketContent.FILENAME) {

                //System.out.println("");
                DatagramPacket response;
                response= new AckPacketContent("OK - Received this").toDatagramPacket();
                response.setSocketAddress(packet.getSocketAddress());
                socket.send(response);

                String fname = ((FileName)content).getFileName();
                File file= null;
                FileInputStream fin= null;
                FileInfoContent fcontent;

                int size;
                byte[] buffer= null;
                DatagramPacket returnPacket = null;

                file= new File(fname);				// Reserve buffer for length of file and read file
                buffer= new byte[(int) file.length()];
                fin= new FileInputStream(file);
                size= fin.read(buffer);
                if (size==-1) {
                    fin.close();
                    throw new Exception("Problem with File Access:"+fname);
                }
                System.out.println("File size: " + buffer.length);

                fcontent= new FileInfoContent(fname, size, buffer);
                //content = new ReqPacketContent(fname, size);

                dstAddress = new InetSocketAddress(SERVER_NODE, SERVER_PORT);
                System.out.println("Sending packet to the server"); // Send packet with file name and length
                returnPacket= fcontent.toDatagramPacket();
                returnPacket.setSocketAddress(dstAddress);
                socket.send(returnPacket);
                System.out.println("Packet sent");
                //this.wait();
                fin.close();

                /**String fileName = getFileName(content);
                File file = new File(fileName);
                byte[] buffer = new byte[(int) file.length()];
                FileInputStream fin = new FileInputStream(file);
                int size = fin.read(buffer);
                if (size == -1) {
                    fin.close();
                    throw new Exception("Problem with File Access:" + fileName);
                }
                System.out.println("File size: " + buffer.length);
                FileInfoContent fileContent;
                fileContent = new FileInfoContent(fileName, size, buffer);

                System.out.println("Sending packet w/ name & length");
                DatagramPacket response;
                response = fileContent.toDatagramPacket();
                response.setSocketAddress(packet.getSocketAddress());
                System.out.println("Sending file to Ingress");
                socket.send(response);*/
            }
        }
        catch(Exception e) {e.printStackTrace();}
    }

    /**private String getFileName(PacketContent content){
        String fileName = content.toString();
        //fileName = fileName.substring(9);
        System.out.println("file name: \"" + fileName + "\"");
        return fileName;
    }*/

    public synchronized void start() throws Exception {
        this.wait();
    }

    public static void startMessage(){
        System.out.println("Waiting for contact");
    }

    public static void endMessage(){
        System.out.println("Program completed");
    }

    public static void main(String[] args) {
        try {
            startMessage();
            (new Worker(DEFAULT_PORT)).start();
            endMessage();
        } catch(java.lang.Exception e) {e.printStackTrace();}
    }
}
