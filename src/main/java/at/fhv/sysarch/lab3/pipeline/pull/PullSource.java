package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.ArrayList;
import java.util.List;

public class PullSource implements IFilterPull<Face, Face> {

    public PullSource() {}

    private List<Face> facesList = new ArrayList<>();

    @Override
    public void getFromPrecessor(PullPipe<Face> pipe) {

    }

    public boolean hasFaces() {
        return !this.facesList.isEmpty();
    }

    @Override
    public Face read() {
        return this.facesList.remove(0);
    }

    public void setSource (List<Face> list) {
        this.facesList.addAll(list);
    }


}
