package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import javafx.scene.paint.Color;

public class ColorFilter implements IFilterPull<Pair<Face, Color>, Face> {

    private PullPipe<Face> pipePrecessor;
    private final PipelineData pd;

    @Override
    public void getFromPrecessor(PullPipe<Face> pipe) {
        this.pipePrecessor = pipe;
    }

    public boolean hasFaces() { return pipePrecessor.hasFaces(); }

    public ColorFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public Pair<Face, Color> read() {
        Face face = pipePrecessor.read();
        Pair<Face, Color> pair = new Pair<>(face, pd.getModelColor());
        return pair;
    }

}
