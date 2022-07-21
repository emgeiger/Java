import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class VerticalFlowLayout implements LayoutManager, java.io.Serializable
{
    public static final int TOP = 0;

    public static final int CENTER = 1;

    public static final int BOTTOM = 2;

    int align; // this the one, that, we actually use

    int hgap;

    int vgap;

    public VerticalFlowLayout()
    {
        this(CENTER, 5, 5);
    }

    public VerticalFlowLayout(int align)
    {
        this(align, 5, 5);
    }

    public VerticalFlowLayout(int align, int hgap, int vgap)
    {
        this.hgap = hgap;
        this.vgap = vgap;
        setAlignment(align);
    }

    public int getAlignment()
    {
        return align;
    }

    /**
     * Sets the alignment for this layout. Possible values are
     * <ul>
     * <li><code>VerticalFlowLayout.TOP</code>
     * <li><code>VerticalFlowLayout.BOTTOM</code>
     * <li><code>VerticalFlowLayout.CENTER</code>
     * </ul>
     * @param     align one of the alignment values shown above
     * @see     #getAlignment()
     * @since     JDK1.1
     */

    public void setAlignment(int align)
    {
        this.align = align;
    }

    /**
     * Gets the horizontal gap between components
     * and between the components and the borders
     * of the <code>Container</code>
     *
     * @return   the horizontal gap between components
     *           and between the components and the borders
     *           of the <code>Container</code>
     * @see     java.awt.VerticalFlowLayout#setHgap
     * @since     JDK1.1
     */

    public int getHgap()
    {
        return hgap;
    }

    /**
     * Sets the horizontal gap between components and
     * between the components and the borders of the
     * <code>Container</code>.
     *
     * @param hgap the horizontal gap between components
     *           and between the components and the borders
     *           of the <code>Container</code>
     * @see     java.awt.VerticalFlowLayout#getHgap
     * @since     JDK1.1
     */

    public void setHgap(int hgap)
    {
        this.hgap = hgap;
    }

    /**
     * Gets the vertical gap between components and
     * between the components and the borders of the
     * <code>Container</code>.
     *
     * @return   the vertical gap between components
     *           and between the components and the borders
     *           of the <code>Container</code>
     * @see     java.awt.VerticalFlowLayout#setVgap
     * @since     JDK1.1
     */

    public int getVgap()
    {
        return vgap;
    }

    /**
     * Sets the vertical gap between components and between
     * the components and the borders of the <code>Container</code>.
     *
     * @param vgap the vertical gap between components
     *           and between the components and the borders
     *           of the <code>Container</code>
     * @see     java.awt.VerticalFlowLayout#getVgap
     */

    public void setVgap(int vgap)
    {
        this.vgap = vgap;
    }

    /**
     * Adds the specified component to the layout.
     * Not used by this class.
     * @param name the name of the component
     * @param comp the component to be added
     */

    public void addLayoutComponent(String name, Component comp) {
        
    }

    /**
     * Removes the specified component from the layout.
     * Not used by this class.
     * @param comp the component to remove
     * @see    java.awt.Container#removeAll
     */

    public void removeLayoutComponent(Component comp) {
    }

    /**
     * Returns the preferred dimensions for this layout given the
     * <i>visible</i> components in the specified target container.
     *
     * @param target the container that needs to be laid out
     * @return  the preferred dimensions to lay out the
     *          subcomponents of the specified container
     * @see Container
     * @see #minimumLayoutSize
     * @see    java.awt.Container#getPreferredSize
     */

    public Dimension preferredLayoutSize(Container target)
    {
        synchronized (target.getTreeLock())
        {
           Dimension dim = new Dimension(0, 0);
           int members = target.getComponentCount();
           boolean firstVisibleComponent = true;

           for(int i = 0; i < members; i++)
           {
               Component m = target.getComponent(i);

               if (m.isVisible())
               {
                   Dimension d = m.getPreferredSize();
                   dim.width = Math.max(dim.width, d.width);

                   if (firstVisibleComponent)
                   {
                       firstVisibleComponent = false;
                   }
                   else
                   {
                       dim.height += vgap;
                   }
                   dim.height += d.height;
               }
           }
           Insets insets = target.getInsets();
           dim.width += insets.left + insets.right + hgap*2;
           dim.height += insets.top + insets.bottom + vgap*2;
           return dim;
        }
    }

    /**
     * Returns the minimum dimensions needed to layout the <i>visible</i>
     * components contained in the specified target container.
     * @param target the container that needs to be laid out
     * @return  the minimum dimensions to lay out the
     *          subcomponents of the specified container
     * @see #preferredLayoutSize
     * @see    java.awt.Container
     * @see    java.awt.Container#doLayout
     */

    public Dimension minimumLayoutSize(Container target)
    {
        synchronized(target.getTreeLock())
        {
            Dimension dim = new Dimension(0, 0);
            int members = target.getComponentCount();
            boolean firstVisibleComponent = true;

            for(int i = 0; i <members; i++)
            {
                Component m = target.getComponent(i);

                if (m.isVisible())
                {
                    Dimension d = m.getMinimumSize();
                    dim.width = Math.max(dim.width, d.width);

                    if (firstVisibleComponent)
                    {
                        firstVisibleComponent = false;
                    } // end if
                    else
                    {
                        dim.height += vgap;
                    } // end else

                    dim.height += d.height;
                } // end if
            } // end for

       	    Insets insets = target.getInsets();
       	    dim.width += insets.left + insets.right + hgap*2;
       	    dim.height += insets.top + insets.bottom + vgap*2;
      	    return dim;
        } // end synchronized
    } // end minimumLayoutSize method

    /**
     * Lays out the container. This method lets each
     * <i>visible</i> component take
     * its preferred size by reshaping the components in the
     * target container in order to satisfy the alignment of
     * this <code>VerticalFlowLayout</code> object.
     *
     * @param target the specified component being laid out
     * @see Container
     * @see    java.awt.Container#doLayout
    */

        public void layoutContainer(Container target)
        {
            synchronized(target.getTreeLock())
            {
                Insets insets = target.getInsets();
                int maxHeight = target.getSize().height - (insets.top + insets.bottom + vgap*2);
                int members = target.getComponentCount();
                int x = insets.left + hgap;
                int y = 0;
                int columnWidth = 0;
                int start = 0;

                boolean ttb = target.getComponentOrientation().isLeftToRight();

                for(int i = 0; i < members; i++)
                {
                    Component m = target.getComponent(i);

                    if (m.isVisible())
                    {
                        Dimension d = m.getPreferredSize();
                        m.setSize(d.width, d.height);

                        if ((y == 0) || ((y + d.height) <= maxHeight))
                        {
                            if (y > 0)
                            {
                                y = vgap;
                            } // end if

                            y += d.height;
                            columnWidth = Math.max(columnWidth, d.width);
                        } // end if
                        else
                        {
                                moveComponents(target, x, insets.top + vgap, columnWidth, maxHeight - y, start, i, ttb);
                                y = d.height;
                                x += hgap + columnWidth;
                                columnWidth = d.width;
                                start = i;
                        } // end else
                    } // end if
                } // end for

                    moveComponents(target, x, insets.top + vgap, columnWidth, maxHeight - y, start, members, ttb);
            } // end synchronized
        } // end layoutContainer method

    /**
     * Centers the elements in the specified row, if there is any slack.
     * @param target the component which needs to be moved
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width dimensions
     * @param height the height dimensions
     * @param columnStart the beginning of the column
     * @param columnEnd the the ending of the column
    */

        private void moveComponents(
            Container target, int x, int y, int width, int height, int columnStart, int columnEnd, boolean ttb)
        {
            switch (align)
            {
                case TOP:
                    y += ttb ? 0 : height;
                    break;
                case CENTER:
                    y += height / 2;
                    break;
                case BOTTOM:
                    y += ttb ? height : 0;
                    break;
            } // end switch

            for(int i = columnStart; i < columnEnd; i++)
            {
                Component m = target.getComponent(i);

                if (m.isVisible())
                {
                    int cx;
                    cx = x + (width - m.getSize().width / 2);

                    if (ttb)
                    {
                        m.setLocation(cx, y);
                    } // end if
                    else
                    {
                        m.setLocation(cx, target.getSize().height - y - m.getSize().height);
                    } // end else

                    y += m.getSize().height + vgap;
                } // end if
            } // end for

        } // end moveComponents method

        public String toString()
        {
            String str = "";
            switch(align)
            {
                case TOP: str = ",align=top"; break;
                case CENTER: str = ",align=center"; break;
                case BOTTOM: str = ",align=bottom"; break;
            } // end switch

            return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + str + "]";
        } // end toString method

        public static void main(String[] args)
        {
            JPanel main = new JPanel();

            final JPanel buttons = new JPanel(new VerticalFlowLayout());

            JButton button = new JButton("Add Radio Button");
            main.add(button, BorderLayout.SOUTH);
            button.addActionListener(new ActionListener()
                {
                    private int i = 8;

                    public void actionPerformed(ActionEvent ae)
                    {
                        buttons.add(new JRadioButton("button R Us" + i++));
                        buttons.revalidate();
//                      pack();
                    } // end actionPerformed method
                });

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(main);
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } // end main method
}