import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class for packet content that represents acknowledgements
 *
 */
public class ReqPacketContent extends PacketContent {

	String filename;
	int size;

	/**
	 * Constructor that takes in information about a file.
	 * @param filename Initial filename.
	 * @param size Size of filename.
	 */
	ReqPacketContent(String filename, int size) {
		type = REQPACKET;
		this.filename = filename;
		this.size = size;
	}

	/**
	 * Constructs an object out of a datagram packet.
	 * @param packet Packet that contains information about a file.
	 */
	protected ReqPacketContent(ObjectInputStream oin) {
		try {
			type= REQPACKET;
			filename = oin.readUTF();
			size = oin.readInt();
		}
		catch(Exception e) {e.printStackTrace();}
	}

	/**
	 * Writes the content into an ObjectOutputStream
	 *
	 */
	protected void toObjectOutputStream(ObjectOutputStream oout) {
		try {
			oout.writeUTF(filename);
			oout.writeInt(size);
		}
		catch(Exception e) {e.printStackTrace();}
	}



	/**
	 * Returns the content of the packet as String.
	 *
	 * @return Returns the content of the packet as String.
	 */
	public String toString() {
		String returnString = "Request:\n";
		returnString += "Name: " + filename + "\n";
		returnString += "Size: " + size + "\n";
		return returnString;
	}


	/**
	 * Returns the info contained in the packet.
	 *
	 * @return Returns the info contained in the packet.
	 */
	public String getFileName() {
		return filename;
	}

	public int getFileSize() {
		return size;
	}
}
