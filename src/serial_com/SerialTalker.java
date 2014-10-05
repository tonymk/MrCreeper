package serial_com;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
//import processing.app.Preferences;


/**
 *
 * @author odroid
 */
public class SerialTalker {
    
    static InputStream input;
    static OutputStream output;
    private boolean isWaitingData;
    
    
    
    
    void run() {
        try {
        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("/dev/ttyACM99");
 
        SerialPort port = (SerialPort)portId.open("serial talk", 4000);
        input = port.getInputStream();
        output = port.getOutputStream();
        port.setSerialPortParams(115200,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        }
        catch(NoSuchPortException | PortInUseException | IOException | UnsupportedCommOperationException e) {
            
        }
        
        while(true){
            
            try {
                receiveData();
                if(!isWaitingData) {
                    sendData();
                }
            } catch (IOException ex) {
                Logger.getLogger(SerialTalker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 
    public static void main(String[] args){
        
        SerialTalker talker = new SerialTalker(); 
        talker.run();
        
    }
    
    private void receiveData() throws IOException {
        if(input.available()>0) {
            while(input.available()>0) {
            System.out.print((char)(input.read()));
            }
            isWaitingData = false;
        }
        
        
    }
    private void sendData() throws IOException {
        isWaitingData = true;
        char test[] = {'R','1','0','0'};
            for(char c : test) {
                output.write(c);
                
            }
    }
    
}
