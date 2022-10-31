import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class for packet content that represents file information
 *
 */
public class FileInfoContent extends PacketContent {

	String filename;
	int size;
	byte[] buffer;

	/**
	 * Constructor that takes in information about a file.
	 * @param filename Initial filename.
	 * @param size Size of filename.
	 */
	FileInfoContent(String filename, int size, byte[] buffer) {
		type= FILEINFO;
		this.filename = filename;
		this.size= size;
		this.buffer = buffer;
	}

	/**
	 * Constructs an object out of a datagram packet.
	 * @param packet Packet that contains information about a file.
	 */
	protected FileInfoContent(ObjectInputStream oin) {
		try {
			type= FILEINFO;
			filename= oin.readUTF();
			size= oin.readInt();
			buffer = new byte[size];
			oin.read(buffer);
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
			oout.write(buffer);
		}
		catch(Exception e) {e.printStackTrace();}
	}


	/**
	 * Returns the content of the packet as String.
	 *
	 * @return Returns the content of the packet as String.
	 */
	public String toString() {
		return "Filename: " + filename + " - Size: " + size;
	}

	/**
	 * Returns the file name contained in the packet.
	 *
	 * @return Returns the file name contained in the packet.
	 */
	public String getFileName() {
		return filename;
	}

	/**
	 * Returns the file size contained in the packet.
	 *
	 * @return Returns the file size contained in the packet.
	 */
	public int getFileSize() {
		return size;
	}

	public byte[] getFileBuffer() {
		return buffer;
	}
}
