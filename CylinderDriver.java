/**************************************************************
* CylinderDriver.java
* Dean & Dean
*
* This drives the Cylinder class.
**************************************************************/

import java.util.Scanner;
import javax.swing.*;    // for JFrame and JPanel

public class CylinderDriver extends JFrame
{
  public static void main(String[] args)
  {
    Scanner stdIn = new Scanner(System.in);
    JFrame frame = new JFrame("Three-Dimensional Cylinder");
    Cylinder cylinder;
    double elev;    // cylinder axis elevation angle in degrees
    double azimuth; // cylinder axis azimuth angle in degrees

    frame.setSize(600, 600);
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    System.out.print("Enter axis elevation (-90 to +90): ");
    elev = stdIn.nextDouble();
    System.out.print("Enter axis azimuth (-90 to +90): ");
    azimuth = stdIn.nextDouble();
    cylinder = new Cylinder(elev, azimuth);
    frame.add(cylinder);
    frame.setVisible(true);
  } // end main
} // end CylinderDriver class