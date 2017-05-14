import mdc.device.urinoanalisy.DocUReader;
import mdc.gui.JFrame;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MDK
 */
public class ReadLogTest {
    private static DocUReader log;
    private static JFrame fr;
   
    @BeforeClass
    public static void init(){
        fr= new JFrame();
    }
    
    @Test 
    public void testGetPorts(){
        String txt = "21121994";
        
    }
}
