package net.katsuster.strview.autogen;

import java.io.*;

public class GenMain {
    protected static void showError() {
        System.err.println("Usage:\n"
                + "  autogen type [input file] [output file]\n"
                + "    type  : Input file type (asf, avc, mpeg2, mpeg4).\n"
                + "    input : Name of a definition file. Default is standard input.\n"
                + "    output: Name of a skelton code file. Default is standard output.\n");
    }

    public static void main(String[] args) {
        String type;
        InputStream in = System.in;
        OutputStream out = System.out;

        try {
            switch (args.length) {
            case 3:
                out = new FileOutputStream(args[2]);
                //fall through
            case 2:
                in = new FileInputStream(args[1]);
                //fall through
            case 1:
                type = args[0];
                break;
            default:
                showError();
                return;
            }

            if (type.equals("asf")) {
                GenASF.parseStream(in, out);
            } else if (type.equals("avc")) {
                GenAVC.parseStream(in, out);
            } else if (type.equals("mpeg2")) {
                GenMPEG2.parseStream(in, out);
            } else if (type.equals("mpeg4")) {
                GenMPEG4.parseStream(in, out);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
