package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.IPullPipe;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PullRenderer implements IPullPipe<Pair<Face, Color>> {
    private GraphicsContext context;
    private RenderingMode rm;

    private PullPipe<Pair<Face, Color>> pipePrecessor;

    public PullRenderer(GraphicsContext context, RenderingMode rm) {
        this.context = context;
        this.rm = rm;
    }

    public void getFromPrecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePrecessor = pipe;
    }

    public Pair<Face, Color> read() {

        while (pipePrecessor.hasFaces()) {
            Pair<Face, Color> input = pipePrecessor.read();

            Color color = input.snd();

            double[] cordX = new double[]{input.fst().getV1().getX(), input.fst().getV2().getX(), input.fst().getV3().getX()};
            double[] cordY = new double[]{input.fst().getV1().getY(), input.fst().getV2().getY(), input.fst().getV3().getY()};

            context.setStroke(color);
            context.setFill(color);
            int factor = 1;
            if (rm == RenderingMode.POINT) {
                context.fillOval(input.fst().getV1().getX() * factor, input.fst().getV1().getY() * factor, 5, 5);
            } else if (rm == RenderingMode.WIREFRAME) {
                context.strokeLine( input.fst().getV1().getX() * factor, input.fst().getV1().getY() * factor,
                                    input.fst().getV2().getX() * factor, input.fst().getV2().getY() * factor);
                context.strokeLine( input.fst().getV1().getX() * factor, input.fst().getV1().getY() * factor,
                                    input.fst().getV3().getX() * factor, input.fst().getV3().getY() * factor);
                context.strokeLine( input.fst().getV2().getX() * factor, input.fst().getV2().getY() * factor,
                                    input.fst().getV3().getX() * factor, input.fst().getV3().getY() * factor);
            } else if (rm == RenderingMode.FILLED) {
                context.strokePolygon(cordX, cordY, 3);
                context.fillPolygon(cordX, cordY, 3);
            }
        }
        return null;
    }
}
