package redempt.orbitsim;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.Format;

import javax.swing.JFormattedTextField;

@SuppressWarnings("serial")
public class PlaceholderTextField extends JFormattedTextField {

    private String placeholder;

    public PlaceholderTextField() {
    }

    public PlaceholderTextField(final int pColumns) {
        super(pColumns);
    }

    public PlaceholderTextField(final String pText) {
        super(pText);
    }
    
    public PlaceholderTextField(Format format) {
    	super(format);
    }

    public String getPlaceholder() {
        return placeholder;
    }
    
    @Override
    public Dimension getPreferredSize() {
    	return this.getSize();
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics()
            .getMaxAscent() + getInsets().top);
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

}