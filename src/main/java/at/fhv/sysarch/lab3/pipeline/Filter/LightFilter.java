package at.fhv.sysarch.lab3.pipeline.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IFilter;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
import javafx.scene.paint.Color;

public class LightFilter implements IFilter<Pair<Face, Color>, Pair<Face,Color>> {

    private PushPipe<Pair<Face, Color>> pipeSuccessor;

    private final PipelineData pd;

    public LightFilter(PipelineData pd) {
        this.pd = pd;
    }


    public void setPipeSuccessor(PushPipe<Pair<Face,Color>> pipe) {
        this.pipeSuccessor = pipe;
    }


    public void write(Pair<Face, Color> input) {
       Face face = input.fst();
       float product = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());

           this.pipeSuccessor.write(new Pair<>(face, input.snd().deriveColor(0,1, product,1)));
    }



}
