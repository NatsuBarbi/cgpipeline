package at.fhv.sysarch.lab3.pipeline.Push;

public interface IPushPipe<I> {
    void write(I input);
}
