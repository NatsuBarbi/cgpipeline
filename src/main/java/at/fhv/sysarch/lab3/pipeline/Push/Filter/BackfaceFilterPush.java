package at.fhv.sysarch.lab3.pipeline.Push.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IFilterPush;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;

public class BackfaceFilterPush implements IFilterPush<Face, Face> {

    private PushPipe<Face> pipeSuccessor;

    public BackfaceFilterPush() {

    }

    public void setPipeSuccessor(PushPipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    @Override
    public void write(Face face) {
        if (face.getV1().dot(face.getN1()) > 0) {
            return;
        } else {
            this.pipeSuccessor.write(face);
        }
    }
}
