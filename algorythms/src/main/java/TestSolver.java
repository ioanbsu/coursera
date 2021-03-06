import java.io.File;

/**
 * @author IoaN, 3/10/13 3:56 PM
 */
public class TestSolver {

    public static void main(String[] args) throws Exception {
        File folder = new File(ClassLoader.getSystemResource("8puzzle").getFile());
        for (File file : folder.listFiles()) {
            if (!file.getName().endsWith(".txt")) {
                continue;
            }
            System.out.println("================" + file.getName() + "================");
            Stopwatch stopwatch=new Stopwatch();
            Solver.main(new String[]{"8puzzle/" + file.getName()});
            System.out.println(stopwatch.elapsedTime());
        }

    }
}
