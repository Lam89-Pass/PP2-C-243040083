import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HalamanRingkasan extends GradientPanel {
    private Main mainFrame;
    private JTextArea txtDetail;
    private ModelPendaftaran currentData;

    public HalamanRingkasan(Main mainFrame) {
        super(new BorderLayout(UITheme.px(20), UITheme.px(20)));
        this.mainFrame = mainFrame;

        setBorder(new EmptyBorder(UITheme.px(20), UITheme.px(30), UITheme.px(20), UITheme.px(30)));

        SectionPanel pnlContent = new SectionPanel("Ringkasan Data Pendaftaran", Glyph.DOC);
        pnlContent.setLayout(new BorderLayout());

        txtDetail = new JTextArea();
        txtDetail.setEditable(false);
        txtDetail.setFont(new Font("Monospaced", Font.PLAIN, UITheme.px(16)));
        txtDetail.setForeground(UITheme.TEXT_DARK);
        txtDetail.setBackground(Color.WHITE);
        txtDetail.setMargin(new Insets(UITheme.px(20), UITheme.px(20), UITheme.px(20), UITheme.px(20)));

        JScrollPane scroll = new JScrollPane(txtDetail);
        scroll.setBorder(new LineBorder(UITheme.BORDER_BLUE, 1, true));
        scroll.getViewport().setBackground(Color.WHITE);
        pnlContent.add(scroll, BorderLayout.CENTER);

        add(pnlContent, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, UITheme.px(15), UITheme.px(10)));
        bottom.setOpaque(false);

        RoundedButton btnKembali = new RoundedButton("Kembali Edit Form", Glyph.REFRESH, false);
        RoundedButton btnDaftar = new RoundedButton("Daftar Sekarang", Glyph.SEND, true);

        btnKembali.addActionListener(e -> mainFrame.showCard("FORM"));
        btnDaftar.addActionListener(e -> mainFrame.pendaftaranBerhasil(currentData));

        bottom.add(btnKembali);
        bottom.add(btnDaftar);

        add(bottom, BorderLayout.SOUTH);
    }

    public void tampilkanData(ModelPendaftaran data) {
        this.currentData = data;
        txtDetail.setText(data.getRingkasan());
        txtDetail.setCaretPosition(0);
    }
}
