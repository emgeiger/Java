import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.util.*;

public class Thermal extends JFrame
{
	private static double[] pointTemp = new double[] {3, 7, 11, 15, 19, 23, 27, 31, 35, 39, 43, 47, 51, 55, 59,
													 63, 67, 71, 75, 79, 83, 87, 91, 95, 99, 103, 107, 111, 115, 119,
													123, 127, 131, 135, 139, 143, 147, 151, 155, 169, 163, 167};
						  //pointTemp = new double[42];

//************************************************************************************************************************

	public Thermal()
	{
		for (int i=0; i < 42; i++)
		{
			pointTemp[i] = 58;
		}
		setTitle("Diffusion from Thermal Well");
		setSize(800, 700);
		createContents();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

//******************************************************************************************

	private void createContents()
	{
		double[] wellTemp = new double[] {30, 40, 50, 60, 70, 80, 90};
		int[] wellTemps = new[] {30, 40, 50, 60, 70, 80, 90};
		//wellsTemp, if needed, for another experiment, for testing
		String temp = null;

		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel westPanel = new JPanel(new GridLayout(0, 1));
		JPanel centerPanel = new JPanel(new GridLayout(0, 42));
		JPanel southPanel = new JPanel(new GridLayout(0, 44));

		Label label = new Label("wellTemp");
		northPanel.add(label);
		label = new Label("degrees F color code:");
		northPanel.add(label);
		for(int i = 0; i < wellTemp.length; i++)
		{
			temp = String.format("%d", (int)wellTemp[i]);
			// temp = String.format("%d", wellTemps[i]);
			// wellsTemp, for whatever it may need to be needed for.
			label = new Label(temp);
			label.setForeground(color(wellTemp[i]));
			northPanel.add(label);
		}

		String[] shortMonths = new DateFormatSymbols().getShortMonths();
		for (int j = 0; j < 12; j++)
		{
			String shortMonth = shortMonths[j];
			westPanel.add(new JLabel(shortMonth));
		}

		for (int i=0; i<=60; i++)
		{
			for (int in=0; in <= 42; in++)
			{
				add(centerPanel);
			} // end for loop

		add(northPanel, BorderLayout.NORTH);
		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		} // end for loop
	} // end createContents

//********************************************************************************************

	public double load()
	{
		double heatFlow;
		double hour = 0;
		heatFlow = -4000 - 28000 * Math.cos((2 * Math.PI) * hour / (360 * 24))
		  + 8000 * Math.cos((2 * Math.PI) * hour / (10 * 24)) + 12000 * Math.sin((2 * Math.PI) * (hour - 10) /  24 );
		  heatFlow += 0.04 * Math.abs(heatFlow);
		  heatFlow += -1000;

		  if (heatFlow < -38000)
		  {
			  heatFlow = -38000;
		  }

		  heatFlow += 0.18 * Math.abs(heatFlow);
		  heatFlow += 0.04 * Math.abs(heatFlow);

		  return heatFlow;
	}

//********************************************************************************************

	public double diffuse(double heatFlow)
	{
		double deltaT = 0;
		double depth = 900.0;                 // feet
		double conductivity = 0.83;           // BTU/Hr/ft/degF
		double density = 140;                 // lb/cuft
		double specificHeat = 0.19;           // BTU/lb/degF
		double diffusion =
		  conductivity / (density * specificHeat);  // sqft/hour
		double wellRadius = 0.25;                   // ft
		double deltaX = 0.33;                       // ft
		double factor = diffusion * deltaT / (deltaX * deltaX);

		heatFlow = pointTemp[0];
		pointTemp[0] = pointTemp[1] + heatFlow /
		  ((2 * Math.PI) * depth * conductivity * Math.log(1 + deltaX / wellRadius));

		for (int j=1; j < 41 && j <= 40; j++)
		{
			pointTemp[j] = pointTemp[j] + factor *
			  ((1 - 0.5/j) * pointTemp[j-1]- 2.0 * pointTemp[j] +
			  (1 + 0.5 /j) * pointTemp[j+1]);

			  if (j == pointTemp[39])
			  {
				  pointTemp[41] = pointTemp[40];
			  }
		}
		return heatFlow;
	} // end diffuse
//********************************************************************************************
	public void display(double periodLength, int periodNumber, double avgPointTemp[])
	{
		// System.out.println("time= " + (int) periodLength + "\t" + avgPointTemp[periodNumber]);
	}
//*************************************************************************************************
	public void display(double periodNum, double avgPointTemp[])
	{
		for (int i=0; i < 42; i++)
		{
			JLabel jl = new JLabel();
			jl.setText(Double.toString(pointTemp[0]));
//			jl.setBackGroundColor();
		}
	} // end display
//************************************************************************
	private Color color(double degF)
	{
		// double degF = 0;
		double red = (degF - 60) / 30;
		double green = 1.0 - Math.abs(degF - 60) / 30;
		double blue =  (90 - degF) / 30;

		if (red > 1.0)
		{
			red = 1.0;
		}
		else if (red < 0.0)
		{
			red = 0.0;
		}
		
		if (green > 1.0)
		{
			green = 1.0;
		}
		else if (green < 0.0)
		{
			green = 0.0;
		}
		
		if (blue > 1.0)
		{
			blue = 1.0;
		}
		else if  (blue < 0.0)
		{
			blue = 0.0;
		}

		// Color color = 
		return new Color((int) red, (int) green, (int) blue);
	} // end color
//************************************************************************
private Color color(int degF)
{
	// double degF = 0;
	double red = (degF - 60) / 30;
	double green = 1.0 - Math.abs(degF - 60) / 30;
	double blue =  (90 - degF) / 30;

	if (red > 1.0)
	{
		red = 1.0;
	}
	else if (red < 0.0)
	{
		red = 0.0;
	}
	
	if (green > 1.0)
	{
		green = 1.0;
	}
	else if (green < 0.0)
	{
		green = 0.0;
	}
	
	if (blue > 1.0)
	{
		blue = 1.0;
	}
	else if  (blue < 0.0)
	{
		blue = 0.0;
	}

	// Color color = 
	return new Color((int) red, (int) green, (int) blue);
} // end color
//*************************************************************************************************
private void color(JLabel label, int degF) {
    float red = (degF - 60f) / 30f;
    float green = 1.0f - Math.abs(degF - 60f) / 30f;
    float blue = (90f - degF) / 30f;
    red = Math.min(1.0f, Math.max(0.0f, red));
    green = Math.min(1.0f, Math.max(0.0f, green));
    blue = Math.min(1.0f, Math.max(0.0f, blue));
    Color color = new Color(red, green, blue);
    label.setForeground(color);
}
//*************************************************************************************************
	public static void main(String[] args)
	{
		Thermal thermal = new Thermal();
		double heatFlow = 0;
		double computedTemperature = 0;
		double time = 0;
		double[] avgPointTemp = new double[pointTemp.length];// = new double[42];
		for(int i=1; i <= 60; i++)
		{
			for (int l=0; l < 42; l++)
			{
//				avgPointTemp[l] = pointTemp[l] += pointTemp[l] / pointTemp.length;
				// this.
				avgPointTemp[l] = 0;
			} // end for

			for (int in=0; in < 144; in++)
			{
				time++;
				computedTemperature = thermal.load();
				thermal.diffuse(computedTemperature);
				for (int l=0; l < 42; l++)
				{
					// this.
					avgPointTemp[l] += computedTemperature / 144;
					thermal.display(time, l, avgPointTemp);
				}
//				computedTemperature /= 144;
			} // end for
		} // end for
	} // end main
} // end class
