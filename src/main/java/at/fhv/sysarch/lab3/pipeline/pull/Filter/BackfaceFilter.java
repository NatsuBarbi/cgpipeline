package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;

public class BackfaceFilter implements IFilterPull<Face, Face> {

    private PullPipe<Face> pipePrecessor;


    @Override
    public void getFromPrecessor(PullPipe<Face> pipe) {
        this.pipePrecessor = pipe;
    }

    public boolean hasFaces() { return pipePrecessor.hasFaces(); }
    @Override
    public Face read() {
        Face source = pipePrecessor.read();
       if(source.getV1().dot(source.getN1()) > 0) {
           return null;
       } else {
           return source;
       }
    }


    // Todo Edits Barbi





}
