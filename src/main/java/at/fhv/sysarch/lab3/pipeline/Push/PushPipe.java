package at.fhv.sysarch.lab3.pipeline.Push;


public class PushPipe<T> implements IPushPipe<T> {

    private IPushPipe<T> successor;

    public void setSuccessor(IPushPipe<T> sink) {
        this.successor = sink;
    }

    public void write(T face) {
        this.successor.write(face);
    }
}
