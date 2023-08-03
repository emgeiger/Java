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

    public void keyPressed(KeyEvent ke)
    {

    }

    public void keyReleased(KeyEvent ke)
    {

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
