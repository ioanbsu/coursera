import java.io.File;

/**
 * @author IoaN, 3/10/13 3:56 PM
 */
public class TestSolver {

    public static void main(String[] args) throws Exception {
        File folder = new File(ClassLoader.getSystemResource("8puzzle").getFile());
        for (File file : folder.listFiles()) {
            if (!file.getName().endsWith("puzzle17.txt")) {
                continue;
            }
            System.out.println("================" + file.getName() + "================");
            Solver.main(new String[]{"8puzzle/" + file.getName()});
        }

    }
}
