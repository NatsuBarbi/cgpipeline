package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class ColorFilter implements IFilter<Face, Pair<Face, Color>> {

    private Pipe<Pair<Face, Color>> pipeSuccessor;
    private final PipelineData pd;

    public ColorFilter(PipelineData pd) {
        this.pd = pd;
    }


    public void setPipeSuccessor(Pipe<Pair<Face, Color>> pipe) {
        this.pipeSuccessor = pipe;
    }


    public void write(Face face) {
        this.pipeSuccessor.write(new Pair<>(face, pd.getModelColor()));
    }

}
