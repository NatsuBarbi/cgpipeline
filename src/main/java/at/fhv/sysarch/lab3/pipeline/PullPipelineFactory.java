package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.Filter.*;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
import at.fhv.sysarch.lab3.pipeline.pull.IPullPipe;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import at.fhv.sysarch.lab3.pipeline.pull.PullSource;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;


public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: pull from the source (model)
        PullSource source = new PullSource();
        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        ModelFilter modelFilter = new ModelFilter(pd.getModelTranslation().multiply(pd.getViewTransform()));
        PullPipe<Face> srcToMVP = new PullPipe<>(source);
        modelFilter.getFromPrecessor(srcToMVP);
        // TODO 2. perform backface culling in VIEW SPACE
        BackfaceFilter backfaceFilter = new BackfaceFilter();
        PullPipe<Face> MvpToBf = new PullPipe<>(modelFilter);
        backfaceFilter.getFromPrecessor(MvpToBf);
        // TODO 3. perform depth sorting in VIEW SPACE

        DepthSortingFilter depthSortFilter = new DepthSortingFilter();
        PullPipe<Face> BfToDs = new PullPipe<>(backfaceFilter);
        depthSortFilter.getFromPrecessor(BfToDs);

        // TODO 4. add coloring (space unimportant)
        ColorFilter colorFilter = new ColorFilter(pd);
        PullPipe<Face> DsToCf = new PullPipe<>(depthSortFilter);
        colorFilter.getFromPrecessor(DsToCf);

        ProjTransformFilter projFilter = new ProjTransformFilter(pd.getProjTransform());

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            LightFilter lightFilter = new LightFilter(pd);
            PullPipe<Pair<Face, Color>> CfToLf = new PullPipe<>(colorFilter);
            lightFilter.getFromPrecessor(CfToLf);
            PullPipe<Pair<Face, Color>> LfToPf = new PullPipe<>(lightFilter);
            projFilter.getFromPrecessor(LfToPf);

        } else {
            PullPipe<Pair<Face, Color>> CfToPf = new PullPipe<>(colorFilter);
            projFilter.getFromPrecessor(CfToPf);
        }

        // TODO 6. perform perspective division to screen coordinates
        PerspDivision pdiv = new PerspDivision();
        PullPipe<Pair<Face, Color>> PfToPdiv = new PullPipe<>(projFilter);
        pdiv.getFromPrecessor(PfToPdiv);

        ProjTransformFilter mv = new ProjTransformFilter(pd.getViewportTransform());
        PullPipe<Pair<Face, Color>> PdivToMv = new PullPipe<>(pdiv);
        mv.getFromPrecessor(PdivToMv);
        // TODO 7. feed into the sink (renderer)

        PullRenderer renderer = new PullRenderer(pd.getGraphicsContext(), pd.getRenderingMode());
        PullPipe<Pair<Face, Color>> MvToRenderer = new PullPipe<>(mv);
        renderer.getFromPrecessor(MvToRenderer);
        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            float pos = 0f;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians
                 pos += fraction;
                 double radians = pos % (2*Math.PI);
                 // TODO create new model rotation matrix using pd.modelRotAxis
                 Mat4 rot = Matrices.rotate(
                 (float) radians,
                 pd.getModelRotAxis()
                 );
                 // TODO compute updated model-view tranformation

                 modelFilter.setRot(rot);

                // TODO update model-view modelFilter

                // TODO trigger rendering of the pipeline

                // line
                // pd.getGraphicsContext().setStroke(Color.PINK);
                // pd.getGraphicsContext().strokeLine(0 + pos, 0 + pos, 100 + pos, 100 + pos);
                // pos++;

                source.setSource(model.getFaces());
                renderer.read();
            }
        };
    }
}
