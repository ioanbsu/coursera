import java.io.File;
import java.util.Arrays;

/**
 * @author IoaN, 2/24/13 10:33 AM
 */
public class TestColinears {

    public static void main(String[] args) throws Exception {
        File folder = new File(ClassLoader.getSystemResource("collinear").getFile());
        for (File file : folder.listFiles()) {
            if (!file.getName().endsWith("rs1423.txt")) {
                continue;
            }
            System.out.println("File: " +file.getName());
            Fast.main(new String[]{"collinear/" + file.getName()});
            System.out.println("=========================");
        }

    }
}
