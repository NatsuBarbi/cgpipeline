package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IFilter;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class ProjTransformFilter implements IFilterPull<Pair<Face, Color>, Pair<Face,Color>> {

    private PullPipe<Pair<Face, Color>> pipePrecessor;


    public ProjTransformFilter(Mat4 mat) {
        Mat = mat;
    }

    @Override
    public void getFromPrecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePrecessor = pipe;
    }


    private Mat4 Mat;

    public boolean hasFaces() { return pipePrecessor.hasFaces(); }

    public Pair<Face, Color> read() {
        Pair<Face, Color> input = this.pipePrecessor.read();
        Face newFace = new Face(Mat.multiply(input.fst().getV1()), Mat.multiply(input.fst().getV2()), Mat.multiply(input.fst().getV3()), Mat.multiply(input.fst().getN1()), Mat.multiply(input.fst().getN2()), Mat.multiply(input.fst().getN3()));
        return new Pair<>(newFace, input.snd());
    }



}
