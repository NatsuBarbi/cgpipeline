package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import javafx.scene.paint.Color;

public class DepthSortingFilter implements IFilterPull<Face, Face> {

    @Override
    public void getFromPrecessor(PullPipe<Face> pipe) {

    }

    @Override
    public Face read() {
        return null;
    }

    @Override
    public boolean hasFaces() {
        return false;
    }
}
