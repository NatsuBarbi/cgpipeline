package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DepthSortingFilter implements IFilterPull<Face, Face> {

    private PullPipe<Face> pipePrecessor;

    private final LinkedList<Face> sortFace = new LinkedList<>();

    @Override
    public void getFromPrecessor(PullPipe<Face> pipe) {
        this.pipePrecessor = pipe;
    }

    @Override
    public Face read() {
        return sortFace.removeFirst();
    }

    @Override
    public boolean hasFaces() {
        while(pipePrecessor.hasFaces()) {
            sortFace.add(pipePrecessor.read());
        }
        if (!sortFace.isEmpty()) {

            sortFace.sort(Comparator.comparing(face -> face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()));
        }
        return !sortFace.isEmpty();
    }
}
