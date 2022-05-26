package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.Filter.*;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
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
        PullPipe<Face> sourceToModelFilter = new PullPipe<>(source);
        modelFilter.getFromPrecessor(sourceToModelFilter);

        // TODO 2. perform backface culling in VIEW SPACE
        BackfaceFilter backfaceFilter = new BackfaceFilter();
        PullPipe<Face> modelFilterToBackfaceFilter = new PullPipe<>(modelFilter);
        backfaceFilter.getFromPrecessor(modelFilterToBackfaceFilter);

        // TODO 3. perform depth sorting in VIEW SPACE
        DepthSortingFilter depthSortFilter = new DepthSortingFilter();
        PullPipe<Face> backfaceFilterToDepthSortingFilter = new PullPipe<>(backfaceFilter);
        depthSortFilter.getFromPrecessor(backfaceFilterToDepthSortingFilter);

        // TODO 4. add coloring (space unimportant)
        ColorFilter colorFilter = new ColorFilter(pd);
        PullPipe<Face> depthSortingFilterToColorFilter = new PullPipe<>(depthSortFilter);
        colorFilter.getFromPrecessor(depthSortingFilterToColorFilter);

        ProjTransformFilter projectTransformFilter = new ProjTransformFilter(pd.getProjTransform());

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            LightFilter lightningFilter = new LightFilter(pd);
            PullPipe<Pair<Face, Color>> colorFilterTolightningFilter = new PullPipe<>(colorFilter);
            lightningFilter.getFromPrecessor(colorFilterTolightningFilter);

            PullPipe<Pair<Face, Color>> lightningFilterToProjectTransformFilter = new PullPipe<>(lightningFilter);
            projectTransformFilter.getFromPrecessor(lightningFilterToProjectTransformFilter);

        } else {
            PullPipe<Pair<Face, Color>> colorFilterToProjectTransformFilter = new PullPipe<>(colorFilter);
            projectTransformFilter.getFromPrecessor(colorFilterToProjectTransformFilter);
        }

        // TODO 6. perform perspective division to screen coordinates
        PerspDivision perspectiveDivisionFilter = new PerspDivision();
        PullPipe<Pair<Face, Color>> projectTransformFilterToPerspectiveDivisionFilter = new PullPipe<>(projectTransformFilter);
        perspectiveDivisionFilter.getFromPrecessor(projectTransformFilterToPerspectiveDivisionFilter);

        ProjTransformFilter modelView = new ProjTransformFilter(pd.getViewportTransform());
        PullPipe<Pair<Face, Color>> perspectiveDivisionFilterToModelView = new PullPipe<>(perspectiveDivisionFilter);
        modelView.getFromPrecessor(perspectiveDivisionFilterToModelView);

        // TODO 7. feed into the sink (renderer)
        PullRenderer renderer = new PullRenderer(pd.getGraphicsContext(), pd.getRenderingMode());
        PullPipe<Pair<Face, Color>> modelViewToRenderer = new PullPipe<>(modelView);
        renderer.getFromPrecessor(modelViewToRenderer);

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
                // TODO compute rotation in radiant
                pos += fraction;
                double radiant = pos % (2 * Math.PI);
                // TODO create new model rotation matrix using pd.modelRotAxis
                Mat4 rot = Matrices.rotate(
                        (float) radiant,
                        pd.getModelRotAxis()
                );
                // TODO compute updated model-view tranformation

                modelFilter.setRot(rot);

                // TODO update model-view modelFilter
                source.setSource(model.getFaces());

                // TODO trigger rendering of the pipeline
                renderer.read();
            }
        };
    }
}
