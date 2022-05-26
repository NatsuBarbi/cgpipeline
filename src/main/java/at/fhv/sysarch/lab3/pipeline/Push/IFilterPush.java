package at.fhv.sysarch.lab3.pipeline.Push;


public interface IFilterPush<I, O> extends IPushPipe<I> {
    void setPipeSuccessor(PushPipe<O> pipe);
    void write(I input);
}
