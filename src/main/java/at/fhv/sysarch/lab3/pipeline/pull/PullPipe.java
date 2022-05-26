package at.fhv.sysarch.lab3.pipeline.pull;

public class PullPipe<I> implements IPullPipe<I> {

    private IFilterPull<I, ?> source;

    public PullPipe(IFilterPull<I, ?> source) {this.source = source;}

    public I read() { return this.source.read(); }

    public boolean hasFaces() {
        return this.source.hasFaces();
    }

}
