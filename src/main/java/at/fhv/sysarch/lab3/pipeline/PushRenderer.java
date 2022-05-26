package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.ISink;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PushRenderer implements ISink<Pair<Face, Color>> {
    private GraphicsContext context;
    private RenderingMode rm;


    public PushRenderer(GraphicsContext context, RenderingMode rm) {
        this.context = context;
        this.rm = rm;
    }

    public void write(Pair<Face,Color> input) {
        Color color = input.snd();

        context.setStroke(color);
        context.setFill(color);
        int factor = 1;
        if(rm == RenderingMode.POINT) {
            context.fillOval(input.fst().getV1().getX()*factor, input.fst().getV1().getY()*factor, 5,5);
        }
        else if(rm == RenderingMode.WIREFRAME){
            context.strokeLine(input.fst().getV1().getX() * factor, input.fst().getV1().getY() * factor, input.fst().getV2().getX() * factor, input.fst().getV2().getY() * factor);
            context.strokeLine(input.fst().getV1().getX() * factor, input.fst().getV1().getY() * factor, input.fst().getV3().getX() * factor, input.fst().getV3().getY() * factor);
            context.strokeLine(input.fst().getV2().getX() * factor, input.fst().getV2().getY() * factor, input.fst().getV3().getX() * factor, input.fst().getV3().getY() * factor);
        }  else if(rm == RenderingMode.FILLED) {
            context.fillPolygon(new double[]{input.fst().getV1().getX(), input.fst().getV2().getX(), input.fst().getV3().getX()}, new double[]{input.fst().getV1().getY(), input.fst().getV2().getY(), input.fst().getV3().getY()}, new double[]{input.fst().getV1().getX(), input.fst().getV2().getX(), input.fst().getV3().getX()}.length);
            context.strokeLine(input.fst().getV1().getX() * factor, input.fst().getV1().getY() * factor, input.fst().getV2().getX() * factor, input.fst().getV2().getY() * factor);
            context.strokeLine(input.fst().getV1().getX() * factor, input.fst().getV1().getY() * factor, input.fst().getV3().getX() * factor, input.fst().getV3().getY() * factor);
            context.strokeLine(input.fst().getV2().getX() * factor, input.fst().getV2().getY() * factor, input.fst().getV3().getX() * factor, input.fst().getV3().getY() * factor);
        }
    }
}
