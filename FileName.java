import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class for packet content that represents acknowledgements
 *
 */
public class FileName extends PacketContent {

    String filename;

    /**
     * Constructor that takes in information about a file.
     * @param filename Initial filename.
     * @param size Size of filename.
     */
    FileName(String filename) {
        type = FILENAME;
        this.filename = filename;
    }

    /**
     * Constructs an object out of a datagram packet.
     * @param packet Packet that contains information about a file.
     */
    protected FileName(ObjectInputStream oin) {
        try {
            type= FILENAME;
            filename = oin.readUTF();
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

}
