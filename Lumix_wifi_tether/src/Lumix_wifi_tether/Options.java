package Lumix_wifi_tether;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The options used in the LumixStreamViewer
 */
public class Options {

    private final String cameraIp;

    private final int cameraNetMaskBitSize;
    private final int udpinit;
    private final boolean blue;
    private final boolean yolo;
    private final boolean dev;

    private Options(String cameraIp, int cameraNetMaskBitSize, int udpinit,boolean blue, boolean yolo,boolean dev) {
        this.cameraIp = cameraIp;
        this.cameraNetMaskBitSize = cameraNetMaskBitSize;
        this.udpinit= udpinit;
        this.blue=blue;
        this.yolo=yolo;
        this.dev=dev;
    }

    /**
     * Either reads the options from the program arguments, or via standard input.
     *
     * @param args the program arguments
     * @return the LumixStreamViewer options
     */
    public static Options read(String[] args) {
        String cameraIp = "192.168.0.1";
        int cameraNetMaskBitSize = 24;
        int udpinit=60606;
        boolean blue=false;
        boolean yolo=false;
        boolean dev=false;

        if (args.length > 4) {
            cameraIp = args[0];
            try {
                cameraNetMaskBitSize = Integer.parseInt(args[1]);
                udpinit=Integer.parseInt(args[2]);
                blue=Boolean.parseBoolean(args[3]);
                yolo=Boolean.parseBoolean(args[4]);
                dev=Boolean.parseBoolean(args[5]);
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
                // camera IP
                System.out.print("Camera IP address [192.168.0.1]: ");
                cameraIp = in.readLine();
                if (cameraIp.length() == 0) {
                    cameraIp = "192.168.0.1";
                }

                // camera netmask
                System.out.print("Camera IP netmask size [24]: ");
                String mask = in.readLine();
                if (mask.length() > 0) {
                    cameraNetMaskBitSize = Integer.parseInt(mask);
                } else {
                    cameraNetMaskBitSize = 24;
                }
                System.out.print("Port for init [60606]: ");
                String mask2 = in.readLine();
                if (mask2.length() > 0) {
                    udpinit = Integer.parseInt(mask2);
                } else {
                    udpinit = 60606;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Options(cameraIp, cameraNetMaskBitSize,udpinit,blue,yolo,dev);
    }

    public String getCameraIp() {
        return cameraIp;
    }

    public int getCameraNetMaskBitSize() {
        return cameraNetMaskBitSize;
    }
    public int getudpinit() {
        return udpinit;
    }
    public boolean getblue() {
        return blue;
    }
    public boolean getyolo() {
        return yolo;
    }
    public boolean getdev() {
        return dev;
    }

}
