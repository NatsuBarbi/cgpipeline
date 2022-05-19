package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;

public class Renderer implements ISink<Face> {
    private GraphicsContext context;
    private RenderingMode rm;

    public Renderer(GraphicsContext context, RenderingMode rm) {
        this.context = context;
        this.rm = rm;
    }

    public void write(Face face) {
        int factor = 1;
        if(rm == RenderingMode.POINT) {
            context.fillOval(face.getV1().getX()*factor, face.getV1().getY()*factor, 5,5);
        }
        else {
            context.strokeLine(face.getV1().getX() * factor, face.getV1().getY() * factor, face.getV2().getX() * factor, face.getV2().getY() * factor);
            context.strokeLine(face.getV1().getX() * factor, face.getV1().getY() * factor, face.getV3().getX() * factor, face.getV3().getY() * factor);
            context.strokeLine(face.getV2().getX() * factor, face.getV2().getY() * factor, face.getV3().getX() * factor, face.getV3().getY() * factor);
        }
    }
}
