import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;
// hello
public class Gravity extends JFrame {
	public static int width;
	public static int height;

    public Gravity() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	width = (int)(screenSize.getWidth() * 9 / 10);
	height = (int)(screenSize.getHeight() * 9 / 10);
        add(new Simulator(9,width,height,"InverseSquare"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width,height);
        setLocationRelativeTo(null);
        setTitle("Gravity Simulator");
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Gravity();
    }
}

