package pl.pajwoj.config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class TrayIconConfig {
    public static void init() {
        SystemTray tray = SystemTray.getSystemTray();

        PopupMenu menu = getPopupMenu();

        InputStream imageStream = ClassLoader.getSystemClassLoader().getResourceAsStream("trayicon.png");
        BufferedImage image;
        try {
            assert imageStream != null;
            image = ImageIO.read(imageStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TrayIcon icon = new TrayIcon(image, "Weather Optimizer", menu);
        icon.setImageAutoSize(true);

        try {
            tray.add(icon);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private static PopupMenu getPopupMenu() {
        PopupMenu menu = new PopupMenu();

        MenuItem exit = new MenuItem();
        exit.setEnabled(true);
        exit.setLabel("Exit");
        exit.addActionListener(e -> System.exit(0));

        MenuItem browser = new MenuItem();
        browser.setEnabled(true);
        browser.setLabel("Open in browser");
        browser.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("http://localhost:8080"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        menu.add(browser);
        menu.add(exit);
        return menu;
    }
}
