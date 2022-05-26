package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;

import java.util.Comparator;
import java.util.LinkedList;

public class DepthSortingFilter implements IFilterPull<Face, Face> {

    private PullPipe<Face> pipePrecessor;

    private final LinkedList<Face> sortFace = new LinkedList<>();

    @Override
    public void getFromPrecessor(PullPipe<Face> pipe) {
        this.pipePrecessor = pipe;
    }

    @Override
    public Face read() {

        if (pipePrecessor.hasFaces() && sortFace.isEmpty()) {
            while (pipePrecessor.hasFaces()) {
                sortFace.add(pipePrecessor.read());
            }
            sortFace.sort(Comparator.comparing(face -> face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()));
        }
        return sortFace.removeFirst();
    }

    @Override
    public boolean hasFaces() {
        return pipePrecessor.hasFaces();
    }
}
