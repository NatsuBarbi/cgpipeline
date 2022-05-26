package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import javafx.scene.paint.Color;

public class PerspDivision implements IFilterPull<Pair<Face, Color>, Pair<Face, Color>> {

    private PullPipe<Pair<Face, Color>> pipePrecessor;

    @Override
    public void getFromPrecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePrecessor = pipe;
    }

    public boolean hasFaces() {
        return pipePrecessor.hasFaces();
    }

    @Override
    public Pair<Face, Color> read() {
        Pair<Face, Color> input = this.pipePrecessor.read();
        Face face = new Face(input.fst().getV1().multiply(1 / input.fst().getV1().getW()), input.fst().getV2().multiply(1 / input.fst().getV2().getW()), input.fst().getV3().multiply(1 / input.fst().getV3().getW()), input.fst());
        return new Pair<>(face, input.snd());
    }
}

