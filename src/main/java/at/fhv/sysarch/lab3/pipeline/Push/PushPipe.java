package at.fhv.sysarch.lab3.pipeline.Push;


// TODO: how can pipes be used for different data types?
public class PushPipe<T> implements ISink<T> {

    private ISink<T> successor;

    public void setSuccessor(ISink<T> sink) {
        this.successor = sink;
    }

    public void write(T face) {
        this.successor.write(face);
    }

}
