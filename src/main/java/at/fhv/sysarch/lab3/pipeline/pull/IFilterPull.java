package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;

public interface IFilterPull <I, O> extends IPullPipe<I> {

    void getFromPrecessor(PullPipe<O> pipe);

    I read();

    boolean hasFaces();
}
