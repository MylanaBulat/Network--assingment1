import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Server extends Node {
	static final int DEFAULT_SRC_PORT = 50001;
	static final int CLIENT_DST_PORT = 50000;
	static final int WORKER_DST_PORT = 50002;
	static final String CLIENT_DST_NODE = "client";
	static final String WORKER_DST_NODE = "worker1";
	InetSocketAddress clientDstAddress;
	InetSocketAddress workerDstAddress;
	/*
	 *
	 */
	Server(String workerDstHost, int workerDstPort, String clientDstHost, int clientDstPort, int srcPort) {
		try {
			clientDstAddress = new InetSocketAddress(clientDstHost, clientDstPort);
			workerDstAddress = new InetSocketAddress(workerDstHost, workerDstPort);
			socket = new DatagramSocket(srcPort);
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

			PacketContent content= PacketContent.fromDatagramPacket(packet);

			if (content.getType()==PacketContent.FILENAME) {
				System.out.println("File name: " + ((FileName)content).getFileName());
				System.out.println("Sending to worker ");
				packet.setSocketAddress(workerDstAddress);

				DatagramPacket response;
				response= new AckPacketContent("OK - Received this").toDatagramPacket();
				response.setSocketAddress(packet.getSocketAddress());
				socket.send(response);

				//String fileName = ((FileName)content).getFileName();
				workerDstAddress = new InetSocketAddress(WORKER_DST_NODE, WORKER_DST_PORT);
				packet.setSocketAddress(workerDstAddress);
				socket.send(packet);
				System.out.println("Packet sent to the worker");

			}

			if (content.getType() == PacketContent.FILEINFO) {
				String fileName = ((FileInfoContent)content).getFileName();
				System.out.println("File name: \"" + fileName + "\"");
				System.out.println("File size: " + ((FileInfoContent)content).getFileSize());
				System.out.println("File buffer: " + new String(((FileInfoContent)content).getFileBuffer()));
				//System.out.println("Sending file to Client");
				//packet.setSocketAddress(clientDstAddress);
				//socket.send(packet);

				DatagramPacket response;
				response= new AckPacketContent("OK - Received this").toDatagramPacket();
				response.setSocketAddress(packet.getSocketAddress());
				socket.send(response);

				clientDstAddress = new InetSocketAddress(CLIENT_DST_NODE, CLIENT_DST_PORT);
				packet.setSocketAddress(clientDstAddress);
				socket.send(packet);
				System.out.println("Packet sent to client");
			}
		}
		catch(Exception e) {e.printStackTrace();}
	}


	public synchronized void start() throws Exception {
		System.out.println("Waiting for contact");
		this.wait();
	}

	/*
	 *
	 */
	public static void main(String[] args) {
		try {
			(new Server(WORKER_DST_NODE, WORKER_DST_PORT, CLIENT_DST_NODE, CLIENT_DST_PORT, DEFAULT_SRC_PORT)).start();
			System.out.println("Program completed");
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}

}
