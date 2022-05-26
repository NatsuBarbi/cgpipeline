package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IFilter;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import javafx.scene.paint.Color;

public class LightFilter implements IFilterPull<Pair<Face, Color>, Pair<Face,Color>> {

    private PullPipe<Pair<Face, Color>> pipePrecessor;

    private final PipelineData pd;

    public LightFilter(PipelineData pd) {
        this.pd = pd;
    }


    @Override
    public void getFromPrecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePrecessor = pipe;
    }

    public boolean hasFaces() { return pipePrecessor.hasFaces(); }


    public Pair<Face,Color> read() {
       Face face = this.pipePrecessor.read().fst();
       float product = face.getN1().toVec3().dot(pd.getLightPos().getUnitVector());
       if (product > 0) {
           return new Pair<>(face, this.pipePrecessor.read().snd().deriveColor(0,1, product,1));
       } else {
          return new Pair<>(face, Color.BLACK);
       }
    }



}
