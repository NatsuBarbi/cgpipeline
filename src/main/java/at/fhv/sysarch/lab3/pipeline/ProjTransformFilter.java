package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class ProjTransformFilter implements IFilter<Pair<Face, Color>, Pair<Face,Color>> {

    private Pipe<Pair<Face, Color>> pipeSuccessor;

    public ProjTransformFilter(Mat4 mat) {
        Mat = mat;
    }


    public void setPipeSuccessor(Pipe<Pair<Face,Color>> pipe) {
        this.pipeSuccessor = pipe;
    }

    private Mat4 Mat;

    public void write(Pair<Face, Color> input) {
        Face newFace = new Face(Mat.multiply(input.fst().getV1()), Mat.multiply(input.fst().getV2()), Mat.multiply(input.fst().getV3()), Mat.multiply(input.fst().getN1()), Mat.multiply(input.fst().getN2()), Mat.multiply(input.fst().getN3()));
        this.pipeSuccessor.write(new Pair<Face, Color>(newFace, input.snd()));
    }

}
