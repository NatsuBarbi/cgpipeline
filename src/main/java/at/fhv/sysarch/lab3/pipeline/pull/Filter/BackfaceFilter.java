package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;

public class BackfaceFilter implements IFilterPull<Face, Face> {

    private PullPipe<Face> pipePrecessor;

    private Face nextF = null;

    @Override
    public void getFromPrecessor(PullPipe<Face> pipe) {
        this.pipePrecessor = pipe;
    }

    public boolean hasFaces() {
        hasNextFace(); // search for valid Face
        return nextF != null; // if found, we have it.
    }

    @Override
    public Face read() {
        hasNextFace();
        Face foundFace = nextF;
        nextF = null;
        return foundFace;
    }

    private void hasNextFace() {
        // while we have faces in source, search for valid face
        // valid face is a face that is not "behind" the view space
        // check one face, if not valid, try next face till source is empty
        while (pipePrecessor.hasFaces() && nextF == null) {
            Face source = pipePrecessor.read();
            if (source.getV1().dot(source.getN1()) < 0) {
                // valid face found
                nextF = source;
            }
        }
    }
}
