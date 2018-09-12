package com.awt.learn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D.Float;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author tangwei
 * @date 2018/9/12 14:35
 */
public class ArtDesignFrame  extends JFrame{

    private static final long serialVersionUID = 6753823382364980954L;
    ArtDesignFrame.ArtDesignPanel artDesignPanel = new ArtDesignFrame.ArtDesignPanel();

    public static void main(String[] args) {
        ArtDesignFrame frame = new ArtDesignFrame();
        frame.setVisible(true);
    }

    public ArtDesignFrame() {
        this.setTitle("绘制艺术图案");
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 338, 230);
        this.add(this.artDesignPanel);
    }

    class ArtDesignPanel extends JPanel {
        private static final long serialVersionUID = 67936637491009735L;

        ArtDesignPanel() {
        }

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Float ellipse = new Float(-80.0F, 5.0F, 160.0F, 10.0F);
            Random random = new Random();
            g2.translate(160, 90);
            int R = random.nextInt(256);
            int G = random.nextInt(256);
            int B = random.nextInt(256);
            Color color = new Color(R, G, B);
            g2.setColor(color);
            g2.draw(ellipse);

            for(int i = 0; i < 100; ++i) {
                R = random.nextInt(256);
                G = random.nextInt(256);
                B = random.nextInt(256);
                color = new Color(R, G, B);
                g2.setColor(color);
                g2.rotate(10.0D);
                g2.draw(ellipse);
            }

        }
    }
}
