import org.apache.batik.bridge.UserAgent;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.svg.LinkActivationEvent;
import org.apache.batik.swing.svg.LinkActivationListener;
import org.w3c.dom.svg.SVGAElement;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class SvgViewer {



    public static class MySvgCanvas extends JSVGCanvas {
        class MyCanvasUserAgent extends JSVGCanvas.CanvasUserAgent {
            @Override
            public void openLink(SVGAElement svgaElement) {
                String href =  svgaElement.getHref().getAnimVal();

                fireLinkActivatedEvent(svgaElement, href);
            }
        }

        @Override
        protected UserAgent createUserAgent() {
            return new MyCanvasUserAgent();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Svg Viewr");
        MySvgCanvas svgCanvas = new MySvgCanvas();
        File svgFile = new File(args[0]);
        svgCanvas.addLinkActivationListener(
                new LinkActivationListener() {
                    @Override
                    public void linkActivated(LinkActivationEvent linkActivationEvent) {
                        System.out.println(  linkActivationEvent.getReferencedURI() );
                    }
                }
        );

        frame.getContentPane().add(svgCanvas, BorderLayout.CENTER);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        svgCanvas.loadSVGDocument(args[0]);
        frame.pack();
        frame.setVisible(true);
    }

}
