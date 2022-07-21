import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.event.ActionListner;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Drivers extends JFrame implements ActionListner
{
	public Druivers()
	{
		createContents();
		pack();
		setVisible(true);
	}

	public void createContents()
	{
		JTextArea jTextArea = new JTextArea();
		String source = "http://downloads.dell.com/catalog/DriverPackCatalog.cab";
		String ftpSourrce = "ftp://downloads.dell.com/catalog/DriverPackCatalog.cab";
		String altFtpSource = "ftp://ftp.dell.com/catalog/DriverPackCatalog.cab";
		URL url = new URL(source);
//		URL url = new URL(ftpSource);
//		URL url = new URL(altFtpSource);
		URLConnection urlConnection; // = url.openConnection();
//		urlConnection = url.openStream();
		InputStream inputStream = urlConnection.getInputStream();

		downloadUsingNIO(source, "");

		setLayout(new BorderLayout());
		jTextArea.append("");
		add(jTextArea);
	}

	private static void downloadUsingNIO(String urlString, String file)
	{
		URL url = new URL(urlString);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while(count = bis.read(buffer, 0, 1024) != -1)
		{
			fis.write(buffer, 0, count);
		}
		fos.close();
		bis.close();
	}

	private static void downloadUsingSream(String urlString, String file) throws IOException
	{
		URL url = new URL(urlString);
		ReadableByteChannelrbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}

	public static void main(String[] args)
	{
	}
}
