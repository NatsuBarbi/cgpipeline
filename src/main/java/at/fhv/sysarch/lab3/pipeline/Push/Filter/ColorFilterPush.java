package at.fhv.sysarch.lab3.pipeline.Push.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IFilterPush;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class ColorFilterPush implements IFilterPush<Face, Pair<Face, Color>> {

    private PushPipe<Pair<Face, Color>> pipeSuccessor;
    private final PipelineData pd;

    public ColorFilterPush(PipelineData pd) {
        this.pd = pd;
    }

    public void setPipeSuccessor(PushPipe<Pair<Face, Color>> pipe) {
        this.pipeSuccessor = pipe;
    }

    public void write(Face face) {
        this.pipeSuccessor.write(new Pair<>(face, pd.getModelColor()));
    }
}
