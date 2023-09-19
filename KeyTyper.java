
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class KeyTyper implements KeyListener
{
	public KeyTyper()
	{
		createContents();
	}

	public void createContents()
	{
		addKeyListener(this);
	}

public class KeyLogger extends JFrame implements KeyListener {
    public KeyLogger() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        pack();
        setVisible(true);
    }

    private void createContents() {
        addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyChar());
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("Key released: " + e.getKeyChar());
    }

    public void keyTyped(KeyEvent e) {
        System.out.println("Key typed: " + e.getKeyChar());
    }

    public static void main(String[] args) {
        new KeyLogger();
    }
}

    public void keyTyped(KeyEvent ke)
    {
        System.out.println(ke.getKeyCode());
    }

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        new KeyTyper();
        scan.close();
    }
}
