package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class LightFilter implements IFilterPull<Pair<Face, Color>, Pair<Face, Color>> {

    private PullPipe<Pair<Face, Color>> pipePrecessor;

    private final PipelineData pd;

    public LightFilter(PipelineData pd) {
        this.pd = pd;
    }

    @Override
    public void getFromPrecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePrecessor = pipe;
    }

    public boolean hasFaces() {
        return pipePrecessor.hasFaces();
    }

    public Pair<Face, Color> read() {
        Pair<Face, Color> pair = this.pipePrecessor.read();
        double product = pair.fst().getN1().dot(new Vec4(pd.getLightPos().getUnitVector(), 0));

        return new Pair<>(
                pair.fst(),
                pair.snd().deriveColor(0, 1, product, 1));
    }
}
