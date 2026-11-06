import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class UITheme {

    public static final double BASE_W = 1150;
    public static final double BASE_H = 820;
    private static float skala = 1f;
    public static final Color NAVY = new Color(0x12, 0x35, 0x78);
    public static final Color BLUE = new Color(0x2B, 0x63, 0xC4);
    public static final Color BORDER_BLUE = new Color(0x9F, 0xBC, 0xE8);
    public static final Color TEXT_DARK = new Color(0x10, 0x2A, 0x5C);
    public static final Color PLACEHOLDER = new Color(0x8A, 0x96, 0xAD);

    public static float getSkala() {
        return skala;
    }

    public static void setSkala(float s) {
        skala = s;
    }

    public static int px(double v) {
        return (int) Math.round(v * skala);
    }

    public static float fpx(double v) {
        return (float) (v * skala);
    }

    public static Font fnt(double size, int style) {
        return new Font("SansSerif", style, px(size));
    }

    public static float hitungSkala(int w, int h) {
        double s = Math.min(w / BASE_W, h / BASE_H);
        s = Math.max(1.0, Math.min(3.0, s));
        return (float) (Math.round(s * 20) / 20.0);
    }

    public static void aktifkanAntialias(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
}
