package at.fhv.sysarch.lab3.pipeline.Push.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IFilterPush;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class PerspDivision implements IFilterPush<Pair<Face, Color>, Pair<Face, Color>> {

    private PushPipe<Pair<Face, Color>> pipeSuccessor;

    @Override
    public void setPipeSuccessor(PushPipe<Pair<Face, Color>> pipe) {
        this.pipeSuccessor = pipe;
    }

    @Override
    public void write(Pair<Face, Color> input) {
        Face face = new Face(input.fst().getV1().multiply(1 / input.fst().getV1().getW()), input.fst().getV2().multiply(1 / input.fst().getV2().getW()), input.fst().getV3().multiply(1 / input.fst().getV3().getW()), input.fst());
        this.pipeSuccessor.write(new Pair(face, input.snd()));
    }
}

